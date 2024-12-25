package com.example.authorarticlesproject.service;

import com.example.authorarticlesproject.model.Article;
import com.example.authorarticlesproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article article) {
        return articleRepository.findById(id).map(existingArticle -> {
            existingArticle.setType(article.getType());
            existingArticle.setTitle(article.getTitle());
            existingArticle.setComment_id(article.getComment_id());
            existingArticle.setRating(article.getRating());
            return articleRepository.save(existingArticle);
        }).orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
