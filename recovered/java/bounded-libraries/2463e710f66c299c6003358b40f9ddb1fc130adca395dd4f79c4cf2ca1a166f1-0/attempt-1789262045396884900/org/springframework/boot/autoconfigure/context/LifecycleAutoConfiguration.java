/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.support.DefaultLifecycleProcessor
 */
package org.springframework.boot.autoconfigure.context;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.context.LifecycleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultLifecycleProcessor;

@AutoConfiguration
@EnableConfigurationProperties(value={LifecycleProperties.class})
public class LifecycleAutoConfiguration {
    @Bean(name={"lifecycleProcessor"})
    @ConditionalOnMissingBean(name={"lifecycleProcessor"}, search=SearchStrategy.CURRENT)
    public DefaultLifecycleProcessor defaultLifecycleProcessor(LifecycleProperties properties) {
        DefaultLifecycleProcessor lifecycleProcessor = new DefaultLifecycleProcessor();
        lifecycleProcessor.setTimeoutPerShutdownPhase(properties.getTimeoutPerShutdownPhase().toMillis());
        return lifecycleProcessor;
    }
}

