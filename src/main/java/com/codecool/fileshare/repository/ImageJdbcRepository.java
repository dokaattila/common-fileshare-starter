package com.codecool.fileshare.repository;

import org.postgresql.util.ByteStreamWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;


@Component("jdbc")
public class ImageJdbcRepository implements ImageRepository{


    @Override
    public String storeImage(String category, String content) {
        // implement store image in database here
        // content is base64 coded image file
        // generate and return uuid of stored image
        // https://www.base64-image.de/
        // https://codebeautify.org/base64-to-image-converter
        ConnectionData cn = new ConnectionData();
        Connection con = cn.getConnection();
        try {
            PreparedStatement prep = con.prepareStatement("Insert into image (category, content, extension) " +
                    "values(?,?,?)");
            prep.setString(1,category);
            byte[] decode = Base64.getDecoder().decode(content);
            prep.setBinaryStream(2, new ByteArrayInputStream(decode),decode.length);
            prep.setString(3,"jpg");
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public String readImage(String uuid) {
        //94b77df8-23d0-41dd-a426-fb1e38a82847
        // implement readImage from database here
        // return base64 encoded image

        ConnectionData cn = new ConnectionData();
        Connection con = cn.getConnection();
        try {
            PreparedStatement prep = con.prepareStatement("SELECT content from image WHERE id::varchar = ?;");
            prep.setString(1,uuid);
            ResultSet res = prep.executeQuery();
            res.next();
            byte[] image = res.getBytes("content");
            return new String(image, StandardCharsets.UTF_8);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
