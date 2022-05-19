package dev.enbyte.filingsaucerclient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadFile {

    private UploadFile(String fileString) {
        try {
        // Create client and upload
            Matcher strMatcher;
            Matcher tokMatcher;
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost uploadFile = new HttpPost(Client.getAddress() + "upload");
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                // Attach File
                File f = new File(fileString);
                builder.addBinaryBody(
                        "file",
                        new FileInputStream(f),
                        ContentType.APPLICATION_OCTET_STREAM,
                        f.getName()
                );

                // POST file and get response.
                HttpEntity multipart = builder.build();
                uploadFile.setEntity(multipart);
                CloseableHttpResponse response = httpClient.execute(uploadFile);
                HttpEntity responseEntity = response.getEntity();

                // Response parsing.
                String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(responseString, JsonObject.class);
                String toMatch = json.get("message").toString();

                // Match string
                Pattern strPattern = Pattern.compile("(?<=\\\")(.*?)(?=\\|)");
                strMatcher = strPattern.matcher(toMatch);
                if (strMatcher.find()) {
                    System.out.println("[INFO] File name retrieved: " + strMatcher.group(1));
                }
                // Match token
                Pattern tokPattern = Pattern.compile("(?<=\\|)(.*?)(?=\\\")");
                tokMatcher = tokPattern.matcher(toMatch);
                if (tokMatcher.find()) {
                    System.out.println("[INFO] Deletion token retrieved: " + tokMatcher.group(1));
                }
                // Copy link to clipboard
                String url = Client.getAddress() + "view/" + strMatcher.group(1);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(url), null);
                JOptionPane.showMessageDialog(new JFrame(), "Upload Success! The link has been copied to your clipboard!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            // Create registry entry as file.
            try (PrintWriter writer = new PrintWriter(System.getProperty("user.home") + File.separator + "FilingSaucer" + File.separator + ".registry" + File.separator + tokMatcher.group(1), StandardCharsets.UTF_8)) {
                writer.println(strMatcher.group(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), """
                    Something went wrong!
                    
                    Please make sure you've specified an address for Filing Saucer's server instance
                    in the client configuration, and that it is online!
                    
                    Also verify that the provided address is correctly formatted, e.x.:
                    http://000.000.000.000:8080/ NOT: http://000.000.000.000:8080""", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String fileString) throws IOException {
        new UploadFile(fileString);
    }
}
