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
