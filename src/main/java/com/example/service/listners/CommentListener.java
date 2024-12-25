package com.example.service.listners;

import com.example.model.Message;

public class CommentListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("CommentListener received a message: " + message.getContent());
        // Здесь можно добавить дополнительную логику, например, обновление базы данных, отправку уведомлений и т.д.
    }

}
