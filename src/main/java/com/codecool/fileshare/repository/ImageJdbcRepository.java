package com.codecool.fileshare.repository;

import com.codecool.fileshare.entity.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface ImageJdbcRepository extends CrudRepository<Image, UUID> {
    // Such emptiness...
}
