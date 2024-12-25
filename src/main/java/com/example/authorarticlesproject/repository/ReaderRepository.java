package com.example.authorarticlesproject.repository;

import com.example.authorarticlesproject.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
