package screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ReceiveScreen extends Thread {
    Panel panel;
    DataInputStream in;
    boolean loop = true;

    public ReceiveScreen(Panel panel, DataInputStream in) {
        this.panel = panel;
        this.in = in;

        start();
    }

    public void run() {
        Image image = null;
        while (loop) {
            try {
                byte[] sizeAr = new byte[4];
                in.readFully(sizeAr);

                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                byte[] imageAr = new byte[size];
                int totalRead = 0;
                int currentRead;
                while (totalRead < size && (currentRead = in.read(imageAr, totalRead, size-totalRead)) > 0) {
                    totalRead += currentRead;
                }

                image = ImageIO.read(new ByteArrayInputStream(imageAr));

                Graphics2D g2d = (Graphics2D) panel.getGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null),null);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
