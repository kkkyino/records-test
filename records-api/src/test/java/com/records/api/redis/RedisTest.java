package com.records.api.redis;

import com.records.common.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void rediSet(){
        System.out.println("aa");
        redisUtil.set("中文1","中文1221");
    }
}
