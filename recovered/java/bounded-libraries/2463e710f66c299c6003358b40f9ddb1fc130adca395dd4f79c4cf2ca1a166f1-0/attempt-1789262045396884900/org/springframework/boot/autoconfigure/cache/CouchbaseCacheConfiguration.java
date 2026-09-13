/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.Cluster
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.cache.CacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.couchbase.CouchbaseClientFactory
 *  org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration
 *  org.springframework.data.couchbase.cache.CouchbaseCacheManager
 *  org.springframework.data.couchbase.cache.CouchbaseCacheManager$CouchbaseCacheManagerBuilder
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.cache;

import com.couchbase.client.java.Cluster;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CouchbaseCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.util.ObjectUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={Cluster.class, CouchbaseClientFactory.class, CouchbaseCacheManager.class})
@ConditionalOnMissingBean(value={CacheManager.class})
@ConditionalOnSingleCandidate(value=CouchbaseClientFactory.class)
@Conditional(value={CacheCondition.class})
class CouchbaseCacheConfiguration {
    CouchbaseCacheConfiguration() {
    }

    @Bean
    CouchbaseCacheManager cacheManager(CacheProperties cacheProperties, CacheManagerCustomizers customizers, ObjectProvider<CouchbaseCacheManagerBuilderCustomizer> couchbaseCacheManagerBuilderCustomizers, CouchbaseClientFactory clientFactory) {
        List<String> cacheNames = cacheProperties.getCacheNames();
        CouchbaseCacheManager.CouchbaseCacheManagerBuilder builder = CouchbaseCacheManager.builder((CouchbaseClientFactory)clientFactory);
        CacheProperties.Couchbase couchbase = cacheProperties.getCouchbase();
        org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration config = org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration.defaultCacheConfig();
        if (couchbase.getExpiration() != null) {
            config = config.entryExpiry(couchbase.getExpiration());
        }
        builder.cacheDefaults(config);
        if (!ObjectUtils.isEmpty(cacheNames)) {
            builder.initialCacheNames(new LinkedHashSet<String>(cacheNames));
        }
        couchbaseCacheManagerBuilderCustomizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        CouchbaseCacheManager cacheManager = builder.build();
        return customizers.customize(cacheManager);
    }
}

