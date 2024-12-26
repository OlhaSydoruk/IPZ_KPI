package com.example.authorarticlesproject.service;

import com.example.authorarticlesproject.model.Author;
import com.example.authorarticlesproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AuthorImageService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public String uploadMultiplePhotosToFile(Long id, MultipartFile[] files) throws IOException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        for (MultipartFile file : files) {
            if (!isSupportedFormat(file.getContentType())) {
                throw new IllegalArgumentException("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
            }

            String filePath = fileStorageService.saveFile(file, id);
            author.getPhotoPaths().add(filePath);
        }
        authorRepository.save(author);

        return IntStream.range(0, author.getPhotoPaths().size())
                .mapToObj(i -> i + " " + author.getPhotoPaths().get(i))
                .collect(Collectors.joining("\n"));
    }

    public void uploadMultiplePhotosToDatabase(Long id, MultipartFile[] files) throws IOException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        for (MultipartFile file : files) {
            if (!isSupportedFormat(file.getContentType())) {
                throw new IllegalArgumentException("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
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
    }

    public byte[] getPhotoFromDatabase(Long id, int index) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (index < 0 || index >= author.getPhotos().size()) {
            throw new IllegalArgumentException("Invalid photo index.");
        }

        return author.getPhotos().get(index);
    }



    public byte[] getPhotoFromFile(Long id, String filePath) throws IOException {
        return fileStorageService.loadFile(filePath);
    }

    public void deletePhotoFromDatabase(Long id, int index) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (index < 0 || index >= author.getPhotos().size()) {
            throw new IllegalArgumentException("Invalid photo index.");
        }

        author.getPhotos().remove(index);
        authorRepository.save(author);
    }



    public void deletePhotoFromFile(Long id, String filePath) throws IOException {
        fileStorageService.deleteFile(filePath);
    }



    public void replacePhotoInDatabase(Long id, int index, MultipartFile file) throws IOException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        if (!isSupportedFormat(file.getContentType())) {
            throw new IllegalArgumentException("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
        }

        if (index < 0 || index >= author.getPhotos().size()) {
            throw new IllegalArgumentException("Invalid photo index.");
        }

        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage resizedImage = fileStorageService.resizeImage(originalImage, 800, 600);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        baos.flush();
        byte[] optimizedImage = baos.toByteArray();
        baos.close();

        author.getPhotos().set(index, optimizedImage);
        authorRepository.save(author);
    }


    public String replacePhotoInFileSystem(Long id, String oldPath, MultipartFile file) throws IOException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        if (!isSupportedFormat(file.getContentType())) {
            throw new IllegalArgumentException("Unsupported file format. Only JPEG, PNG, and GIF are allowed.");
        }

        fileStorageService.deleteFile(oldPath);
        String newFilePath = fileStorageService.saveFile(file, id);

        if (!author.getPhotoPaths().remove(oldPath)) {
            throw new RuntimeException("Old photo path not found in author's records.");
        }
        author.getPhotoPaths().add(newFilePath);
        authorRepository.save(author);
        return newFilePath;
    }

    private boolean isSupportedFormat(String contentType) {
        if (contentType == null) return false;
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                contentType.equals(MediaType.IMAGE_GIF_VALUE);
    }
}
