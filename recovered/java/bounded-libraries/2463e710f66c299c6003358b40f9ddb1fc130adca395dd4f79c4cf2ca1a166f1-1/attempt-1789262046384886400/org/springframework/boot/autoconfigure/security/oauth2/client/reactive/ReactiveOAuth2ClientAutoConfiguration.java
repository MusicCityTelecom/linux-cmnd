/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
 *  org.springframework.security.oauth2.client.registration.ClientRegistration
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.security.oauth2.client.reactive;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientConfigurations;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import reactor.core.publisher.Flux;

@AutoConfiguration(before={ReactiveSecurityAutoConfiguration.class})
@EnableConfigurationProperties(value={OAuth2ClientProperties.class})
@Conditional(value={NonServletApplicationCondition.class})
@ConditionalOnClass(value={Flux.class, EnableWebFluxSecurity.class, ClientRegistration.class})
@Import(value={ReactiveOAuth2ClientConfigurations.ReactiveClientRegistrationRepositoryConfiguration.class, ReactiveOAuth2ClientConfigurations.ReactiveOAuth2ClientConfiguration.class})
public class ReactiveOAuth2ClientAutoConfiguration {

    static class NonServletApplicationCondition
    extends NoneNestedConditions {
        NonServletApplicationCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
        static class ServletApplicationCondition {
            ServletApplicationCondition() {
            }
        }
    }
}

