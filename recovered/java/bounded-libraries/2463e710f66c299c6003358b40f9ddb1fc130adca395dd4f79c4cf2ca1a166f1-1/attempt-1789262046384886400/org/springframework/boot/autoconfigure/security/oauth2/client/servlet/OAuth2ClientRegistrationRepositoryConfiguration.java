/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.oauth2.client.registration.ClientRegistration
 *  org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
 *  org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
 */
package org.springframework.boot.autoconfigure.security.oauth2.client.servlet;

import java.util.ArrayList;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(value={OAuth2ClientProperties.class})
@Conditional(value={ClientsConfiguredCondition.class})
class OAuth2ClientRegistrationRepositoryConfiguration {
    OAuth2ClientRegistrationRepositoryConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(value={ClientRegistrationRepository.class})
    InMemoryClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {
        ArrayList<ClientRegistration> registrations = new ArrayList<ClientRegistration>(OAuth2ClientPropertiesRegistrationAdapter.getClientRegistrations(properties).values());
        return new InMemoryClientRegistrationRepository(registrations);
    }
}

