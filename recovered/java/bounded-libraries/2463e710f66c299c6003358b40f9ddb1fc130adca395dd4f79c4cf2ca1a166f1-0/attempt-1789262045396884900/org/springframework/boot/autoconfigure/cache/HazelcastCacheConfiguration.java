/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.core.HazelcastInstance
 *  com.hazelcast.spring.cache.HazelcastCacheManager
 *  org.springframework.cache.CacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={HazelcastInstance.class, HazelcastCacheManager.class})
@ConditionalOnMissingBean(value={CacheManager.class})
@Conditional(value={CacheCondition.class})
@ConditionalOnSingleCandidate(value=HazelcastInstance.class)
class HazelcastCacheConfiguration {
    HazelcastCacheConfiguration() {
    }

    @Bean
    HazelcastCacheManager cacheManager(CacheManagerCustomizers customizers, HazelcastInstance existingHazelcastInstance) {
        HazelcastCacheManager cacheManager = new HazelcastCacheManager(existingHazelcastInstance);
        return customizers.customize(cacheManager);
    }
}

