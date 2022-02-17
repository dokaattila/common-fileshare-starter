package com.codecool.fileshare.repository;

public interface ImageRepository {
     String storeImage(String category, String content,String extension);
     String readImage(String uuid);
     String readExtension(String uuid);
}
