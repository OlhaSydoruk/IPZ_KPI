package com.example.service;

import com.example.model.Message;
import com.example.service.listners.MessageListener;

public interface MessageBroker {
    void publish(Message message);
    void subscribe(String topic, MessageListener listener);
}
