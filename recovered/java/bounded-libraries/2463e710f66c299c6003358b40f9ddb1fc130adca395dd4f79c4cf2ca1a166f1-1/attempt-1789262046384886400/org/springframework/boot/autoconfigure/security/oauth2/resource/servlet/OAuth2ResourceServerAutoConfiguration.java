/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
 */
package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.Oauth2ResourceServerConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

@AutoConfiguration(before={SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties(value={OAuth2ResourceServerProperties.class})
@ConditionalOnClass(value={BearerTokenAuthenticationToken.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@Import(value={Oauth2ResourceServerConfiguration.JwtConfiguration.class, Oauth2ResourceServerConfiguration.OpaqueTokenConfiguration.class})
public class OAuth2ResourceServerAutoConfiguration {
}

