package name.martingeisse.os.system.gfx;

import name.martingeisse.os.core.Code;
import name.martingeisse.os.core.ProcessContext;
import name.martingeisse.os.core.message.request.EmptyResponse;
import name.martingeisse.os.core.message.request.ServerRequestCycle;
import name.martingeisse.os.core.message.subscription.ServerSubscriptionCycle;
import name.martingeisse.os.core.message.subscription.SubscriptionCancellationPseudoRequest;
import name.martingeisse.os.core.message.subscription.SubscriptionInitiationPseudoRequest;
import name.martingeisse.os.system.gfx.message.DrawRectangleRequest;
import name.martingeisse.os.system.gfx.message.KeyInputInitiation;
import name.martingeisse.os.system.gfx.message.SetColorRequest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentHashMap;

public class GraphicsDriverCode implements Code {

    private final ConcurrentHashMap<ServerSubscriptionCycle, ServerSubscriptionCycle> serverSubscriptionCycles = new ConcurrentHashMap<>();

    @Override
    public void run(ProcessContext context) throws InterruptedException {
        MainWindow mainWindow = new MainWindow(new MainWindow.Client() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                for (ServerSubscriptionCycle subscriptionCycle : serverSubscriptionCycles.keySet()) {
                    // no key code / char for now
                    subscriptionCycle.sendResponse(new EmptyResponse());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });

        Color color = new Color(255, 255, 255);
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ServerRequestCycle requestCycle = context.receiveRequest();
                if (requestCycle.getRequest() instanceof SubscriptionInitiationPseudoRequest request) {
                    if (request.initiation instanceof KeyInputInitiation) {
                        serverSubscriptionCycles.put(request.subscriptionCycle, request.subscriptionCycle);
                    }
                } else if (requestCycle.getRequest() instanceof SubscriptionCancellationPseudoRequest request) {
                    serverSubscriptionCycles.remove(request.subscriptionCycle);
                } else if (requestCycle.getRequest() instanceof SetColorRequest request) {
                    color = new Color(request.r, request.g, request.b);
                    requestCycle.respond(new EmptyResponse());
                } else if (requestCycle.getRequest() instanceof DrawRectangleRequest request) {
                    Graphics g = mainWindow.createGraphics();
                    try {
                        g.setColor(color);
                        g.fillRect(request.x, request.y, request.w, request.h);
                    } finally {
                        g.dispose();
                    }
                    requestCycle.respond(new EmptyResponse());
                    mainWindow.markDirty();
                }
            }
        } finally {
            mainWindow.dispose();
        }
    }

}
