/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.audit.AuditTrailRecordResolutionPlanConfigurer
 *  org.apereo.cas.authentication.bypass.audit.MultifactorAuthenticationProviderBypassAuditResourceResolver
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeaturesEnabled
 *  org.apereo.inspektr.audit.spi.AuditActionResolver
 *  org.apereo.inspektr.audit.spi.AuditResourceResolver
 *  org.apereo.inspektr.audit.spi.support.DefaultAuditActionResolver
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import org.apereo.cas.audit.AuditTrailRecordResolutionPlanConfigurer;
import org.apereo.cas.authentication.bypass.audit.MultifactorAuthenticationProviderBypassAuditResourceResolver;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.apereo.cas.util.spring.boot.ConditionalOnFeaturesEnabled;
import org.apereo.inspektr.audit.spi.AuditActionResolver;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.apereo.inspektr.audit.spi.support.DefaultAuditActionResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeaturesEnabled(value={@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.Audit), @ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.MultifactorAuthentication)})
@AutoConfiguration
public class CasCoreMultifactorAuthenticationAuditConfiguration {
    @Bean
    @ConditionalOnMissingBean(name={"casCoreMfaAuditTrailRecordResolutionPlanConfigurer"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public AuditTrailRecordResolutionPlanConfigurer casCoreMfaAuditTrailRecordResolutionPlanConfigurer() {
        return plan -> {
            plan.registerAuditResourceResolver("MULTIFACTOR_AUTHENTICATION_BYPASS_RESOURCE_RESOLVER", (AuditResourceResolver)new MultifactorAuthenticationProviderBypassAuditResourceResolver());
            plan.registerAuditActionResolver("MULTIFACTOR_AUTHENTICATION_BYPASS_ACTION_RESOLVER", (AuditActionResolver)new DefaultAuditActionResolver());
        };
    }
}

