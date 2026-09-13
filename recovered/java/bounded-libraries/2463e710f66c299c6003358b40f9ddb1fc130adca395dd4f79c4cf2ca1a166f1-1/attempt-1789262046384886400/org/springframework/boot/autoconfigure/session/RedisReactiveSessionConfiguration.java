/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
 *  org.springframework.session.ReactiveSessionRepository
 *  org.springframework.session.data.redis.ReactiveRedisSessionRepository
 *  org.springframework.session.data.redis.config.annotation.web.server.RedisWebSessionConfiguration
 */
package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.ReactiveSessionCondition;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.data.redis.ReactiveRedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.server.RedisWebSessionConfiguration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={ReactiveRedisConnectionFactory.class, ReactiveRedisSessionRepository.class})
@ConditionalOnMissingBean(value={ReactiveSessionRepository.class})
@ConditionalOnBean(value={ReactiveRedisConnectionFactory.class})
@Conditional(value={ReactiveSessionCondition.class})
@EnableConfigurationProperties(value={RedisSessionProperties.class})
class RedisReactiveSessionConfiguration {
    RedisReactiveSessionConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    static class SpringBootRedisWebSessionConfiguration
    extends RedisWebSessionConfiguration {
        SpringBootRedisWebSessionConfiguration() {
        }

        @Autowired
        void customize(SessionProperties sessionProperties, RedisSessionProperties redisSessionProperties, ServerProperties serverProperties) {
            Duration timeout = sessionProperties.determineTimeout(() -> serverProperties.getReactive().getSession().getTimeout());
            if (timeout != null) {
                this.setMaxInactiveIntervalInSeconds((int)timeout.getSeconds());
            }
            this.setRedisNamespace(redisSessionProperties.getNamespace());
            this.setSaveMode(redisSessionProperties.getSaveMode());
        }
    }
}

