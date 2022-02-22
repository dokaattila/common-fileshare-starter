package com.codecool.fileshare.repository;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component("file")
public class ImageFileRepository implements ImageRepository {

    private final String path = "src/main/resources/files/";

    public String storeImage(String category, String content) {
        UUID uuid = generateUUID(content);
        File file = new File(this.path + uuid);
        try {
            FileUtils.write(file, category + ":" + content + "\n", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuid.toString();
    }

    public String readImage(String uuid) {
        File file = new File(this.path + uuid);
        String[] readFileArray = new String[0];
        try {
            String readFile = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            readFileArray = readFile.split(":");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFileArray[1];
    }

    @Override
    public String readCategory(String uuid) {
        File file = new File(this.path + uuid);
        String[] readFileArray = new String[0];
        try {
            String readFile = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            readFileArray = readFile.split(":");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFileArray[0];
    }

    private UUID generateUUID(String str) {
        byte[] bytes = Base64.decodeBase64(str);
        return UUID.nameUUIDFromBytes(bytes);
    }
}