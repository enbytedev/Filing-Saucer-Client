package dev.enbyte.filingsaucerclient;

import java.io.File;
import java.io.IOException;

public class FilesystemSetup {

    public void fullSetup() throws IOException {
        homeSetup();
        registrySetup();
        fileSetup();
    }
    public void homeSetup() {
        // Create application folder if one does not exist.
        File FilingSaucerFolder = new File(System.getProperty("user.home") + File.separator + "FilingSaucer");
        if (!FilingSaucerFolder.exists()) {
            FilingSaucerFolder.mkdirs();
            System.out.println("[INFO - FilesystemSetup] Created folder /FilingSaucer in user home directory.");
        } else {
            System.out.println("[INFO - FilesystemSetup] Did not create folder /FilingSaucer in user home directory, already exists.");
        }
    }
    public void registrySetup() {
        // Create registry folder if one does not exist.
        File RegistryFolder = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + ".registry");
        if (!RegistryFolder.exists()) {
            RegistryFolder.mkdirs();
            System.out.println("[INFO - FilesystemSetup] Created folder /.registry in FilingSaucer directory.");
        } else {
            System.out.println("[INFO - FilesystemSetup] Did not create folder /.registry in FilingSaucer directory, already exists.");
        }
    }
    public void fileSetup() throws IOException {
        // Create other files if they do not exist.
        File RegistryFile = new File(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + "addressConfig.txt");
        if (!RegistryFile.exists()) {
            RegistryFile.createNewFile();
            System.out.println("[INFO - FilesystemSetup] Created file /addressConfig.txt in FilingSaucer directory.");
        } else {
            System.out.println("[INFO - FilesystemSetup] Did not create file /addressConfig.txt in FilingSaucer directory, already exists.");
        }
    }
}
