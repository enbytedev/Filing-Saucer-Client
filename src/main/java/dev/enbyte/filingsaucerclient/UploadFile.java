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


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadFile {

    String fileString = new String();

    public UploadFile(String fileString) throws IOException {
        // Create client and upload
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://000.000.000.000:0000/upload");
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
        System.out.println(json.get("message"));
        String toMatch = json.get("message").toString();

        // Match string
        Pattern strPattern = Pattern.compile("(?<=\\\")(.*?)(?=\\|)");
        Matcher strMatcher = strPattern.matcher(toMatch);
        if (strMatcher.find())
        {
            System.out.println(strMatcher.group(1));
        }
        // Match token.
        Pattern tokPattern = Pattern.compile("(?<=\\|)(.*?)(?=\\\")");
        Matcher tokMatcher = tokPattern.matcher(toMatch);
        if (tokMatcher.find())
        {
            System.out.println(tokMatcher.group(1));
        }

    }

    public static void main(String fileString) throws IOException {
        new UploadFile(fileString);
    }
}
