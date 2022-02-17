package com.codecool.fileshare.controller;

import com.codecool.fileshare.dto.ImageDTO;
import com.codecool.fileshare.entity.Image;
import com.codecool.fileshare.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/{category}")
    public String storeImage(@RequestBody ImageDTO imageDTO, @PathVariable("category") String category) {
        Image img = imageService.storeImage(imageDTO, category);
        return img.getId().toString();
    }

    @GetMapping("/{uuid}")
    public ImageDTO getImage(@PathVariable("uuid") String uuid) {
        Image img = imageService.getImage(uuid);
        return new ImageDTO(new String(img.getContent(), StandardCharsets.UTF_8));
    }
}
