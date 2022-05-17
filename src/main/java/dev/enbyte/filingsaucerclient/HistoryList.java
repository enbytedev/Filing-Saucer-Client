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
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                tokens.add(listOfFile.getName());
                String content = Files.readString(Path.of(listOfFile.getPath()), StandardCharsets.UTF_8);
                fileName.add(content);
            }
        }
    }
}
