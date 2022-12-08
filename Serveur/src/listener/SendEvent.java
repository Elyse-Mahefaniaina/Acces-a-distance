package listener;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendEvent implements MouseListener, KeyListener, MouseMotionListener {

    DataOutputStream os;

    public SendEvent(DataOutputStream os) throws IOException {
        this.os = os;

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            os.writeInt(Commands.PRESS_KEY.getAbbrev());
            os.writeInt(e.getKeyCode());
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            os.writeInt(Commands.RELEASED_KEY.getAbbrev());
            os.writeInt(e.getKeyCode());
            os.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            int xbtn = 1024;
            if(e.getButton() == MouseEvent.BUTTON3) {
                xbtn = 4096;
            }
            os.writeInt(Commands.PRESS_MOUSE.getAbbrev());
            os.writeInt(xbtn);
            os.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            int xbtn = 1024;
            if(e.getButton() == MouseEvent.BUTTON3) {
                xbtn = 4096;
            }

            os.writeInt(Commands.RELEASED_MOUSE.getAbbrev());
            os.writeInt(xbtn);
            os.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        try {
            os.writeInt(Commands.MOUVE_MOUSE.getAbbrev());
            os.writeUTF(e.getX()+"::"+e.getY());
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
