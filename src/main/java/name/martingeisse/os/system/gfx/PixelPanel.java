package name.martingeisse.os.system.gfx;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 */
public class PixelPanel extends JPanel {

    private final BufferedImage image;
    private final LinkedList<Byte> inputBuffer = new LinkedList<>();

    public PixelPanel() {
        super(false);
        setFocusable(true);
        this.image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        setSize(640, 480);
        setPreferredSize(new Dimension(640, 480));
//        addKeyListener(new KeyAdapter() {
//
//            private final KeyCodeTranslator translator = new KeyCodeTranslator();
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                handle(translator.translate(e.getKeyCode(), false));
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                handle(translator.translate(e.getKeyCode(), true));
//            }
//
//            private void handle(byte[] bytes) {
//                if (bytes != null) {
//                    for (byte b : bytes) {
//                        inputBuffer.offer(b);
//                    }
//                }
//            }
//
//        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public Graphics createGraphics() {
        return image.createGraphics();
    }

    public byte readInput() {
        return inputBuffer.isEmpty() ? 0 : inputBuffer.poll();
    }

}
