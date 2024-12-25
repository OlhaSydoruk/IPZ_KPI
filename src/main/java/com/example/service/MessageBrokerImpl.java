package com.example.service;

import com.example.model.Message;
import com.example.service.listners.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageBrokerImpl  implements MessageBroker {
    private Map<String, List<MessageListener>> listeners = new HashMap<>();

    @Override
    public void publish(Message message) {
        List<MessageListener> subscribers = listeners.get(message.getTopic());
        if (subscribers != null) {
            for (MessageListener listener : subscribers) {
                listener.onMessage(message);
            }
        }
    }

    @Override
    public void subscribe(String topic, MessageListener listener) {
        listeners.computeIfAbsent(topic, k -> new ArrayList<>()).add(listener);
    }
}