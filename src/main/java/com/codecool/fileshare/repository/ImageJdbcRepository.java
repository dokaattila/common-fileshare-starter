package com.codecool.fileshare.repository;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Component("jdbc")
public class ImageJdbcRepository implements ImageRepository{
    private final DataSource dataSource;

    public ImageJdbcRepository(){
        dataSource=initDataSource();
        System.out.println(dataSource);
    }

    @Override
    public String storeImage(String category, String content)  {
        System.out.println(category+"\n"+content);
        String sql="INSERT INTO art_gallery VALUES(?,?,?)";
        String uuid="";
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            uuid=uuidFromBase64(content);
            ps.setString(1,uuid);
            ps.setString(2,category);
            ps.setBytes(3,content.getBytes(StandardCharsets.UTF_8));
            ps.execute();
            ps.getConnection().close();
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
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            byte[] bytes=rs.getBytes(1);
            forTheReturn=new String(bytes, StandardCharsets.UTF_8);
            ps.getConnection().close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return forTheReturn;
    }

    private DataSource initDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(System.getenv("DRIVER_CLASS_NAME"))
                .url(System.getenv("URL"))
                .username(System.getenv("LOGIN"))
                .password(System.getenv("PASSWORD"))
                .build();
    }
}
