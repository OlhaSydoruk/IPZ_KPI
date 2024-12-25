package com.example.authorarticlesproject.service;

import com.example.authorarticlesproject.model.Author;
import com.example.authorarticlesproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // Get all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get author by ID
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
    }

    // Save a new author
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Update an existing author
    public Author updateAuthor(Long id, Author author) {
        return authorRepository.findById(id).map(existingAuthor -> {
            existingAuthor.setName(author.getName());
            existingAuthor.setAge(author.getAge());
            existingAuthor.setGender(author.getGender());
            existingAuthor.setArticles_id(author.getArticles_id());
            existingAuthor.setPhotos(author.getPhotos()); // Update the list of photos
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
    }

    // Add a photo to an author's photo list
    public Author addPhotoToAuthor(Long id, byte[] photo) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));

        author.getPhotos().add(photo);
        return authorRepository.save(author);
    }

    // Remove a photo from an author's photo list by index
    public Author removePhotoFromAuthor(Long id, int photoIndex) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));

        if (photoIndex < 0 || photoIndex >= author.getPhotos().size()) {
            throw new RuntimeException("Invalid photo index: " + photoIndex);
        }

        author.getPhotos().remove(photoIndex);
        return authorRepository.save(author);
    }

    // Delete an author by ID
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author with ID " + id + " not found");
        }
        authorRepository.deleteById(id);
    }
}
