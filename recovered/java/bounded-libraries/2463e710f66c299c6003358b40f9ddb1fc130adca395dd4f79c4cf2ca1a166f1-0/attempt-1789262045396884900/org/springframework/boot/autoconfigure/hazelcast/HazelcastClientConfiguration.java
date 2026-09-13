/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.client.HazelcastClient
 *  com.hazelcast.client.config.ClientConfig
 *  com.hazelcast.client.config.XmlClientConfigBuilder
 *  com.hazelcast.client.config.YamlClientConfigBuilder
 *  com.hazelcast.core.HazelcastInstance
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.client.config.YamlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.net.URL;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastClientConfigAvailableCondition;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={HazelcastClient.class})
@ConditionalOnMissingBean(value={HazelcastInstance.class})
class HazelcastClientConfiguration {
    static final String CONFIG_SYSTEM_PROPERTY = "hazelcast.client.config";

    HazelcastClientConfiguration() {
    }

    private static HazelcastInstance getHazelcastInstance(ClientConfig config) {
        if (StringUtils.hasText((String)config.getInstanceName())) {
            return HazelcastClient.getOrCreateHazelcastClient((ClientConfig)config);
        }
        return HazelcastClient.newHazelcastClient((ClientConfig)config);
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnSingleCandidate(value=ClientConfig.class)
    static class HazelcastClientConfigConfiguration {
        HazelcastClientConfigConfiguration() {
        }

        @Bean
        HazelcastInstance hazelcastInstance(ClientConfig config) {
            return HazelcastClientConfiguration.getHazelcastInstance(config);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={ClientConfig.class})
    @Conditional(value={HazelcastClientConfigAvailableCondition.class})
    static class HazelcastClientConfigFileConfiguration {
        HazelcastClientConfigFileConfiguration() {
        }

        @Bean
        HazelcastInstance hazelcastInstance(HazelcastProperties properties, ResourceLoader resourceLoader) throws IOException {
            Resource configLocation = properties.resolveConfigLocation();
            ClientConfig config = configLocation != null ? this.loadClientConfig(configLocation) : ClientConfig.load();
            config.setClassLoader(resourceLoader.getClassLoader());
            return HazelcastClientConfiguration.getHazelcastInstance(config);
        }

        private ClientConfig loadClientConfig(Resource configLocation) throws IOException {
            URL configUrl = configLocation.getURL();
            String configFileName = configUrl.getPath();
            if (configFileName.endsWith(".yaml")) {
                return new YamlClientConfigBuilder(configUrl).build();
            }
            return new XmlClientConfigBuilder(configUrl).build();
        }
    }
}

