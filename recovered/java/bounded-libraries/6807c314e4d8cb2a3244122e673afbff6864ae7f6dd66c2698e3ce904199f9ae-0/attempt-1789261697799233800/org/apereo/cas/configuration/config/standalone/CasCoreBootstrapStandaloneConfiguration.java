/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager
 *  org.apereo.cas.configuration.DefaultCasConfigurationPropertiesSourceLocator
 *  org.apereo.cas.configuration.api.CasConfigurationPropertiesSourceLocator
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.configuration.loader.ConfigurationPropertiesLoaderFactory
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.bootstrap.config.PropertySourceLocator
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Profile
 *  org.springframework.core.PriorityOrdered
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.core.env.CompositePropertySource
 *  org.springframework.core.io.ResourceLoader
 */
package org.apereo.cas.configuration.config.standalone;

import java.util.List;
import java.util.Optional;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager;
import org.apereo.cas.configuration.DefaultCasConfigurationPropertiesSourceLocator;
import org.apereo.cas.configuration.api.CasConfigurationPropertiesSourceLocator;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.loader.ConfigurationPropertiesLoaderFactory;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.io.ResourceLoader;

@ConditionalOnProperty(value={"spring.cloud.config.enabled"}, havingValue="false", matchIfMissing=true)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.CasConfiguration)
@AutoConfiguration
public class CasCoreBootstrapStandaloneConfiguration {

    @Configuration(value="CasCoreBootstrapStandaloneLocatorConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    @Profile(value={"standalone", "embedded"})
    public static class CasCoreBootstrapStandaloneLocatorConfiguration {
        @ConditionalOnMissingBean(name={"casConfigurationPropertiesSourceLocator"})
        @Bean
        public CasConfigurationPropertiesSourceLocator casConfigurationPropertiesSourceLocator(@Qualifier(value="configurationPropertiesLoaderFactory") ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory, @Qualifier(value="configurationPropertiesEnvironmentManager") CasConfigurationPropertiesEnvironmentManager configurationPropertiesEnvironmentManager) {
            return new DefaultCasConfigurationPropertiesSourceLocator(configurationPropertiesEnvironmentManager, configurationPropertiesLoaderFactory);
        }
    }

    @Configuration(value="CasCoreBootstrapStandaloneSourcesConfiguration", proxyBeanMethods=false)
    @EnableConfigurationProperties(value={CasConfigurationProperties.class})
    public static class CasCoreBootstrapStandaloneSourcesConfiguration
    implements PriorityOrdered {
        @Bean
        public PropertySourceLocator casCoreBootstrapPropertySourceLocator(List<CasConfigurationPropertiesSourceLocator> locatorList, ResourceLoader resourceLoader) {
            AnnotationAwareOrderComparator.sortIfNecessary(locatorList);
            return environment -> {
                CompositePropertySource composite = new CompositePropertySource("casCoreBootstrapPropertySourceLocator");
                locatorList.stream().map(locator -> locator.locate(environment, resourceLoader)).filter(Optional::isPresent).map(Optional::get).forEach(arg_0 -> ((CompositePropertySource)composite).addPropertySource(arg_0));
                return composite;
            };
        }

        public int getOrder() {
            return Integer.MAX_VALUE;
        }
    }
}

