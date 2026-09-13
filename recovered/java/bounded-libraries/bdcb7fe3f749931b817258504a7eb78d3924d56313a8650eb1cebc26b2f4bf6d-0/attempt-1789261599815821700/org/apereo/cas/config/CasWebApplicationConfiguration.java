/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.AutoConfigureOrder
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.scheduling.annotation.EnableAsync
 */
package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.web.CasWebApplicationReady;
import org.apereo.cas.web.CasWebApplicationReadyListener;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@AutoConfigureOrder(value=-2147483648)
@EnableAsync(proxyTargetClass=false)
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.WebApplication)
@AutoConfiguration
public class CasWebApplicationConfiguration {
    @ConditionalOnMissingBean(name={"casWebApplicationReadyListener"})
    @Bean
    public CasWebApplicationReadyListener casWebApplicationReadyListener() {
        return new CasWebApplicationReady();
    }
}

