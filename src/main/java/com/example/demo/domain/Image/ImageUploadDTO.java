package com.example.demo.domain.Image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageUploadDTO {
    private List<MultipartFile> files;
}