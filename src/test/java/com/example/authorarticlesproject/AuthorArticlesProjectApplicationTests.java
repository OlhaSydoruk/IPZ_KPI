package com.example.authorarticlesproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class AuthorArticlesProjectApplicationTests {

    @MockBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private RedisConnectionFactory connectionFactory;

    @MockBean
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;


}
