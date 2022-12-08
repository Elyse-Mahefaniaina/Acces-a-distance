package main;

import execute.ReceiveEvent;
import screen.SendScreen;

import javax.swing.JOptionPane;
import java.awt.AWTException;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, AWTException {

        String ip = JOptionPane.showInputDialog("Entrer l'adresse ip du serveur");
        Socket socket = new Socket(ip, 5000);
        System.out.println("Connection etablie au server "+ip);

        new SendScreen(socket);
        new ReceiveEvent(socket);
    }
}