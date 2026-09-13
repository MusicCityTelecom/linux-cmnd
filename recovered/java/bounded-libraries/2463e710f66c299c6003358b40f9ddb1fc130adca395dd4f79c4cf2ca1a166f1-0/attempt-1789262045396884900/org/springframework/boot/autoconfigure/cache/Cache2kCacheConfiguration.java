/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.cache2k.Cache2kBuilder
 *  org.cache2k.extra.spring.SpringCache2kCacheManager
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.cache.CacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.autoconfigure.cache;

import java.util.List;
import java.util.function.Function;
import org.cache2k.Cache2kBuilder;
import org.cache2k.extra.spring.SpringCache2kCacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.Cache2kBuilderCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={Cache2kBuilder.class, SpringCache2kCacheManager.class})
@ConditionalOnMissingBean(value={CacheManager.class})
@Conditional(value={CacheCondition.class})
class Cache2kCacheConfiguration {
    Cache2kCacheConfiguration() {
    }

    @Bean
    SpringCache2kCacheManager cacheManager(CacheProperties cacheProperties, CacheManagerCustomizers customizers, ObjectProvider<Cache2kBuilderCustomizer> cache2kBuilderCustomizers) {
        SpringCache2kCacheManager cacheManager = new SpringCache2kCacheManager();
        cacheManager.defaultSetup(this.configureDefaults(cache2kBuilderCustomizers));
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            cacheManager.setDefaultCacheNames(cacheNames);
        }
        return customizers.customize(cacheManager);
    }

    private Function<Cache2kBuilder<?, ?>, Cache2kBuilder<?, ?>> configureDefaults(ObjectProvider<Cache2kBuilderCustomizer> cache2kBuilderCustomizers) {
        return builder -> {
            cache2kBuilderCustomizers.orderedStream().forEach(customizer -> customizer.customize((Cache2kBuilder<?, ?>)builder));
            return builder;
        };
    }
}

