package dev.enbyte.filingsaucerclient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class HistoryList {
    File folder = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + ".registry");
    File[] listOfFiles = folder.listFiles();
    ArrayList tokens = new ArrayList<>();
    ArrayList fileName = new ArrayList<>();
    public HistoryList() throws IOException {
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                tokens.add(listOfFiles[i].getName());
                String content = Files.readString(Path.of(listOfFiles[i].getPath()), StandardCharsets.UTF_8);
                fileName.add(content);
            }
        }
    }


}
