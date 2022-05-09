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
    JButton uploadButton = new JButton("Upload File");
    JButton openButton = new JButton("Open File");
    JButton deleteButton = new JButton("Delete File");
    JButton refreshButton = new JButton("Refresh");
    JPanel panel = new JPanel(new GridLayout(8, 3));
    JPanel panelNames = new JPanel(new GridLayout(1, 4));
    JLabel historyLabel = new JLabel("History", JLabel.CENTER);
    JTextArea fileNames = new JTextArea();

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

        // Setup GUI.
        uploadButton.addActionListener(this);
        refreshButton.addActionListener(this);
        uploadButton.setFocusable(false);
        refreshButton.setFocusable(false);
        panel.add(uploadButton);
        panel.add(openButton);
        panel.add(deleteButton);
        panel.add(refreshButton);

        // Add buttons
        ReadHistory();
        mainView.add(panel);
        mainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainView.pack();
        mainView.setVisible(true);
        mainView.setSize(700, 500);
    }

    public void ReadHistory() throws IOException {
        HistoryList historyList = new HistoryList();
        for (int i = 1; i <= historyList.tokens.size(); i++) {
            JTextArea fileNames = new JTextArea(historyList.fileName.get(i - 1) + "Token: " + historyList.tokens.get(i - 1));
            fileNames.setLineWrap(true);
            fileNames.setEditable(false);
            panel.add(fileNames);
        }
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
