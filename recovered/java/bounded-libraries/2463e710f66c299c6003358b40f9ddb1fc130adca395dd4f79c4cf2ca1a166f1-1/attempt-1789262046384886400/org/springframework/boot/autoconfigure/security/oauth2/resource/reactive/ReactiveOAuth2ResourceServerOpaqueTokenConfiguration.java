/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.config.web.server.ServerHttpSecurity
 *  org.springframework.security.config.web.server.ServerHttpSecurity$OAuth2ResourceServerSpec
 *  org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector
 *  org.springframework.security.oauth2.server.resource.introspection.SpringReactiveOpaqueTokenIntrospector
 *  org.springframework.security.web.server.SecurityWebFilterChain
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.reactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;

class ReactiveOAuth2ResourceServerOpaqueTokenConfiguration {
    ReactiveOAuth2ResourceServerOpaqueTokenConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={SecurityWebFilterChain.class})
    static class WebSecurityConfiguration {
        WebSecurityConfiguration() {
        }

        @Bean
        @ConditionalOnBean(value={ReactiveOpaqueTokenIntrospector.class})
        SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            http.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated());
            http.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::opaqueToken);
            return http.build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={ReactiveOpaqueTokenIntrospector.class})
    static class OpaqueTokenIntrospectionClientConfiguration {
        OpaqueTokenIntrospectionClientConfiguration() {
        }

        @Bean
        @ConditionalOnProperty(name={"spring.security.oauth2.resourceserver.opaquetoken.introspection-uri"})
        SpringReactiveOpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2ResourceServerProperties properties) {
            OAuth2ResourceServerProperties.Opaquetoken opaqueToken = properties.getOpaquetoken();
            return new SpringReactiveOpaqueTokenIntrospector(opaqueToken.getIntrospectionUri(), opaqueToken.getClientId(), opaqueToken.getClientSecret());
        }
    }
}

