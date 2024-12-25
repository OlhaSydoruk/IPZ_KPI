package com.example.model;

import jakarta.persistence.*;

@Entity
public class Comment {

    public Comment(Long id, int likes, int dislikes, String text, Article article, String commentAuthor) {
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.text = text;
        this.article = article;
        this.commentAuthor = commentAuthor;
    }

    public Comment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int likes;
    private int dislikes;
    private String text;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    private String commentAuthor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }
}
