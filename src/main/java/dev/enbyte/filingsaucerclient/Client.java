package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

    Client() throws IOException {
        // Setup folders for config and registry.
        FilesystemSetup fss = new FilesystemSetup();
        fss.homeSetup();
        fss.registrySetup();
        fss.fileSetup();

        // Setup GUI.
        uploadButton.addActionListener(this);
        refreshButton.addActionListener(this);
        openButton.addActionListener(this);
        deleteButton.addActionListener(this);
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

    public static String getAddress() throws IOException {
        File addressConfig = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + "addressConfig.txt");
        String addr = Files.readString(Path.of(addressConfig.getPath()), StandardCharsets.UTF_8);
        System.out.println(addr);
        return addr;
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
        if (e.getSource() == openButton) {
            ButtonFunctions bf = new ButtonFunctions();
            try {
                bf.OpenButton();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource() == deleteButton) {
            ButtonFunctions bf = new ButtonFunctions();
            try {
                bf.DeleteButton();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
