package com.example.oss.api.controllers;

import com.example.oss.api.services.Post.images.PostImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController("postImageController")
@RequestMapping("/post-images")
public class PostImageController {
    private PostImageService postImageService;

    @PostMapping("/uploadToFile/{postId}")
    public ResponseEntity<String> uploadToFileSystem(@PathVariable UUID postId, @RequestParam("file") MultipartFile file) throws IOException {
        String filename = postImageService.uploadImageToFileSystem(postId, file);
        return ResponseEntity.ok("Image saved to file system: " + filename);
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<byte[]> getImageFromFileSystem(@PathVariable String filename) throws IOException {
        byte[] image = postImageService.getImageFromFileSystem(filename);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/file/{filename}")
    public ResponseEntity<String> deleteImageFromFileSystem(@PathVariable String filename) throws IOException {
        postImageService.deleteImageFromFileSystem(filename);
        return ResponseEntity.ok("Image deleted from file system.");
    }

    @PostMapping("/uploadToDB/{postId}")
    public ResponseEntity<String> uploadToDatabase(@PathVariable UUID postId, @RequestParam("file") MultipartFile file) throws IOException {
        postImageService.saveImageToDatabase(postId, file);
        return ResponseEntity.ok("Image saved to database.");
    }

    @GetMapping("/db/{postId}")
    public ResponseEntity<byte[]> getImageFromDatabase(@PathVariable UUID postId) {
        byte[] image = postImageService.getImageFromDatabase(postId);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/db/{postId}")
    public ResponseEntity<String> deleteImageFromDatabase(@PathVariable UUID postId) {
        postImageService.deleteImageFromDatabase(postId);
        return ResponseEntity.ok("Image deleted from database.");
    }
}
