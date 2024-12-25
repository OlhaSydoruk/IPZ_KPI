package com.example.authorarticlesproject.controller;

import com.example.authorarticlesproject.configuration.RedisMessagePublisher;
import com.example.authorarticlesproject.configuration.NewsArticlesListener;
import com.example.authorarticlesproject.configuration.SportArticlesListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.authorarticlesproject.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {
    private static Logger logger = LoggerFactory.getLogger(RedisController.class);


    @Autowired
    private RedisMessagePublisher messagePublisher;

    @Autowired
    private NewsArticlesListener newsArticlesListener;

    @Autowired
    private SportArticlesListener sportArticlesListener;

    @PostMapping("/publish/{topic}")
    public void publish(@RequestBody Message message, @PathVariable String topic) {
        logger.info("Publishing to {}: {}", topic, message.toString());
        messagePublisher.publish(message.toString(), topic);
    }

    @GetMapping("/subscriber/{topic}")
    public ResponseEntity<List<String>> getMessage(@PathVariable String topic) {
        if ("NewsArticles".equals(topic)) {
            return ResponseEntity.ok(newsArticlesListener.messageNewsList);
        } else if ("SportArticles".equals(topic)) {
            return ResponseEntity.ok(sportArticlesListener.messageSportList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }
}
