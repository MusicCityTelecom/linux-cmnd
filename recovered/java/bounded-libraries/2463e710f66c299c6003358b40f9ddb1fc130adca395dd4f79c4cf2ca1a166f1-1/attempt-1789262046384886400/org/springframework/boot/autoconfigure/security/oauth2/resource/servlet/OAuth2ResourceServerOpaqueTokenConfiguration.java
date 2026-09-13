/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer$AuthorizedUrl
 *  org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
 *  org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
 *  org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector
 *  org.springframework.security.web.SecurityFilterChain
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods=false)
class OAuth2ResourceServerOpaqueTokenConfiguration {
    OAuth2ResourceServerOpaqueTokenConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnDefaultWebSecurity
    static class OAuth2SecurityFilterChainConfiguration {
        OAuth2SecurityFilterChainConfiguration() {
        }

        @Bean
        @ConditionalOnBean(value={OpaqueTokenIntrospector.class})
        SecurityFilterChain opaqueTokenSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeRequests(requests -> ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated());
            http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken);
            return (SecurityFilterChain)http.build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={OpaqueTokenIntrospector.class})
    static class OpaqueTokenIntrospectionClientConfiguration {
        OpaqueTokenIntrospectionClientConfiguration() {
        }

        @Bean
        @ConditionalOnProperty(name={"spring.security.oauth2.resourceserver.opaquetoken.introspection-uri"})
        SpringOpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2ResourceServerProperties properties) {
            OAuth2ResourceServerProperties.Opaquetoken opaqueToken = properties.getOpaquetoken();
            return new SpringOpaqueTokenIntrospector(opaqueToken.getIntrospectionUri(), opaqueToken.getClientId(), opaqueToken.getClientSecret());
        }
    }
}

