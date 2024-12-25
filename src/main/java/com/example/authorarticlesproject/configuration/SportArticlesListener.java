package com.example.authorarticlesproject.configuration;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportArticlesListener implements MessageListener {
    public static List<String> messageSportList = new ArrayList<>();
    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageSportList.add(message.toString());
        System.out.println("Message received : " +message);

    }
}
