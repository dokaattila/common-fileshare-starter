package com.codecool.fileshare.repository;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Component("jdbc")
public class ImageJdbcRepository implements ImageRepository{

    private final DataSource dataSource;

    public ImageJdbcRepository(){
        this.dataSource=initDataSource();
    }

    @Override
    public String storeImage(String category, String content,String extension)  {
        System.out.println(category+"\n"+content);
        String sql="INSERT INTO image (id,category,content,extension) VALUES(?,?,?,?)";
        UUID uuid=null;
        try {
            Connection conn=dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            uuid=uuidFromBase64(content);
            ps.setObject(1,uuid);
            ps.setString(2,category);
            ps.setBytes(3,content.getBytes(StandardCharsets.UTF_8));
            ps.setString(4,extension);
            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        assert uuid != null;
        return uuid.toString();
    }

    private UUID uuidFromBase64(String str) {
        byte[] bytes = Base64.decodeBase64(str);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }

    @Override
    public String readImage(String uuid) {
        String sql="SELECT image from image WHERE id::varchar=?;";
        String imageBase64String="";
        try {
            Connection conn=dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            byte[] bytes=rs.getBytes(1);
            imageBase64String=new String(bytes, StandardCharsets.UTF_8);
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return imageBase64String;
    }

    @Override
    public String readExtension(String uuid) {
        String sql="SELECT extension from image WHERE id::varchar=?;";
        String extensionString="";
        try {
            Connection conn=dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            extensionString=rs.getString(1);
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return extensionString;
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
