package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Client implements ActionListener {

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

    JFrame mainView = new JFrame("Filing Saucer Client");
    JButton uploadButton = new JButton("Select File");
    JPanel panel = new JPanel(new GridLayout(4, 5));
    JLabel title = new JLabel("Filing Saucer Client", JLabel.CENTER);

    Client() throws IOException {
        // Create registry folder if one does not exist.
        File home = new File(System.getProperty("user.home"));
        File FilingSaucerFolder = new File(System.getProperty("user.home") + File.separator + "FilingSaucer");
        if (!FilingSaucerFolder.exists()){
            FilingSaucerFolder.mkdirs();
            System.out.println("[INFO] Created folder FilingSaucer in user home directory.");
        } else {
            System.out.println("[INFO] Did not create folder FilingSaucer in user home directory, already exists.");
        }
        HistoryList historyList = new HistoryList();
        System.out.println(historyList);

        // Setup GUI.
        panel.add(title);

        uploadButton.setBounds(0, 0, 50, 20);
        uploadButton.addActionListener(this);
        uploadButton.setFocusable(false);
        panel.add(uploadButton);

        mainView.add(panel);
        mainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainView.pack();
        mainView.setVisible(true);
        mainView.setSize(300, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            JFileChooser fileChooser = new JFileChooser();
            // Open a file picker in user's home folder.
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(mainView);
            // If a file is selected, upload it with UploadFile.
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println("[INFO] File selected, starting upload!");
                File selectedFile = fileChooser.getSelectedFile();
                // The filename is made a string and passed into UploadFile.
                String fileString = selectedFile.toString();
                try {
                    UploadFile.main(fileString);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
