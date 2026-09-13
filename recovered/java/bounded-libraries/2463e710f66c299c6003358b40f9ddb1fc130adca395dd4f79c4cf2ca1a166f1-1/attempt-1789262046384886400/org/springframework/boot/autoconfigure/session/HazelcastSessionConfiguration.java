/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.core.HazelcastInstance
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.session.SessionRepository
 *  org.springframework.session.hazelcast.HazelcastIndexedSessionRepository
 *  org.springframework.session.hazelcast.config.annotation.web.http.HazelcastHttpSessionConfiguration
 */
package org.springframework.boot.autoconfigure.session;

import com.hazelcast.core.HazelcastInstance;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.HazelcastSessionProperties;
import org.springframework.boot.autoconfigure.session.ServletSessionCondition;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.config.annotation.web.http.HazelcastHttpSessionConfiguration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={HazelcastIndexedSessionRepository.class})
@ConditionalOnMissingBean(value={SessionRepository.class})
@ConditionalOnBean(value={HazelcastInstance.class})
@Conditional(value={ServletSessionCondition.class})
@EnableConfigurationProperties(value={HazelcastSessionProperties.class})
class HazelcastSessionConfiguration {
    HazelcastSessionConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    public static class SpringBootHazelcastHttpSessionConfiguration
    extends HazelcastHttpSessionConfiguration {
        @Autowired
        public void customize(SessionProperties sessionProperties, HazelcastSessionProperties hazelcastSessionProperties, ServerProperties serverProperties) {
            Duration timeout = sessionProperties.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
            if (timeout != null) {
                this.setMaxInactiveIntervalInSeconds((int)timeout.getSeconds());
            }
            this.setSessionMapName(hazelcastSessionProperties.getMapName());
            this.setFlushMode(hazelcastSessionProperties.getFlushMode());
            this.setSaveMode(hazelcastSessionProperties.getSaveMode());
        }
    }
}

