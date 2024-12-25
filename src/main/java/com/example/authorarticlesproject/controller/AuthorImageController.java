package com.example.authorarticlesproject.controller;

import com.example.authorarticlesproject.model.Author;
import com.example.authorarticlesproject.repository.AuthorRepository;
import com.example.authorarticlesproject.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/authors")
public class AuthorImageController {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // Upload multiple images to the file system
    @PostMapping("/{id}/upload-multiple-photos-file-system")
    public ResponseEntity<String> uploadMultiplePhotosToFile(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        for (MultipartFile file : files) {
            if (!isSupportedFormat(file.getContentType())) {
                return ResponseEntity.badRequest().body("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
            }

            String filePath = fileStorageService.saveFile(file, id);
            author.getPhotoPaths().add(filePath);
        }
        authorRepository.save(author);

        String photoPaths = IntStream.range(0, author.getPhotoPaths().size())
                .mapToObj(i -> i + " " + author.getPhotoPaths().get(i))
                .collect(Collectors.joining("\n"));


        return ResponseEntity.ok("Photos uploaded successfully to the file system. \n " + photoPaths);
    }



    @PostMapping("/{id}/upload-multiple-photos-db")
    public ResponseEntity<String> uploadMultiplePhotosToDatabase(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        for (MultipartFile file : files) {
            if (!isSupportedFormat(file.getContentType())) {
                return ResponseEntity.badRequest().body("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
            }

            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage resizedImage = fileStorageService.resizeImage(originalImage, 800, 600);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            baos.flush();
            byte[] optimizedImage = baos.toByteArray();
            baos.close();

            author.getPhotos().add(optimizedImage);
        }
        authorRepository.save(author);

        return ResponseEntity.ok("Photos uploaded successfully to the database.");
    }

    // Retrieve a specific photo from the database by index
    @GetMapping("/{id}/photo/{index}")
    public ResponseEntity<byte[]> getPhotoFromDatabase(
            @PathVariable Long id, @PathVariable int index) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (index < 0 || index >= author.getPhotos().size()) {
            return ResponseEntity.badRequest().body(null);
        }

        byte[] photo = author.getPhotos().get(index);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photo);
    }

    // Retrieve a photo from the file system by providing its path
    @GetMapping("/{id}/photo-file")
    public ResponseEntity<byte[]> getPhotoFromFile(@PathVariable Long id, @RequestParam("path") String filePath) {
        try {
            byte[] image = fileStorageService.loadFile(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Delete a specific photo from the database by index
    @DeleteMapping("/{id}/photo/{index}")
    public ResponseEntity<String> deletePhotoFromDatabase(
            @PathVariable Long id, @PathVariable int index) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (index < 0 || index >= author.getPhotos().size()) {
            return ResponseEntity.badRequest().body("Invalid photo index.");
        }

        author.getPhotos().remove(index);
        authorRepository.save(author);

        return ResponseEntity.ok("Photo deleted successfully from database.");
    }

    // Delete a specific photo from the file system
    @DeleteMapping("/{id}/photo-file")
    public ResponseEntity<String> deletePhotoFromFile(@PathVariable Long id, @RequestParam("path") String filePath) {
        try {
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
