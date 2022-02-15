package com.codecool.fileshare;

import com.codecool.fileshare.repository.ImageJdbcRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;


@SpringBootApplication
public class FileshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileshareApplication.class, args);
       /*try {
            ImageJdbcRepository img = new ImageJdbcRepository();
            String myStr = "";
            try {
                myStr = new String(Files.readAllBytes(Paths.get("img")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //img.storeImage("Expressionism", myStr);
            System.out.println(img.readImage("ffd8ffe0-0010-4a46-4946-000101010258"));
        } catch (
                SQLException exception) {
            exception.printStackTrace();
        }
*/
    }
}

