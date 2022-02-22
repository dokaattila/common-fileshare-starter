package com.codecool.fileshare.service;

import com.codecool.fileshare.dto.ImageDTO;
import com.codecool.fileshare.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    @Qualifier("file")
    ImageRepository imageRepository;

    public String storeImage(ImageDTO imageDTO, String category){
        return imageRepository.storeImage(category, imageDTO.getImageData());
    }

    public ImageDTO getImage(String uuid){
        return  new ImageDTO(imageRepository.readImage(uuid),imageRepository.readCategory(uuid));
    }
}
