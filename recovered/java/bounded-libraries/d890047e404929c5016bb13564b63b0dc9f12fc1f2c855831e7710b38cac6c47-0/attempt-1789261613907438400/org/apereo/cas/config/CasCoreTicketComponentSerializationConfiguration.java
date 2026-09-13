/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.features.CasFeatureModule$FeatureCatalog
 *  org.apereo.cas.ticket.ProxyGrantingTicketImpl
 *  org.apereo.cas.ticket.ProxyTicketImpl
 *  org.apereo.cas.ticket.ServiceTicketImpl
 *  org.apereo.cas.ticket.TicketGrantingTicketImpl
 *  org.apereo.cas.ticket.TransientSessionTicketImpl
 *  org.apereo.cas.ticket.expiration.AlwaysExpiresExpirationPolicy
 *  org.apereo.cas.ticket.expiration.BaseDelegatingExpirationPolicy
 *  org.apereo.cas.ticket.expiration.HardTimeoutExpirationPolicy
 *  org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy
 *  org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy$ProxyTicketExpirationPolicy
 *  org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy$ServiceTicketExpirationPolicy
 *  org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy$TransientSessionTicketExpirationPolicy
 *  org.apereo.cas.ticket.expiration.NeverExpiresExpirationPolicy
 *  org.apereo.cas.ticket.expiration.RememberMeDelegatingExpirationPolicy
 *  org.apereo.cas.ticket.expiration.ThrottledUseAndTimeoutExpirationPolicy
 *  org.apereo.cas.ticket.expiration.TicketGrantingTicketExpirationPolicy
 *  org.apereo.cas.ticket.expiration.TimeoutExpirationPolicy
 *  org.apereo.cas.ticket.registry.DefaultEncodedTicket
 *  org.apereo.cas.util.serialization.ComponentSerializationPlanConfigurer
 *  org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled
 *  org.springframework.boot.autoconfigure.AutoConfiguration
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ScopedProxyMode
 */
package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.ticket.ProxyGrantingTicketImpl;
import org.apereo.cas.ticket.ProxyTicketImpl;
import org.apereo.cas.ticket.ServiceTicketImpl;
import org.apereo.cas.ticket.TicketGrantingTicketImpl;
import org.apereo.cas.ticket.TransientSessionTicketImpl;
import org.apereo.cas.ticket.expiration.AlwaysExpiresExpirationPolicy;
import org.apereo.cas.ticket.expiration.BaseDelegatingExpirationPolicy;
import org.apereo.cas.ticket.expiration.HardTimeoutExpirationPolicy;
import org.apereo.cas.ticket.expiration.MultiTimeUseOrTimeoutExpirationPolicy;
import org.apereo.cas.ticket.expiration.NeverExpiresExpirationPolicy;
import org.apereo.cas.ticket.expiration.RememberMeDelegatingExpirationPolicy;
import org.apereo.cas.ticket.expiration.ThrottledUseAndTimeoutExpirationPolicy;
import org.apereo.cas.ticket.expiration.TicketGrantingTicketExpirationPolicy;
import org.apereo.cas.ticket.expiration.TimeoutExpirationPolicy;
import org.apereo.cas.ticket.registry.DefaultEncodedTicket;
import org.apereo.cas.util.serialization.ComponentSerializationPlanConfigurer;
import org.apereo.cas.util.spring.boot.ConditionalOnFeatureEnabled;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@ConditionalOnFeatureEnabled(feature=CasFeatureModule.FeatureCatalog.TicketRegistry)
@AutoConfiguration
public class CasCoreTicketComponentSerializationConfiguration {
    @Bean
    @ConditionalOnMissingBean(name={"coreTicketsComponentSerializationPlanConfigurer"})
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    public ComponentSerializationPlanConfigurer coreTicketsComponentSerializationPlanConfigurer() {
        return plan -> {
            plan.registerSerializableClass(RememberMeDelegatingExpirationPolicy.class);
            plan.registerSerializableClass(TicketGrantingTicketImpl.class);
            plan.registerSerializableClass(ServiceTicketImpl.class);
            plan.registerSerializableClass(ProxyGrantingTicketImpl.class);
            plan.registerSerializableClass(ProxyTicketImpl.class);
            plan.registerSerializableClass(DefaultEncodedTicket.class);
            plan.registerSerializableClass(TransientSessionTicketImpl.class);
            plan.registerSerializableClass(MultiTimeUseOrTimeoutExpirationPolicy.class);
            plan.registerSerializableClass(MultiTimeUseOrTimeoutExpirationPolicy.ServiceTicketExpirationPolicy.class);
            plan.registerSerializableClass(MultiTimeUseOrTimeoutExpirationPolicy.ProxyTicketExpirationPolicy.class);
            plan.registerSerializableClass(MultiTimeUseOrTimeoutExpirationPolicy.TransientSessionTicketExpirationPolicy.class);
            plan.registerSerializableClass(NeverExpiresExpirationPolicy.class);
            plan.registerSerializableClass(RememberMeDelegatingExpirationPolicy.class);
            plan.registerSerializableClass(TimeoutExpirationPolicy.class);
            plan.registerSerializableClass(HardTimeoutExpirationPolicy.class);
            plan.registerSerializableClass(AlwaysExpiresExpirationPolicy.class);
            plan.registerSerializableClass(ThrottledUseAndTimeoutExpirationPolicy.class);
            plan.registerSerializableClass(TicketGrantingTicketExpirationPolicy.class);
            plan.registerSerializableClass(BaseDelegatingExpirationPolicy.class);
        };
    }
}

