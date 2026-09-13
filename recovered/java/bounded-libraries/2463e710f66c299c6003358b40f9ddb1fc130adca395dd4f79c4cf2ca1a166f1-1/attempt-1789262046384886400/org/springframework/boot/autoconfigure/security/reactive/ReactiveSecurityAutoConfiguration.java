/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
 *  org.springframework.security.web.server.WebFilterChainProxy
 *  org.springframework.web.reactive.config.WebFluxConfigurer
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.security.reactive;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Flux;

@AutoConfiguration
@EnableConfigurationProperties(value={SecurityProperties.class})
@ConditionalOnClass(value={Flux.class, EnableWebFluxSecurity.class, WebFilterChainProxy.class, WebFluxConfigurer.class})
public class ReactiveSecurityAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={WebFilterChainProxy.class})
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
    @EnableWebFluxSecurity
    static class EnableWebFluxSecurityConfiguration {
        EnableWebFluxSecurityConfiguration() {
        }
    }
}

