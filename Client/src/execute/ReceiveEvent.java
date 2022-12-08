package execute;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiveEvent extends Thread {

    Socket socketEvent;
    Robot robot;
    DataInputStream is;
    boolean loop = true;

    public ReceiveEvent(Socket socket) throws AWTException, IOException {
        socketEvent = socket;
        robot = new Robot();
        is = new DataInputStream(socketEvent.getInputStream());

        start();
    }

    public void run() {
        while (loop) {
            try {
                int command = is.readInt();
                switch (command) {
                    case 1 -> robot.keyPress(is.readInt());
                    case 2 -> robot.mousePress(is.readInt());
                    case -1 -> robot.keyRelease(is.readInt());
                    case -2 -> robot.mouseRelease(is.readInt());
                    case 3 -> {
                        String coordonne = is.readUTF();
                        int x = Integer.parseInt(coordonne.split("::")[0]);
                        int y = Integer.parseInt(coordonne.split("::")[1]);
                        robot.mouseMove(x, y);
                    }
                }
            } catch (IOException ignored) { }
        }
    }
}
