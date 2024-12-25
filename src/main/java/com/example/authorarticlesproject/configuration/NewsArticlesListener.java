package com.example.authorarticlesproject.configuration;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsArticlesListener implements MessageListener {
    public static List<String> messageNewsList = new ArrayList<>();
    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageNewsList.add(message.toString());
        System.out.println("Message received : " +message);

    }
}
