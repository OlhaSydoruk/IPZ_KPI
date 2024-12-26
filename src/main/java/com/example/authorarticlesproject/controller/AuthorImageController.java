package com.example.authorarticlesproject.controller;

import com.example.authorarticlesproject.service.AuthorImageService;
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
    private AuthorImageService authorImageService;

    @PostMapping("/{id}/upload-multiple-photos-file-system")
    public ResponseEntity<String> uploadMultiplePhotosToFile(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile[] files) {
        try {
            String result = authorImageService.uploadMultiplePhotosToFile(id, files);
            return ResponseEntity.ok("Photos uploaded successfully to the file system.\n" + result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload photos: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/upload-multiple-photos-db")
    public ResponseEntity<String> uploadMultiplePhotosToDatabase(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile[] files) {
        try {
            authorImageService.uploadMultiplePhotosToDatabase(id, files);
            return ResponseEntity.ok("Photos uploaded successfully to the database.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload photos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/photo/{index}")
    public ResponseEntity<byte[]> getPhotoFromDatabase(
            @PathVariable Long id, @PathVariable int index) {
        try {
            byte[] photo = authorImageService.getPhotoFromDatabase(id, index);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}/photo-file")
    public ResponseEntity<byte[]> getPhotoFromFile(@PathVariable Long id, @RequestParam("path") String filePath) {
        try {
            byte[] image = authorImageService.getPhotoFromFile(id, filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}/photo/{index}")
    public ResponseEntity<String> deletePhotoFromDatabase(
            @PathVariable Long id, @PathVariable int index) {
        try {
            authorImageService.deletePhotoFromDatabase(id, index);
            return ResponseEntity.ok("Photo deleted successfully from database.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/photo-file")
    public ResponseEntity<String> deletePhotoFromFile(@PathVariable Long id, @RequestParam("path") String filePath) {
        try {
            authorImageService.deletePhotoFromFile(id, filePath);
            return ResponseEntity.ok("Photo deleted successfully from file system.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete photo: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/replace-photo-db/{index}")
    public ResponseEntity<String> replacePhotoInDatabase(
            @PathVariable Long id, @PathVariable int index, @RequestParam("file") MultipartFile file) {
        try {
            authorImageService.replacePhotoInDatabase(id, index, file);
            return ResponseEntity.ok("Photo replaced successfully in the database.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to replace photo: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/replace-photo-file")
    public ResponseEntity<String> replacePhotoInFileSystem(
            @PathVariable Long id, @RequestParam("oldPath") String oldPath, @RequestParam("file") MultipartFile file) {
        try {
            String newFilePath = authorImageService.replacePhotoInFileSystem(id, oldPath, file);
            return ResponseEntity.ok("Photo replaced successfully in the file system. \n "+newFilePath);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to replace photo: " + e.getMessage());
        }
    }
}
