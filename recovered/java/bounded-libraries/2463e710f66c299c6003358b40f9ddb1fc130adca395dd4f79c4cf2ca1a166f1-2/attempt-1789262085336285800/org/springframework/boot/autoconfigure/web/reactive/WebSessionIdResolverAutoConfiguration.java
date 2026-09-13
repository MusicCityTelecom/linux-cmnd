/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException
 *  org.springframework.boot.web.server.Cookie
 *  org.springframework.context.annotation.Bean
 *  org.springframework.http.ResponseCookie$ResponseCookieBuilder
 *  org.springframework.util.StringUtils
 *  org.springframework.web.server.session.CookieWebSessionIdResolver
 *  org.springframework.web.server.session.WebSessionIdResolver
 *  org.springframework.web.server.session.WebSessionManager
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException;
import org.springframework.boot.web.server.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseCookie;
import org.springframework.util.StringUtils;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;

@AutoConfiguration
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(value={WebSessionManager.class, Mono.class})
@EnableConfigurationProperties(value={WebFluxProperties.class, ServerProperties.class})
public class WebSessionIdResolverAutoConfiguration {
    private final ServerProperties serverProperties;
    private final WebFluxProperties webFluxProperties;

    public WebSessionIdResolverAutoConfiguration(ServerProperties serverProperties, WebFluxProperties webFluxProperties) {
        this.serverProperties = serverProperties;
        this.webFluxProperties = webFluxProperties;
        this.assertNoMutuallyExclusiveProperties(serverProperties, webFluxProperties);
    }

    private void assertNoMutuallyExclusiveProperties(ServerProperties serverProperties, WebFluxProperties webFluxProperties) {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleNonNullValuesIn(entries -> {
            entries.put("spring.webflux.session.cookie.same-site", webFluxProperties.getSession().getCookie().getSameSite());
            entries.put("server.reactive.session.cookie.same-site", serverProperties.getReactive().getSession().getCookie().getSameSite());
        });
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSessionIdResolver webSessionIdResolver() {
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
        String cookieName = this.serverProperties.getReactive().getSession().getCookie().getName();
        if (StringUtils.hasText((String)cookieName)) {
            resolver.setCookieName(cookieName);
        }
        resolver.addCookieInitializer(this::initializeCookie);
        return resolver;
    }

    private void initializeCookie(ResponseCookie.ResponseCookieBuilder builder) {
        Cookie cookie = this.serverProperties.getReactive().getSession().getCookie();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(() -> ((Cookie)cookie).getDomain()).to(arg_0 -> ((ResponseCookie.ResponseCookieBuilder)builder).domain(arg_0));
        map.from(() -> ((Cookie)cookie).getPath()).to(arg_0 -> ((ResponseCookie.ResponseCookieBuilder)builder).path(arg_0));
        map.from(() -> ((Cookie)cookie).getHttpOnly()).to(arg_0 -> ((ResponseCookie.ResponseCookieBuilder)builder).httpOnly(arg_0));
        map.from(() -> ((Cookie)cookie).getSecure()).to(arg_0 -> ((ResponseCookie.ResponseCookieBuilder)builder).secure(arg_0));
        map.from(() -> ((Cookie)cookie).getMaxAge()).to(arg_0 -> ((ResponseCookie.ResponseCookieBuilder)builder).maxAge(arg_0));
        map.from((Object)this.getSameSite(cookie)).to(arg_0 -> ((ResponseCookie.ResponseCookieBuilder)builder).sameSite(arg_0));
    }

    private String getSameSite(Cookie properties) {
        if (properties.getSameSite() != null) {
            return properties.getSameSite().attributeValue();
        }
        WebFluxProperties.Cookie deprecatedProperties = this.webFluxProperties.getSession().getCookie();
        if (deprecatedProperties.getSameSite() != null) {
            return deprecatedProperties.getSameSite().attribute();
        }
        return null;
    }
}

