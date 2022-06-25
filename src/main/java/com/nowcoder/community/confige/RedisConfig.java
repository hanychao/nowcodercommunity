package com.nowcoder.community.confige;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
/**
 * @author hyc
 * @create 2022-06-24 22:23
 */
@Configuration
public class RedisConfig {

    /**
     *参照RedisAutoConfiguration中的方法修改
     */
    @Bean //定义第三方bean，通过redisTemplate访问Redis
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 访问数据库需要注入连接，方法上声明参数，这个参数就被容器装配了
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 存到数据库指定序列化方式，key-value(key序列化方式，value序列化方式，hash自己又有key value)
        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        // 触发生效
        template.afterPropertiesSet();

        return template;
    }
}
