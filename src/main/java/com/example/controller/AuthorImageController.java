package com.example.controller;

import com.example.model.Author;
import com.example.repository.AuthorRepository;
import com.example.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api/authors")
public class AuthorImageController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // Завантаження зображення у файлову систему
    @PostMapping("/{id}/upload-photo-file")
    public ResponseEntity<String> uploadPhotoToFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (!isSupportedFormat(file.getContentType())) {
            return ResponseEntity.badRequest().body("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
        }

        String filePath = fileStorageService.saveFile(file, id);
        return ResponseEntity.ok("Photo saved at: " + filePath);
    }

    // Завантаження зображення у базу даних
    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<String> uploadPhotoToDatabase(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (!isSupportedFormat(file.getContentType())) {
            return ResponseEntity.badRequest().body("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
        }

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        author.setPhoto(file.getBytes());
        authorRepository.save(author);

        return ResponseEntity.ok("Photo uploaded successfully to database.");
    }

    // Перегляд зображення з бази даних
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getPhotoFromDatabase(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        byte[] photo = author.getPhoto();
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photo);
    }

    // Видалення зображення з бази даних
    @DeleteMapping("/{id}/photo")
    public ResponseEntity<String> deletePhotoFromDatabase(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        author.setPhoto(null);
        authorRepository.save(author);

        return ResponseEntity.ok("Photo deleted successfully from database.");
    }

    // Видалення зображення з файлової системи
    @DeleteMapping("/{id}/photo-file")
    public ResponseEntity<String> deletePhotoFromFile(@PathVariable Long id, @RequestParam("path") String filePath) {
        try {
            // Видалення файлу за вказаним шляхом
            fileStorageService.deleteFile(filePath);
            return ResponseEntity.ok("Photo deleted successfully from file system.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete photo: " + e.getMessage());
        }
    }


    private boolean isSupportedFormat(String contentType) {
        if (contentType == null) return false;
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                contentType.equals(MediaType.IMAGE_GIF_VALUE);
    }
}
