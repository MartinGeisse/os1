package name.martingeisse.os.system.gfx;

import name.martingeisse.os.core.message.request.EmptyResponse;
import name.martingeisse.os.core.message.subscription.ServerSubscriptionCycle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public final class MainWindow extends JFrame {

    private final Client client;
    private final JPanel panel;
    private final BufferedImage image;

    public MainWindow(Client client) {
        super("Pixel Display");
        this.client = client;

        panel = new JPanel(false) {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, null);
            }
        };
        panel.setFocusable(true);
        panel.setSize(640, 480);
        panel.setPreferredSize(new Dimension(640, 480));

        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        add(panel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        new Timer(20, event -> {
            if (dirty.getAndSet(false)) {
                pixelPanel.repaint();
            }
        }).start();

        panel.addKeyListener(client);
    }

    public Graphics createGraphics() {
        return image.createGraphics();
    }

    public interface Client extends KeyListener {
    }
}
