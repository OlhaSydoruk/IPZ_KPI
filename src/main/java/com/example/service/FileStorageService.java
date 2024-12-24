package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String storagePath = "/files"; // Volume у Docker

    public String saveFile(MultipartFile file, Long id) throws IOException {
        String fileName = "author_" + id + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(storagePath, fileName);
        Files.createDirectories(filePath.getParent());
        file.transferTo(filePath.toFile());
        return filePath.toString();
    }

    public byte[] loadFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    public void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }
}
