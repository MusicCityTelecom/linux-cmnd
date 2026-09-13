/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cache.CacheManager
 *  org.springframework.cache.support.NoOpCacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.cache;

import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={CacheManager.class})
@Conditional(value={CacheCondition.class})
class NoOpCacheConfiguration {
    NoOpCacheConfiguration() {
    }

    @Bean
    NoOpCacheManager cacheManager() {
        return new NoOpCacheManager();
    }
}

