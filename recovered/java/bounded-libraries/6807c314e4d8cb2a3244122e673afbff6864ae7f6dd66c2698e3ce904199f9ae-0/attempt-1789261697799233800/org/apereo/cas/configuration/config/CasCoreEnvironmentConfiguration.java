/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager
 *  org.apereo.cas.configuration.CommaSeparatedStringToThrowablesConverter
 *  org.apereo.cas.configuration.StandaloneConfigurationFilePropertiesSourceLocator
 *  org.apereo.cas.configuration.api.CasConfigurationPropertiesSourceLocator
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.loader.ConfigurationPropertiesLoaderFactory
 *  org.apereo.cas.configuration.support.CasConfigurationJasyptCipherExecutor
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.ConfigurationPropertiesBinding
 *  org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.configuration.config;

import java.util.List;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager;
import org.apereo.cas.configuration.CommaSeparatedStringToThrowablesConverter;
import org.apereo.cas.configuration.StandaloneConfigurationFilePropertiesSourceLocator;
import org.apereo.cas.configuration.api.CasConfigurationPropertiesSourceLocator;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.loader.ConfigurationPropertiesLoaderFactory;
import org.apereo.cas.configuration.support.CasConfigurationJasyptCipherExecutor;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;

@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.CasConfiguration)
@AutoConfiguration
public class CasCoreEnvironmentConfiguration {

    @Configuration(value="CasCoreEnvironmentLocatorConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreEnvironmentLocatorConfiguration {
        @Bean
        @ConditionalOnMissingBean(name={"standaloneConfigurationFilePropertiesSourceLocator"})
        public CasConfigurationPropertiesSourceLocator standaloneConfigurationFilePropertiesSourceLocator(@Qualifier(value="configurationPropertiesEnvironmentManager") CasConfigurationPropertiesEnvironmentManager configurationPropertiesEnvironmentManager, @Qualifier(value="configurationPropertiesLoaderFactory") ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory) {
            return new StandaloneConfigurationFilePropertiesSourceLocator(configurationPropertiesEnvironmentManager, configurationPropertiesLoaderFactory);
        }
    }

    @Configuration(value="CasCoreEnvironmentFactoryConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreEnvironmentFactoryConfiguration {
        @ConfigurationPropertiesBinding
        @Bean
        public Converter<String, List<Class<? extends Throwable>>> commaSeparatedStringToThrowablesCollection() {
            return new CommaSeparatedStringToThrowablesConverter();
        }

        @ConditionalOnMissingBean(name={"casConfigurationCipherExecutor"})
        @Bean
        public CipherExecutor<String, String> casConfigurationCipherExecutor(Environment environment) {
            return new CasConfigurationJasyptCipherExecutor(environment);
        }

        @ConditionalOnMissingBean(name={"configurationPropertiesLoaderFactory"})
        @Bean
        public ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory(@Qualifier(value="casConfigurationCipherExecutor") CipherExecutor<String, String> casConfigurationCipherExecutor, Environment environment) {
            return new ConfigurationPropertiesLoaderFactory(casConfigurationCipherExecutor, environment);
        }
    }

    @Configuration(value="CasCoreEnvironmentManagerConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreEnvironmentManagerConfiguration {
        @ConditionalOnMissingBean(name={"configurationPropertiesEnvironmentManager"})
        @Bean
        public CasConfigurationPropertiesEnvironmentManager configurationPropertiesEnvironmentManager(ConfigurationPropertiesBindingPostProcessor binder) {
            return new CasConfigurationPropertiesEnvironmentManager(binder);
        }
    }
}

