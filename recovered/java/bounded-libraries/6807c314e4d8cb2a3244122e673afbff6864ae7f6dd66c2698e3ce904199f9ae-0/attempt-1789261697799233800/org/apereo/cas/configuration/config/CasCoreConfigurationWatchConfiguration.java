/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.util.spring.beans.BeanCondition
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.core.env.PropertyResolver
 */
package org.apereo.cas.configuration.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager;
import org.apereo.cas.configuration.CasConfigurationWatchService;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.PropertyResolver;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.CasConfiguration)
@AutoConfiguration
public class CasCoreConfigurationWatchConfiguration {
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public InitializingBean casConfigurationWatchService(@Qualifier(value="configurationPropertiesEnvironmentManager") CasConfigurationPropertiesEnvironmentManager configurationPropertiesEnvironmentManager, ConfigurableApplicationContext applicationContext) throws Exception {
        return (InitializingBean)BeanSupplier.of(InitializingBean.class).when(BeanCondition.on((String)"cas.events.core.track-configuration-modifications").isTrue().given((PropertyResolver)applicationContext.getEnvironment())).supply(() -> new CasConfigurationWatchService(configurationPropertiesEnvironmentManager, applicationContext)).otherwiseProxy().get();
    }
}

