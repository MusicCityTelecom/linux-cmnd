/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.mongodb.core.MongoOperations
 *  org.springframework.session.SessionRepository
 *  org.springframework.session.data.mongo.MongoIndexedSessionRepository
 *  org.springframework.session.data.mongo.config.annotation.web.http.MongoHttpSessionConfiguration
 */
package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.MongoSessionProperties;
import org.springframework.boot.autoconfigure.session.ServletSessionCondition;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.mongo.MongoIndexedSessionRepository;
import org.springframework.session.data.mongo.config.annotation.web.http.MongoHttpSessionConfiguration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={MongoOperations.class, MongoIndexedSessionRepository.class})
@ConditionalOnMissingBean(value={SessionRepository.class})
@ConditionalOnBean(value={MongoOperations.class})
@Conditional(value={ServletSessionCondition.class})
@EnableConfigurationProperties(value={MongoSessionProperties.class})
class MongoSessionConfiguration {
    MongoSessionConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    public static class SpringBootMongoHttpSessionConfiguration
    extends MongoHttpSessionConfiguration {
        @Autowired
        public void customize(SessionProperties sessionProperties, MongoSessionProperties mongoSessionProperties, ServerProperties serverProperties) {
            Duration timeout = sessionProperties.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
            if (timeout != null) {
                this.setMaxInactiveIntervalInSeconds((int)timeout.getSeconds());
            }
            this.setCollectionName(mongoSessionProperties.getCollectionName());
        }
    }
}

