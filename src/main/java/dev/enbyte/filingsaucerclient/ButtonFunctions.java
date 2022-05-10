package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ButtonFunctions {

    public boolean OpenButton() throws IOException {
        String out = JOptionPane.showInputDialog("""
                Specify file to copy link by name.

                Note: this generates a URL and copies it to your clipboard.
                The link will not open if file does not exist.""");
        String url = Client.getAddress() + "files/" + out;
        if (!out.equals("")) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(url), null);
            JOptionPane.showMessageDialog(new JFrame(), "The link has been copied to your clipboard!\n Note: The URL will not work unless the file input is valid!", "Success!", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
    public boolean DeleteButton() throws IOException {
        try {
        String out = JOptionPane.showInputDialog("Specify file to delete by token.");
            if (!out.equals("")) {
                URL url = new URL(Client.getAddress() + "delete/" + out);
                InputStream is = url.openStream();
                is.close();
                File regEntry = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + ".registry" + File.separator + out);
                regEntry.delete();
                JOptionPane.showMessageDialog(new JFrame(), "The specified file has been sent a request to be deleted!\n Note: This does not ensure deletion! Please verify proper removal on your own time!", "Success!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), """
                    Something went wrong!
                    
                    Please make sure you've specified an address for Filing Saucer's server instance
                    in the client configuration, and that it is online!
                    
                    Also verify that the provided address is correctly formatted, e.x.:
                    http://000.000.000.000:8080/ NOT: http://000.000.000.000:8080""", "Error!", JOptionPane.ERROR_MESSAGE);

        }
        return true;
    }

}
