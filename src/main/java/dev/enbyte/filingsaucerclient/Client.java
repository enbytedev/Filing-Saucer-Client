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
    JButton refreshButton = new JButton("Refresh");
    JPanel panel = new JPanel(new GridLayout(3, 1));
    JPanel panelOpen = new JPanel(new GridLayout(3, 1));
    JPanel panelDelete = new JPanel(new GridLayout(3, 1));
    JLabel openLabel = new JLabel("Open File", JLabel.CENTER);
    JLabel deleteLabel = new JLabel("Delete File", JLabel.CENTER);


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
        panel.add(refreshButton);
        panelOpen.add(openLabel);
        panelDelete.add(deleteLabel);

        // Add buttons
        ReadHistory();

        panel.add(panelOpen);
        panel.add(panelDelete);
        mainView.add(panel);
        mainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainView.pack();
        mainView.setVisible(true);
        mainView.setSize(700, 500);
    }

    public void repaint() throws IOException {
        ReadHistory();
        mainView.revalidate();
        mainView.repaint();
    }
    public void ReadHistory() throws IOException {
        HistoryList historyList = new HistoryList();
        for (int i = 1; i <= historyList.tokens.size(); i++) {
            JButton openButton = new JButton(historyList.tokens.get(i - 1).toString());
            openButton.setBackground(Color.GREEN);
            openButton.setForeground(Color.BLACK);
            openButton.setHorizontalAlignment(SwingConstants.CENTER);
            panelOpen.add(openButton);
            JButton deleteButton = new JButton(historyList.tokens.get(i - 1).toString());
            deleteButton.setBackground(Color.RED);
            deleteButton.setForeground(Color.BLACK);
            deleteButton.setHorizontalAlignment(SwingConstants.CENTER);
            panelDelete.add(deleteButton);
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
        if (e.getSource() == refreshButton) {
            mainView.repaint();
        }
    }
}
