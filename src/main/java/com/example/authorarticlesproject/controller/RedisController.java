package com.example.authorarticlesproject.controller;

import com.example.authorarticlesproject.configuration.RedisMessagePublisher;
import com.example.authorarticlesproject.configuration.RedisMessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.authorarticlesproject.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {
    private static Logger logger = LoggerFactory.getLogger(RedisController.class);


    @Autowired
    private RedisMessagePublisher messagePublisher;

    @Autowired
    private RedisMessageSubscriber redisMessageSubscriber;

    @PostMapping("/publish")
    public void publish(@RequestBody Message message) {
        logger.info("Publishing: {}", message.toString());
        messagePublisher.publish(message.toString());
    }

    @GetMapping("/subscriber")
    public List<String> getMessage(){
    return redisMessageSubscriber.messageList;
    }
}
