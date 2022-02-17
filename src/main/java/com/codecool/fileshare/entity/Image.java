package com.codecool.fileshare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    private UUID id;
    private String category;
    private byte[] content;
}
