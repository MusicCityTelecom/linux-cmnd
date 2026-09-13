/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cache.Cache
 *  org.springframework.cache.CacheManager
 *  org.springframework.cache.support.SimpleCacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.cache;

import java.util.Collection;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnBean(value={Cache.class})
@ConditionalOnMissingBean(value={CacheManager.class})
@Conditional(value={CacheCondition.class})
class GenericCacheConfiguration {
    GenericCacheConfiguration() {
    }

    @Bean
    SimpleCacheManager cacheManager(CacheManagerCustomizers customizers, Collection<Cache> caches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return customizers.customize(cacheManager);
    }
}

