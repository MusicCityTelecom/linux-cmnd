/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.cache.CacheManager
 *  javax.cache.Caching
 *  javax.cache.configuration.Configuration
 *  javax.cache.configuration.MutableConfiguration
 *  javax.cache.spi.CachingProvider
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.cache.CacheManager
 *  org.springframework.cache.jcache.JCacheCacheManager
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.io.Resource
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.cache;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheCondition;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.HazelcastJCacheCustomizationConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.JCachePropertiesCustomizer;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={Caching.class, JCacheCacheManager.class})
@ConditionalOnMissingBean(value={org.springframework.cache.CacheManager.class})
@Conditional(value={CacheCondition.class, JCacheAvailableCondition.class})
@Import(value={HazelcastJCacheCustomizationConfiguration.class})
class JCacheCacheConfiguration
implements BeanClassLoaderAware {
    private ClassLoader beanClassLoader;

    JCacheCacheConfiguration() {
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Bean
    JCacheCacheManager cacheManager(CacheManagerCustomizers customizers, CacheManager jCacheCacheManager) {
        JCacheCacheManager cacheManager = new JCacheCacheManager(jCacheCacheManager);
        return customizers.customize(cacheManager);
    }

    @Bean
    @ConditionalOnMissingBean
    CacheManager jCacheCacheManager(CacheProperties cacheProperties, ObjectProvider<javax.cache.configuration.Configuration<?, ?>> defaultCacheConfiguration, ObjectProvider<JCacheManagerCustomizer> cacheManagerCustomizers, ObjectProvider<JCachePropertiesCustomizer> cachePropertiesCustomizers) throws IOException {
        CacheManager jCacheCacheManager = this.createCacheManager(cacheProperties, cachePropertiesCustomizers);
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            for (String cacheName : cacheNames) {
                jCacheCacheManager.createCache(cacheName, (javax.cache.configuration.Configuration)defaultCacheConfiguration.getIfAvailable(MutableConfiguration::new));
            }
        }
        cacheManagerCustomizers.orderedStream().forEach(customizer -> customizer.customize(jCacheCacheManager));
        return jCacheCacheManager;
    }

    private CacheManager createCacheManager(CacheProperties cacheProperties, ObjectProvider<JCachePropertiesCustomizer> cachePropertiesCustomizers) throws IOException {
        CachingProvider cachingProvider = this.getCachingProvider(cacheProperties.getJcache().getProvider());
        Properties properties = this.createCacheManagerProperties(cachePropertiesCustomizers, cacheProperties);
        Resource configLocation = cacheProperties.resolveConfigLocation(cacheProperties.getJcache().getConfig());
        if (configLocation != null) {
            return cachingProvider.getCacheManager(configLocation.getURI(), this.beanClassLoader, properties);
        }
        return cachingProvider.getCacheManager(null, this.beanClassLoader, properties);
    }

    private CachingProvider getCachingProvider(String cachingProviderFqn) {
        if (StringUtils.hasText((String)cachingProviderFqn)) {
            return Caching.getCachingProvider((String)cachingProviderFqn);
        }
        return Caching.getCachingProvider();
    }

    private Properties createCacheManagerProperties(ObjectProvider<JCachePropertiesCustomizer> cachePropertiesCustomizers, CacheProperties cacheProperties) {
        Properties properties = new Properties();
        cachePropertiesCustomizers.orderedStream().forEach(customizer -> customizer.customize(cacheProperties, properties));
        return properties;
    }

    @Order(value=0x7FFFFFFF)
    static class JCacheProviderAvailableCondition
    extends SpringBootCondition {
        JCacheProviderAvailableCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("JCache", new Object[0]);
            String providerProperty = "spring.cache.jcache.provider";
            if (context.getEnvironment().containsProperty(providerProperty)) {
                return ConditionOutcome.match(message.because("JCache provider specified"));
            }
            Iterator providers = Caching.getCachingProviders().iterator();
            if (!providers.hasNext()) {
                return ConditionOutcome.noMatch(message.didNotFind("JSR-107 provider").atAll());
            }
            providers.next();
            if (providers.hasNext()) {
                return ConditionOutcome.noMatch(message.foundExactly("multiple JSR-107 providers"));
            }
            return ConditionOutcome.match(message.foundExactly("single JSR-107 provider"));
        }
    }

    @Order(value=0x7FFFFFFF)
    static class JCacheAvailableCondition
    extends AnyNestedCondition {
        JCacheAvailableCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnSingleCandidate(value=CacheManager.class)
        static class CustomJCacheCacheManager {
            CustomJCacheCacheManager() {
            }
        }

        @Conditional(value={JCacheProviderAvailableCondition.class})
        static class JCacheProvider {
            JCacheProvider() {
            }
        }
    }
}

