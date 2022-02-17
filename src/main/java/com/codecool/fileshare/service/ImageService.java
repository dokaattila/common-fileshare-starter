package com.codecool.fileshare.service;

import com.codecool.fileshare.Util;
import com.codecool.fileshare.dto.ImageDTO;
import com.codecool.fileshare.entity.Image;
import com.codecool.fileshare.repository.ImageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    ImageJdbcRepository imageJdbcRepository;

    public Image storeImage(ImageDTO imageDTO, String category) {
        UUID uuid = Util.uuidFromBase64(imageDTO.getImageData());
        return imageJdbcRepository.save(new Image(uuid, category, imageDTO.getImageData().getBytes(StandardCharsets.UTF_8)));
    }

    public Image getImage(String uuid) {
        // TODO: Make .get() call safe here
        return imageJdbcRepository.findById(UUID.fromString(uuid)).get();
    }
}
