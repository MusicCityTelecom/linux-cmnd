/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.plugins.SocketAcceptorInterceptor
 *  org.springframework.boot.rsocket.server.RSocketServerCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver
 *  org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity
 *  org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver
 *  org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor
 */
package org.springframework.boot.autoconfigure.security.rsocket;

import io.rsocket.plugins.SocketAcceptorInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessageHandlerCustomizer;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor;

@AutoConfiguration
@EnableRSocketSecurity
@ConditionalOnClass(value={SecuritySocketAcceptorInterceptor.class})
public class RSocketSecurityAutoConfiguration {
    @Bean
    RSocketServerCustomizer springSecurityRSocketSecurity(SecuritySocketAcceptorInterceptor interceptor) {
        return server -> server.interceptors(registry -> registry.forSocketAcceptor((SocketAcceptorInterceptor)interceptor));
    }

    @ConditionalOnClass(value={AuthenticationPrincipalArgumentResolver.class})
    @Configuration(proxyBeanMethods=false)
    static class RSocketSecurityMessageHandlerConfiguration {
        RSocketSecurityMessageHandlerConfiguration() {
        }

        @Bean
        RSocketMessageHandlerCustomizer rSocketAuthenticationPrincipalMessageHandlerCustomizer() {
            return messageHandler -> messageHandler.getArgumentResolverConfigurer().addCustomResolver(new HandlerMethodArgumentResolver[]{new AuthenticationPrincipalArgumentResolver()});
        }
    }
}

