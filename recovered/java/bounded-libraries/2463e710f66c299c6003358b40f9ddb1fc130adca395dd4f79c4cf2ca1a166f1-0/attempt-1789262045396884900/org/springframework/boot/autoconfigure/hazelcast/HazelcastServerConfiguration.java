/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.config.Config
 *  com.hazelcast.config.XmlConfigBuilder
 *  com.hazelcast.config.YamlConfigBuilder
 *  com.hazelcast.core.Hazelcast
 *  com.hazelcast.core.HazelcastInstance
 *  com.hazelcast.core.ManagedContext
 *  com.hazelcast.spring.context.SpringManagedContext
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.ResourceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ManagedContext;
import com.hazelcast.spring.context.SpringManagedContext;
import java.io.IOException;
import java.net.URL;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastConfigCustomizer;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastConfigResourceCondition;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={HazelcastInstance.class})
class HazelcastServerConfiguration {
    static final String CONFIG_SYSTEM_PROPERTY = "hazelcast.config";

    HazelcastServerConfiguration() {
    }

    private static HazelcastInstance getHazelcastInstance(Config config) {
        if (StringUtils.hasText((String)config.getInstanceName())) {
            return Hazelcast.getOrCreateHazelcastInstance((Config)config);
        }
        return Hazelcast.newHazelcastInstance((Config)config);
    }

    static class ConfigAvailableCondition
    extends HazelcastConfigResourceCondition {
        ConfigAvailableCondition() {
            super(HazelcastServerConfiguration.CONFIG_SYSTEM_PROPERTY, new String[]{"file:./hazelcast.xml", "classpath:/hazelcast.xml", "file:./hazelcast.yaml", "classpath:/hazelcast.yaml"});
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={SpringManagedContext.class})
    static class SpringManagedContextHazelcastConfigCustomizerConfiguration {
        SpringManagedContextHazelcastConfigCustomizerConfiguration() {
        }

        @Bean
        @Order(value=0)
        HazelcastConfigCustomizer springManagedContextHazelcastConfigCustomizer(ApplicationContext applicationContext) {
            return config -> {
                SpringManagedContext managementContext = new SpringManagedContext();
                managementContext.setApplicationContext(applicationContext);
                config.setManagedContext((ManagedContext)managementContext);
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnSingleCandidate(value=Config.class)
    static class HazelcastServerConfigConfiguration {
        HazelcastServerConfigConfiguration() {
        }

        @Bean
        HazelcastInstance hazelcastInstance(Config config) {
            return HazelcastServerConfiguration.getHazelcastInstance(config);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={Config.class})
    @Conditional(value={ConfigAvailableCondition.class})
    static class HazelcastServerConfigFileConfiguration {
        HazelcastServerConfigFileConfiguration() {
        }

        @Bean
        HazelcastInstance hazelcastInstance(HazelcastProperties properties, ResourceLoader resourceLoader, ObjectProvider<HazelcastConfigCustomizer> hazelcastConfigCustomizers) throws IOException {
            Resource configLocation = properties.resolveConfigLocation();
            Config config = configLocation != null ? this.loadConfig(configLocation) : Config.load();
            config.setClassLoader(resourceLoader.getClassLoader());
            hazelcastConfigCustomizers.orderedStream().forEach(customizer -> customizer.customize(config));
            return HazelcastServerConfiguration.getHazelcastInstance(config);
        }

        private Config loadConfig(Resource configLocation) throws IOException {
            URL configUrl = configLocation.getURL();
            Config config = HazelcastServerConfigFileConfiguration.loadConfig(configUrl);
            if (ResourceUtils.isFileURL((URL)configUrl)) {
                config.setConfigurationFile(configLocation.getFile());
            } else {
                config.setConfigurationUrl(configUrl);
            }
            return config;
        }

        private static Config loadConfig(URL configUrl) throws IOException {
            String configFileName = configUrl.getPath();
            if (configFileName.endsWith(".yaml")) {
                return new YamlConfigBuilder(configUrl).build();
            }
            return new XmlConfigBuilder(configUrl).build();
        }
    }
}

