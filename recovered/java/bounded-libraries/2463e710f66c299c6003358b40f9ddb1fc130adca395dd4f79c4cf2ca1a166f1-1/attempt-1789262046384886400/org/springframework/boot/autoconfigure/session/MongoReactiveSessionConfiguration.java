/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.mongodb.core.ReactiveMongoOperations
 *  org.springframework.session.ReactiveSessionRepository
 *  org.springframework.session.data.mongo.ReactiveMongoSessionRepository
 *  org.springframework.session.data.mongo.config.annotation.web.reactive.ReactiveMongoWebSessionConfiguration
 */
package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.MongoSessionProperties;
import org.springframework.boot.autoconfigure.session.ReactiveSessionCondition;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.data.mongo.ReactiveMongoSessionRepository;
import org.springframework.session.data.mongo.config.annotation.web.reactive.ReactiveMongoWebSessionConfiguration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={ReactiveMongoOperations.class, ReactiveMongoSessionRepository.class})
@ConditionalOnMissingBean(value={ReactiveSessionRepository.class})
@ConditionalOnBean(value={ReactiveMongoOperations.class})
@Conditional(value={ReactiveSessionCondition.class})
@EnableConfigurationProperties(value={MongoSessionProperties.class})
class MongoReactiveSessionConfiguration {
    MongoReactiveSessionConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    static class SpringBootReactiveMongoWebSessionConfiguration
    extends ReactiveMongoWebSessionConfiguration {
        SpringBootReactiveMongoWebSessionConfiguration() {
        }

        @Autowired
        void customize(SessionProperties sessionProperties, MongoSessionProperties mongoSessionProperties, ServerProperties serverProperties) {
            Duration timeout = sessionProperties.determineTimeout(() -> serverProperties.getReactive().getSession().getTimeout());
            if (timeout != null) {
                this.setMaxInactiveIntervalInSeconds((int)timeout.getSeconds());
            }
            this.setCollectionName(mongoSessionProperties.getCollectionName());
        }
    }
}

