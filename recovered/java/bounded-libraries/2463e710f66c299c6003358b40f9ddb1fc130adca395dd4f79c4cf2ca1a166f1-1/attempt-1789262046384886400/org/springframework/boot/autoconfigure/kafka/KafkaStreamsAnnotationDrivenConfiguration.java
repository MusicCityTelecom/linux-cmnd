/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.kafka.streams.StreamsBuilder
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.env.Environment
 *  org.springframework.kafka.config.KafkaStreamsConfiguration
 *  org.springframework.kafka.config.StreamsBuilderFactoryBean
 *  org.springframework.kafka.core.CleanupConfig
 */
package org.springframework.boot.autoconfigure.kafka;

import java.util.Map;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.CleanupConfig;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={StreamsBuilder.class})
@ConditionalOnBean(name={"defaultKafkaStreamsBuilder"})
class KafkaStreamsAnnotationDrivenConfiguration {
    private final KafkaProperties properties;

    KafkaStreamsAnnotationDrivenConfiguration(KafkaProperties properties) {
        this.properties = properties;
    }

    @ConditionalOnMissingBean
    @Bean(value={"defaultKafkaStreamsConfig"})
    KafkaStreamsConfiguration defaultKafkaStreamsConfig(Environment environment) {
        Map<String, Object> streamsProperties = this.properties.buildStreamsProperties();
        if (this.properties.getStreams().getApplicationId() == null) {
            String applicationName = environment.getProperty("spring.application.name");
            if (applicationName == null) {
                throw new InvalidConfigurationPropertyValueException("spring.kafka.streams.application-id", null, "This property is mandatory and fallback 'spring.application.name' is not set either.");
            }
            streamsProperties.put("application.id", applicationName);
        }
        return new KafkaStreamsConfiguration(streamsProperties);
    }

    @Bean
    KafkaStreamsFactoryBeanConfigurer kafkaStreamsFactoryBeanConfigurer(@Qualifier(value="defaultKafkaStreamsBuilder") StreamsBuilderFactoryBean factoryBean, ObjectProvider<StreamsBuilderFactoryBeanCustomizer> customizers) {
        customizers.orderedStream().forEach(customizer -> customizer.customize(factoryBean));
        return new KafkaStreamsFactoryBeanConfigurer(this.properties, factoryBean);
    }

    static class KafkaStreamsFactoryBeanConfigurer
    implements InitializingBean {
        private final KafkaProperties properties;
        private final StreamsBuilderFactoryBean factoryBean;

        KafkaStreamsFactoryBeanConfigurer(KafkaProperties properties, StreamsBuilderFactoryBean factoryBean) {
            this.properties = properties;
            this.factoryBean = factoryBean;
        }

        public void afterPropertiesSet() {
            this.factoryBean.setAutoStartup(this.properties.getStreams().isAutoStartup());
            KafkaProperties.Cleanup cleanup = this.properties.getStreams().getCleanup();
            CleanupConfig cleanupConfig = new CleanupConfig(cleanup.isOnStartup(), cleanup.isOnShutdown());
            this.factoryBean.setCleanupConfig(cleanupConfig);
        }
    }
}

