package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI implements ActionListener {

    JFrame mainView = new JFrame("Filing Saucer Client");
    JFrame historyView = new JFrame("History");
    JButton uploadButton = new JButton("Upload File");
    JButton openButton = new JButton("Open File");
    JButton deleteButton = new JButton("Delete File");
    JButton historyButton = new JButton("History / Refresh");
    JPanel mainPanel = new JPanel(new GridLayout(8, 3));
    JPanel historyPanel = new JPanel(new GridLayout(8, 3));

    public void gui() {
        Timer timer = new Timer(20, this);
        timer.start();

        // Setup GUI.
        uploadButton.addActionListener(this);
        historyButton.addActionListener(this);
        openButton.addActionListener(this);
        deleteButton.addActionListener(this);
        uploadButton.setFocusable(false);
        historyButton.setFocusable(false);
        mainPanel.add(uploadButton);
        mainPanel.add(openButton);
        mainPanel.add(deleteButton);
        mainPanel.add(historyButton);

        mainView.add(mainPanel);
        mainView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainView.pack();
        mainView.setVisible(true);
        mainView.setSize(300, 250);
    }

    public void ReadHistory() throws IOException {
        HistoryList historyList = new HistoryList();
        for (int i = 0; i < historyList.tokens.size(); i++) {
            JTextArea fileNames = new JTextArea(historyList.fileName.get(i) + "Token: " + historyList.tokens.get(i));
            fileNames.setLineWrap(true);
            fileNames.setEditable(false);
            historyPanel.add(fileNames);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ButtonFunctions bf = new ButtonFunctions();

        // * Upload
        if (e.getSource() == uploadButton) {
            bf.UploadButton();
        }

        // * Open
        if (e.getSource() == openButton) {
            try {
                bf.OpenButton();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        // * Delete
        if (e.getSource() == deleteButton) {
            try {
                bf.DeleteButton();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        // * History
        if (e.getSource() == historyButton) {
            try {
                historyPanel.removeAll();
                ReadHistory();
                historyView.add(historyPanel);
                historyView.pack();
                historyView.setSize(300, 500);
                historyView.setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("History opened.");
        }
    }
}
