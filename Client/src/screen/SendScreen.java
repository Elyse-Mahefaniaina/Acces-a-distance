package screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SendScreen extends Thread {
    Socket socket;
    DataOutputStream os;
    boolean loop = true;

    public SendScreen(Socket socket) {
        this.socket = socket;

        start();
    }

    public void run() {
        Robot robot = null;
        Image image = null;
        Rectangle sizeScreen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            os = new DataOutputStream(socket.getOutputStream());
            robot = new Robot();

        } catch (IOException | AWTException e) {
            throw new RuntimeException(e);
        }
        while (loop) {
            try {
                image = robot.createScreenCapture(sizeScreen);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                ImageIO.write((RenderedImage) image, "png", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                os.write(size);
                os.write(byteArrayOutputStream.toByteArray());
                os.flush();

            }catch (IOException ignored) { }
        }
    }
}
