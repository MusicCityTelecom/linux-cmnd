/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.core.HazelcastInstance
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.io.Resource
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastClientConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastProperties;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastServerConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;

@AutoConfiguration
@Conditional(value={HazelcastDataGridCondition.class})
@ConditionalOnClass(value={HazelcastInstance.class})
@EnableConfigurationProperties(value={HazelcastProperties.class})
@Import(value={HazelcastClientConfiguration.class, HazelcastServerConfiguration.class})
public class HazelcastAutoConfiguration {

    static class HazelcastDataGridCondition
    extends SpringBootCondition {
        private static final String HAZELCAST_JET_CONFIG_FILE = "classpath:/hazelcast-jet-default.yaml";

        HazelcastDataGridCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition(HazelcastDataGridCondition.class.getSimpleName(), new Object[0]);
            Resource resource = context.getResourceLoader().getResource(HAZELCAST_JET_CONFIG_FILE);
            if (resource.exists()) {
                return ConditionOutcome.noMatch(message.because("Found Hazelcast Jet on the classpath"));
            }
            return ConditionOutcome.match(message.because("Hazelcast Jet not found on the classpath"));
        }
    }
}

