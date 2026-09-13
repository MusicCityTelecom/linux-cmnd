/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
 *  org.springframework.data.redis.core.ReactiveRedisTemplate
 *  org.springframework.data.redis.core.ReactiveStringRedisTemplate
 *  org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
 *  org.springframework.data.redis.serializer.RedisSerializationContext
 *  org.springframework.data.redis.serializer.RedisSerializer
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import reactor.core.publisher.Flux;

@AutoConfiguration(after={RedisAutoConfiguration.class})
@ConditionalOnClass(value={ReactiveRedisConnectionFactory.class, ReactiveRedisTemplate.class, Flux.class})
public class RedisReactiveAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name={"reactiveRedisTemplate"})
    @ConditionalOnBean(value={ReactiveRedisConnectionFactory.class})
    public ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory, ResourceLoader resourceLoader) {
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(resourceLoader.getClassLoader());
        RedisSerializationContext serializationContext = RedisSerializationContext.newSerializationContext().key((RedisSerializer)jdkSerializer).value((RedisSerializer)jdkSerializer).hashKey((RedisSerializer)jdkSerializer).hashValue((RedisSerializer)jdkSerializer).build();
        return new ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializationContext);
    }

    @Bean
    @ConditionalOnMissingBean(name={"reactiveStringRedisTemplate"})
    @ConditionalOnBean(value={ReactiveRedisConnectionFactory.class})
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory);
    }
}

