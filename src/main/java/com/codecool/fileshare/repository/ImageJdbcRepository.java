package com.codecool.fileshare.repository;

import org.apache.tomcat.util.codec.binary.Base64;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Component("jdbc")
public class ImageJdbcRepository implements ImageRepository{
    private final PGSimpleDataSource pgds;

    public ImageJdbcRepository() throws SQLException {
        pgds=new PGSimpleDataSource();
        pgds.setServerNames(new String[]{System.getenv("SERVER")});
        pgds.setPortNumbers(new int[] {Integer.parseInt(System.getenv("PORT"))});
        pgds.setDatabaseName(System.getenv("DATABASE"));
        pgds.setUser(System.getenv("LOGIN"));
        pgds.setPassword(System.getenv("PASSWORD"));
        System.out.println("Trying to connect...");
        pgds.getConnection().close();
        System.out.println("Connection OK");
    }

    @Override
    public String storeImage(String category, String content)  {
        // implement store image in database here
        // content is base64 coded image file
        // generate and return uuid of stored image
        // https://www.base64-image.de/
        // https://codebeautify.org/base64-to-image-converter
        System.out.println(category+"\n"+content);
        String sql="INSERT INTO art_gallery VALUES(?,?,?)";
        String uuid="";
        try {;
            PreparedStatement ps = pgds.getConnection().prepareStatement(sql);
            uuid=uuidFromBase64(content);
            ps.setString(1,uuid);
            ps.setString(2,category);
            ps.setBytes(3,content.getBytes(StandardCharsets.UTF_8));
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return uuid;
    }

    private String uuidFromBase64(String str) {
        byte[] bytes = Base64.decodeBase64(str);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }

    @Override
    public String readImage(String uuid) {
        String sql="SELECT image from art_gallery WHERE uuid=?;";
        String forTheReturn="";
        try {;
            PreparedStatement ps = pgds.getConnection().prepareStatement(sql);
            ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            byte[] bytes=rs.getBytes(1);
            forTheReturn=new String(bytes, StandardCharsets.UTF_8);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return forTheReturn;

    }
}
