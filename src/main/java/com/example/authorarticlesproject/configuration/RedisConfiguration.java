package com.example.authorarticlesproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfiguration {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(newsListenerAdapter(), newsTopic());
        container.addMessageListener(sportListenerAdapter(), sportTopic());
        return container;
    }

    @Bean
    public MessageListenerAdapter newsListenerAdapter() {
        return new MessageListenerAdapter(new NewsArticlesListener());
    }

    @Bean
    public MessageListenerAdapter sportListenerAdapter() {
        return new MessageListenerAdapter(new SportArticlesListener());
    }

    @Bean
    public ChannelTopic sportTopic() {
        return new ChannelTopic("SportArticles");
    }

    @Bean
    public ChannelTopic newsTopic() {
        return new ChannelTopic("NewsArticles");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }

    @Bean
    public MessagePublisher messagePublisher(RedisTemplate<String, Object> redisTemplate) {
        return new RedisMessagePublisher(redisTemplate);
    }
}
