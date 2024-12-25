package com.example.authorarticlesproject.repository;

import com.example.authorarticlesproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
