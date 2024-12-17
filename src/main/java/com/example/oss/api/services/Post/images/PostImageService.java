package com.example.oss.api.services.Post.images;

import com.example.oss.api.dto.PostDto;
import com.example.oss.api.models.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public interface PostImageService {
    String uploadImageToFileSystem(UUID postId, MultipartFile file) throws IOException;

    byte[] getImageFromFileSystem(String filename) throws IOException;

    void deleteImageFromFileSystem(String filename) throws IOException;

    void saveImageToDatabase(UUID postId, MultipartFile file) throws IOException;

    byte[] getImageFromDatabase(UUID postId);

    void deleteImageFromDatabase(UUID postId);

}
