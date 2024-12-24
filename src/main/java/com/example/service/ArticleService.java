package com.example.service;

import com.example.model.Article;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article articleDetails) {
        return articleRepository.findById(id).map(article -> {
            article.setType(articleDetails.getType());
            article.setTitle(articleDetails.getTitle());
            article.setRating(articleDetails.getRating());
            return articleRepository.save(article);
        }).orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
