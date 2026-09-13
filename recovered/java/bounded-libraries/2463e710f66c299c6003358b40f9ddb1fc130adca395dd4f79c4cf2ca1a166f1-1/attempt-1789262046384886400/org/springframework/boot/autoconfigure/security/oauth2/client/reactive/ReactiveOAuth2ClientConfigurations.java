/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.config.web.server.ServerHttpSecurity
 *  org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService
 *  org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService
 *  org.springframework.security.oauth2.client.registration.ClientRegistration
 *  org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository
 *  org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
 *  org.springframework.security.oauth2.client.web.server.AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository
 *  org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
 *  org.springframework.security.web.server.SecurityWebFilterChain
 */
package org.springframework.boot.autoconfigure.security.oauth2.client.reactive;

import java.util.ArrayList;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

class ReactiveOAuth2ClientConfigurations {
    ReactiveOAuth2ClientConfigurations() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={ReactiveClientRegistrationRepository.class})
    static class ReactiveOAuth2ClientConfiguration {
        ReactiveOAuth2ClientConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        ReactiveOAuth2AuthorizedClientService authorizedClientService(ReactiveClientRegistrationRepository clientRegistrationRepository) {
            return new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository);
        }

        @Bean
        @ConditionalOnMissingBean
        ServerOAuth2AuthorizedClientRepository authorizedClientRepository(ReactiveOAuth2AuthorizedClientService authorizedClientService) {
            return new AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository(authorizedClientService);
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
        static class SecurityWebFilterChainConfiguration {
            SecurityWebFilterChainConfiguration() {
            }

            @Bean
            @ConditionalOnMissingBean
            SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
                http.authorizeExchange().anyExchange().authenticated();
                http.oauth2Login();
                http.oauth2Client();
                return http.build();
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @Conditional(value={ClientsConfiguredCondition.class})
    @ConditionalOnMissingBean(value={ReactiveClientRegistrationRepository.class})
    static class ReactiveClientRegistrationRepositoryConfiguration {
        ReactiveClientRegistrationRepositoryConfiguration() {
        }

        @Bean
        InMemoryReactiveClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {
            ArrayList<ClientRegistration> registrations = new ArrayList<ClientRegistration>(OAuth2ClientPropertiesRegistrationAdapter.getClientRegistrations(properties).values());
            return new InMemoryReactiveClientRegistrationRepository(registrations);
        }
    }
}

