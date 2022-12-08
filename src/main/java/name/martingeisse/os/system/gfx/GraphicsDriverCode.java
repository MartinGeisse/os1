package name.martingeisse.os.system.gfx;

import name.martingeisse.os.core.Code;
import name.martingeisse.os.core.ProcessContext;
import name.martingeisse.os.core.message.EmptyResponse;
import name.martingeisse.os.core.message.ServerRequestCycle;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphicsDriverCode implements Code {

    private final AtomicBoolean dirty = new AtomicBoolean(true);

    @Override
    public void run(ProcessContext context) throws InterruptedException {

        PixelPanel pixelPanel = new PixelPanel();
        JFrame frame = new JFrame("Pixel Display");
        frame.add(pixelPanel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        new Timer(20, event -> {
            if (dirty.getAndSet(false)) {
                pixelPanel.repaint();
            }
        }).start();

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ServerRequestCycle requestCycle = context.receiveRequest();
                if (requestCycle.getRequest() instanceof DrawRectangleRequest request) {
                    Graphics g = pixelPanel.createGraphics();
                    try {
                        g.setColor(new Color(request.r, request.g, request.b));
                        g.fillRect(request.x, request.y, request.w, request.h);
                    } finally {
                        g.dispose();
                    }
                    requestCycle.respond(new EmptyResponse());
                    dirty.set(true);
                }
            }
        } finally {
            frame.dispose();
        }
    }

}
