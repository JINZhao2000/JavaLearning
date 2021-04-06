package com.ayy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class RedisApplicationTests {
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        // redisTemplate.opsForValue().
        // redisTemplate.opsForList().
        // redisTemplate.opsForSet().
        // redisTemplate.opsForHash().
        // redisTemplate.opsForZSet().
        // redisTemplate.opsForGeo().
        // redisTemplate.opsForHyperLogLog().

        // redisTemplate.getConnectionFactory().getConnection();
        // connection.flushAll();
        // connection.flushDb();

        redisTemplate.opsForValue().set("key","value");
        System.out.println(redisTemplate.opsForValue().get("key"));
    }

}
