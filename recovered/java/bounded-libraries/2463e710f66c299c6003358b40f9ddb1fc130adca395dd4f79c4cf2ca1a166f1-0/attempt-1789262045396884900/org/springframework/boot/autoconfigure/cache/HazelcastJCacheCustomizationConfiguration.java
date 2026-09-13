/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.core.HazelcastInstance
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.autoconfigure.cache;

import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.JCachePropertiesCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={HazelcastInstance.class})
class HazelcastJCacheCustomizationConfiguration {
    HazelcastJCacheCustomizationConfiguration() {
    }

    @Bean
    HazelcastPropertiesCustomizer hazelcastPropertiesCustomizer(ObjectProvider<HazelcastInstance> hazelcastInstance) {
        return new HazelcastPropertiesCustomizer((HazelcastInstance)hazelcastInstance.getIfUnique());
    }

    static class HazelcastPropertiesCustomizer
    implements JCachePropertiesCustomizer {
        private final HazelcastInstance hazelcastInstance;

        HazelcastPropertiesCustomizer(HazelcastInstance hazelcastInstance) {
            this.hazelcastInstance = hazelcastInstance;
        }

        @Override
        public void customize(CacheProperties cacheProperties, Properties properties) {
            Resource configLocation = cacheProperties.resolveConfigLocation(cacheProperties.getJcache().getConfig());
            if (configLocation != null) {
                properties.setProperty("hazelcast.config.location", HazelcastPropertiesCustomizer.toUri(configLocation).toString());
            } else if (this.hazelcastInstance != null) {
                properties.put("hazelcast.instance.itself", this.hazelcastInstance);
            }
        }

        private static URI toUri(Resource config) {
            try {
                return config.getURI();
            }
            catch (IOException ex) {
                throw new IllegalArgumentException("Could not get URI from " + config, ex);
            }
        }
    }
}

