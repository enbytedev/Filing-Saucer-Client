package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Client implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }

    JFrame mainView = new JFrame();
    JButton uploadButton = new JButton("Select File");
    Client() {
        mainView.setSize(300, 500);
        uploadButton.setBounds(0, 0, 100, 20);

        uploadButton.addActionListener(this);

        mainView.add(uploadButton);
        uploadButton.setFocusable(false);
        mainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainView.setLayout(null);
        mainView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(mainView);
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println("File selected.");
                File selectedFile = fileChooser.getSelectedFile();
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
