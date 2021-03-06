package com.records.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @author changfa
 */

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host : 127.0.0.1 }")
    private String hostName;
    @Value("${spring.redis.port : 6379 }")
    private Integer port;
    @Value("${spring.redis.password : }")
    private String password;
    @Value("${spring.redis.database : 0 }")
    private Integer database;
    @Value("${spring.redis.timeout : 5000 }")
    private Integer timeout;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(hostName);
        standaloneConfig.setPort(port);
        standaloneConfig.setPassword(password);
        standaloneConfig.setDatabase(database);
        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfig = LettuceClientConfiguration.builder();
        lettuceClientConfig.commandTimeout(Duration.ofMillis(timeout));
        return new LettuceConnectionFactory(standaloneConfig, lettuceClientConfig.build());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // ?????????????????????????????????<String,Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // ??????????????????????????????
        // 1.json??????????????????
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 2.string??????????????????
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key??????string???????????????
        template.setKeySerializer(stringRedisSerializer);
        // hash???key?????????string??????????????????
        template.setHashKeySerializer(stringRedisSerializer);

        // value??????Jackson??????????????????
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash???value?????????Jackson??????????????????
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

}
