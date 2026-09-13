/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.oauth2.jwt.JwtDecoder
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerJwtConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerOpaqueTokenConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;

class Oauth2ResourceServerConfiguration {
    Oauth2ResourceServerConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @Import(value={OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration.class, OAuth2ResourceServerOpaqueTokenConfiguration.OAuth2SecurityFilterChainConfiguration.class})
    static class OpaqueTokenConfiguration {
        OpaqueTokenConfiguration() {
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={JwtDecoder.class})
    @Import(value={OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration.class, OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration.class})
    static class JwtConfiguration {
        JwtConfiguration() {
        }
    }
}

