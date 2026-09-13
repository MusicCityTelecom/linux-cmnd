/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.ProtocolAttributeEncoder
 *  org.apereo.cas.authentication.support.DefaultCasProtocolAttributeEncoder
 *  org.apereo.cas.authentication.support.NoOpProtocolAttributeEncoder
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.services.RegisteredServiceCipherExecutor
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.scheduling.annotation.EnableAsync
 */
package org.apereo.cas.config;

import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.authentication.support.DefaultCasProtocolAttributeEncoder;
import org.apereo.cas.authentication.support.NoOpProtocolAttributeEncoder;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.services.RegisteredServiceCipherExecutor;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@EnableAsync(proxyTargetClass=false)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.ServiceRegistry)
@AutoConfiguration(after={CasCoreServicesConfiguration.class})
public class CasCoreServicesAuthenticationConfiguration {
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public ProtocolAttributeEncoder noOpCasAttributeEncoder() {
        return new NoOpProtocolAttributeEncoder();
    }

    @ConditionalOnMissingBean(name={"casAttributeEncoder"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    @Bean
    public ProtocolAttributeEncoder casAttributeEncoder(@Qualifier(value="servicesManager") ServicesManager servicesManager, @Qualifier(value="cacheCredentialsCipherExecutor") CipherExecutor cacheCredentialsCipherExecutor, @Qualifier(value="registeredServiceCipherExecutor") RegisteredServiceCipherExecutor registeredServiceCipherExecutor) {
        return new DefaultCasProtocolAttributeEncoder(servicesManager, registeredServiceCipherExecutor, cacheCredentialsCipherExecutor);
    }
}

