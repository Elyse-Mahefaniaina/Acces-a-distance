package screen;

import listener.SendEvent;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Desktop {
    Socket socket;
    JFrame frame = new JFrame();
    Panel panel = new Panel();
    SendEvent se;
    DataOutputStream os;
    DataInputStream in;

    public Desktop(Socket socket) throws IOException {
        this.socket = socket;
        panel.setBorder(BorderFactory.createLineBorder(new Color(0x196793)));

        os = new DataOutputStream(this.socket.getOutputStream());
        in = new DataInputStream(this.socket.getInputStream());

        init();
    }

    public void initFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        frame.add(panel);
        panel.setFocusable(true);
        frame.setFocusable(false);

        panel.addKeyListener(se);
        panel.addMouseListener(se);
        panel.addMouseMotionListener(se);

        frame.setVisible(true);
    }

    public void init() {
        try {
            se = new SendEvent(os);
            new ReceiveScreen(panel, in);
            initFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
