/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.reactive;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@AutoConfiguration(before={ReactiveSecurityAutoConfiguration.class, ReactiveUserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties(value={OAuth2ResourceServerProperties.class})
@ConditionalOnClass(value={EnableWebFluxSecurity.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@Import(value={ReactiveOAuth2ResourceServerConfiguration.JwtConfiguration.class, ReactiveOAuth2ResourceServerConfiguration.OpaqueTokenConfiguration.class})
public class ReactiveOAuth2ResourceServerAutoConfiguration {
}

