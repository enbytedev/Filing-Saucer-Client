package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Client {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Client();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Client() throws IOException {
        // Setup folders for config and registry.
        FilesystemSetup fss = new FilesystemSetup();
        fss.fullSetup();

        // Creates a GUI
        GUI gui = new GUI();
        gui.gui();
    }


    public static String getAddress() throws IOException {
        File addressConfig = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + "addressConfig.txt");
        String addr = Files.readString(Path.of(addressConfig.getPath()), StandardCharsets.UTF_8);
        if (addr != "") {
            if (!addr.matches("/^(.*)\\/$/g")) {
                return addr;
            } else {
                // TODO
                //JOptionPane.showMessageDialog(new JFrame(), "Potential issue!\nPlease include a forward slash after your URL in HOME/FilingSaucer/addressConfig.txt!", "Error!", JOptionPane.INFORMATION_MESSAGE);
                return addr;
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Potential issue!\nPlease make sure your URL is in HOME/FilingSaucer/addressConfig.txt!", "Error!", JOptionPane.INFORMATION_MESSAGE);
            return addr;
        }
    }

}
