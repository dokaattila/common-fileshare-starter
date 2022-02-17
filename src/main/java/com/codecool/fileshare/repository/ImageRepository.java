package com.codecool.fileshare.repository;

public interface ImageRepository {
     String storeImage(String category, String content);
     String readImage(String uuid);
}
