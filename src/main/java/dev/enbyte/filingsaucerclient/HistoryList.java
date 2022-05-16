package dev.enbyte.filingsaucerclient;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class HistoryList {
    File folder = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + ".registry");
    File[] listOfFiles = folder.listFiles();
    ArrayList<Object> tokens = new ArrayList<>();
    ArrayList<Object> fileName = new ArrayList<>();
    public HistoryList() throws IOException {
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                tokens.add(listOfFile.getName());
                String content = Files.readString(Path.of(listOfFile.getPath()), StandardCharsets.UTF_8);
                fileName.add(content);
            }
        }
    }

    GUI gui = new GUI();
    public void ReadHistory() throws IOException {
        HistoryList historyList = new HistoryList();
        for (int i = 0; i < historyList.tokens.size(); i++) {
            JTextArea fileNames = new JTextArea(historyList.fileName.get(i) + "Token: " + historyList.tokens.get(i));
            fileNames.setLineWrap(true);
            fileNames.setEditable(false);
            gui.historyPanel.add(fileNames);
        }
    }
}
