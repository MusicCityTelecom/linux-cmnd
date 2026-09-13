/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.session.SessionRepository
 *  org.springframework.session.data.redis.RedisIndexedSessionRepository
 *  org.springframework.session.data.redis.config.ConfigureNotifyKeyspaceEventsAction
 *  org.springframework.session.data.redis.config.ConfigureRedisAction
 *  org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration
 */
package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.boot.autoconfigure.session.ServletSessionCondition;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.ConfigureNotifyKeyspaceEventsAction;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={RedisTemplate.class, RedisIndexedSessionRepository.class})
@ConditionalOnMissingBean(value={SessionRepository.class})
@ConditionalOnBean(value={RedisConnectionFactory.class})
@Conditional(value={ServletSessionCondition.class})
@EnableConfigurationProperties(value={RedisSessionProperties.class})
class RedisSessionConfiguration {
    RedisSessionConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    ConfigureRedisAction configureRedisAction(RedisSessionProperties redisSessionProperties) {
        switch (redisSessionProperties.getConfigureAction()) {
            case NOTIFY_KEYSPACE_EVENTS: {
                return new ConfigureNotifyKeyspaceEventsAction();
            }
            case NONE: {
                return ConfigureRedisAction.NO_OP;
            }
        }
        throw new IllegalStateException("Unsupported redis configure action '" + (Object)((Object)redisSessionProperties.getConfigureAction()) + "'.");
    }

    @Configuration(proxyBeanMethods=false)
    public static class SpringBootRedisHttpSessionConfiguration
    extends RedisHttpSessionConfiguration {
        @Autowired
        public void customize(SessionProperties sessionProperties, RedisSessionProperties redisSessionProperties, ServerProperties serverProperties) {
            Duration timeout = sessionProperties.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
            if (timeout != null) {
                this.setMaxInactiveIntervalInSeconds((int)timeout.getSeconds());
            }
            this.setRedisNamespace(redisSessionProperties.getNamespace());
            this.setFlushMode(redisSessionProperties.getFlushMode());
            this.setSaveMode(redisSessionProperties.getSaveMode());
            this.setCleanupCron(redisSessionProperties.getCleanupCron());
        }
    }
}

