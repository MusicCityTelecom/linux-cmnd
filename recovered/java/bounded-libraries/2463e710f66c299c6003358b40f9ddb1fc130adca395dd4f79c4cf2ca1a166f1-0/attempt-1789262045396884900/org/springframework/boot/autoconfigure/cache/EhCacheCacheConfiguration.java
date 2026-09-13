/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.Cache
 *  net.sf.ehcache.CacheManager
 *  org.springframework.cache.CacheManager
 *  org.springframework.cache.ehcache.EhCacheCacheManager
 *  org.springframework.cache.ehcache.EhCacheManagerUtils
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.autoconfigure.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ResourceCondition;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={Cache.class, EhCacheCacheManager.class})
@ConditionalOnMissingBean(value={org.springframework.cache.CacheManager.class})
@Conditional(value={CacheCondition.class, ConfigAvailableCondition.class})
class EhCacheCacheConfiguration {
    EhCacheCacheConfiguration() {
    }

    @Bean
    EhCacheCacheManager cacheManager(CacheManagerCustomizers customizers, CacheManager ehCacheCacheManager) {
        return customizers.customize(new EhCacheCacheManager(ehCacheCacheManager));
    }

    @Bean
    @ConditionalOnMissingBean
    CacheManager ehCacheCacheManager(CacheProperties cacheProperties) {
        Resource location = cacheProperties.resolveConfigLocation(cacheProperties.getEhcache().getConfig());
        if (location != null) {
            return EhCacheManagerUtils.buildCacheManager((Resource)location);
        }
        return EhCacheManagerUtils.buildCacheManager();
    }

    static class ConfigAvailableCondition
    extends ResourceCondition {
        ConfigAvailableCondition() {
            super("EhCache", "spring.cache.ehcache.config", "classpath:/ehcache.xml");
        }
    }
}

