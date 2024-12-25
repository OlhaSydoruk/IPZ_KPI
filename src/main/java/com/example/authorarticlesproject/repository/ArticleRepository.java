package com.example.authorarticlesproject.repository;

import com.example.authorarticlesproject.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
