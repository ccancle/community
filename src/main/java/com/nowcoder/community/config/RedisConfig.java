package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @program: community
 * @description: Redis配置类
 * @author: Macchac
 * @create: 2020-07-13 17:54
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String ,Object> template = new RedisTemplate<>();
        //有工厂之后获得访问数据库的能力
        template.setConnectionFactory(factory);
        //数据转换方式 Java对象转json 序列化方式

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        //设置value 的序列化方式
        template.setValueSerializer(RedisSerializer.json());

        //设置value为hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        //设置value为hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        //设置完成之后触发配置
        template.afterPropertiesSet();
        return template;
    }
}
