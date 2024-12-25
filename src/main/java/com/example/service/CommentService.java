package com.example.service;

import com.example.model.Article;
import com.example.model.Comment;
import com.example.model.Message;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private MessageBroker broker;

    public CommentService(CommentRepository commentRepository, MessageBroker broker) {
        this.commentRepository = commentRepository;
        this.broker = broker;
    }


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment commentDetails) {
        return commentRepository.findById(id).map(comment -> {
            comment.setLikes(commentDetails.getLikes());
            comment.setDislikes(commentDetails.getDislikes());
            comment.setText(commentDetails.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public void addCommentToArticle(Article article, Comment comment) {
        article.getAuthor().addNotification("New comment on your article '" + article.getTitle() + "': " + comment.getText());
        broker.publish(new Message("NewComment", "New comment added by " + comment.getCommentAuthor() + " to article '" + article.getTitle() + "'"));
    }

}
