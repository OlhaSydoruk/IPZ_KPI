package com.example.service;

import com.example.model.Author;
import com.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        return authorRepository.findById(id).map(author -> {
            author.setName(authorDetails.getName());
            author.setAge(authorDetails.getAge());
            author.setGender(authorDetails.getGender());
            author.setPhoto(authorDetails.getPhoto());
            return authorRepository.save(author);
        }).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
