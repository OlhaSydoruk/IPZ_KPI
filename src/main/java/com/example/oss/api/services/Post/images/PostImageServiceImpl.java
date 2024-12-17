package com.example.oss.api.services.Post.images;

import com.example.oss.api.dto.PostDto;
import com.example.oss.api.models.Post;
import com.example.oss.api.repository.factory.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;


@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService{
    @Value("${image.upload-dir}")
    private String uploadDir;

    private FactoryRepository fr;

    @Override
    public String uploadImageToFileSystem(UUID postId, MultipartFile file) throws IOException {
        if (!isValidImage(file)) {
            throw new IOException("Invalid image format. Only JPEG, PNG, or GIF allowed.");
        }

        String fileName = postId + "_" + file.getOriginalFilename() + Objects.hash(uploadDir, file);;
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Post post = fr.getPostRepository().findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setImagePath(path.toString());
        fr.getPostRepository().save(post);

        return fileName;
    }

    @Override
    public byte[] getImageFromFileSystem(String filename) throws IOException {
        Path path = Paths.get(uploadDir, filename);
        return Files.readAllBytes(path);
    }

    @Override
    public void deleteImageFromFileSystem(String filename) throws IOException {
        Path path = Paths.get(uploadDir, filename);
        Files.deleteIfExists(path);
    }

    @Override
    public void saveImageToDatabase(UUID postId, MultipartFile file) throws IOException {
        if (!isValidImage(file)) {
            throw new IOException("Invalid image format.");
        }

        byte[] imageData = file.getBytes();
        Post post = fr.getPostRepository().findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setImageData(imageData);
        fr.getPostRepository().save(post);
    }

    @Override
    public byte[] getImageFromDatabase(UUID postId) {
        Post post = fr.getPostRepository().findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getImageData();
    }

    @Override
    public void deleteImageFromDatabase(UUID postId) {
        Post post = fr.getPostRepository().findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setImageData(null);
        fr.getPostRepository().save(post);
    }

    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif"));
    }

}
