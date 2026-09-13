/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.infinispan.configuration.cache.Configuration
 *  org.infinispan.configuration.cache.ConfigurationBuilder
 *  org.infinispan.manager.DefaultCacheManager
 *  org.infinispan.manager.EmbeddedCacheManager
 *  org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.cache.CacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.Resource
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.autoconfigure.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={SpringEmbeddedCacheManager.class})
@ConditionalOnMissingBean(value={CacheManager.class})
@Conditional(value={CacheCondition.class})
public class InfinispanCacheConfiguration {
    @Bean
    public SpringEmbeddedCacheManager cacheManager(CacheManagerCustomizers customizers, EmbeddedCacheManager embeddedCacheManager) {
        SpringEmbeddedCacheManager cacheManager = new SpringEmbeddedCacheManager(embeddedCacheManager);
        return customizers.customize(cacheManager);
    }

    @Bean(destroyMethod="stop")
    @ConditionalOnMissingBean
    public EmbeddedCacheManager infinispanCacheManager(CacheProperties cacheProperties, ObjectProvider<ConfigurationBuilder> defaultConfigurationBuilder) throws IOException {
        EmbeddedCacheManager cacheManager = this.createEmbeddedCacheManager(cacheProperties);
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            cacheNames.forEach(cacheName -> cacheManager.defineConfiguration(cacheName, this.getDefaultCacheConfiguration((ConfigurationBuilder)defaultConfigurationBuilder.getIfAvailable())));
        }
        return cacheManager;
    }

    private EmbeddedCacheManager createEmbeddedCacheManager(CacheProperties cacheProperties) throws IOException {
        Resource location = cacheProperties.resolveConfigLocation(cacheProperties.getInfinispan().getConfig());
        if (location != null) {
            try (InputStream in = location.getInputStream();){
                DefaultCacheManager defaultCacheManager = new DefaultCacheManager(in);
                return defaultCacheManager;
            }
        }
        return new DefaultCacheManager();
    }

    private org.infinispan.configuration.cache.Configuration getDefaultCacheConfiguration(ConfigurationBuilder defaultConfigurationBuilder) {
        if (defaultConfigurationBuilder != null) {
            return defaultConfigurationBuilder.build();
        }
        return new ConfigurationBuilder().build();
    }
}

