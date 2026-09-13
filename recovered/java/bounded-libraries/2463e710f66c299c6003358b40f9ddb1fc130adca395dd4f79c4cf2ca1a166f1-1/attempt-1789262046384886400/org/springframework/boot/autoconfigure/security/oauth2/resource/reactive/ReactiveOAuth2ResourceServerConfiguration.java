/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
 *  org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
 *  org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.reactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerJwkConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerOpaqueTokenConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;

class ReactiveOAuth2ResourceServerConfiguration {
    ReactiveOAuth2ResourceServerConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={BearerTokenAuthenticationToken.class, ReactiveOpaqueTokenIntrospector.class})
    @Import(value={ReactiveOAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration.class, ReactiveOAuth2ResourceServerOpaqueTokenConfiguration.WebSecurityConfiguration.class})
    static class OpaqueTokenConfiguration {
        OpaqueTokenConfiguration() {
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={BearerTokenAuthenticationToken.class, ReactiveJwtDecoder.class})
    @Import(value={ReactiveOAuth2ResourceServerJwkConfiguration.JwtConfiguration.class, ReactiveOAuth2ResourceServerJwkConfiguration.WebSecurityConfiguration.class})
    static class JwtConfiguration {
        JwtConfiguration() {
        }
    }
}

