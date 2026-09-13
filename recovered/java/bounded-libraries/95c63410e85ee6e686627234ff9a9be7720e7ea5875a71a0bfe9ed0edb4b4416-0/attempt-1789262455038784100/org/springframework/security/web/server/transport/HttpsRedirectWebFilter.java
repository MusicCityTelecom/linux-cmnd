/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  org.springframework.web.util.UriComponentsBuilder
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.transport;

import java.net.URI;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

public final class HttpsRedirectWebFilter
implements WebFilter {
    private PortMapper portMapper = new PortMapperImpl();
    private ServerWebExchangeMatcher requiresHttpsRedirectMatcher = ServerWebExchangeMatchers.anyExchange();
    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.just((Object)exchange).filter(this::isInsecure).flatMap(this.requiresHttpsRedirectMatcher::matches).filter(matchResult -> matchResult.isMatch()).switchIfEmpty(chain.filter(exchange).then(Mono.empty())).map(matchResult -> this.createRedirectUri(exchange)).flatMap(uri -> this.redirectStrategy.sendRedirect(exchange, (URI)uri));
    }

    public void setPortMapper(PortMapper portMapper) {
        Assert.notNull((Object)portMapper, (String)"portMapper cannot be null");
        this.portMapper = portMapper;
    }

    public void setRequiresHttpsRedirectMatcher(ServerWebExchangeMatcher requiresHttpsRedirectMatcher) {
        Assert.notNull((Object)requiresHttpsRedirectMatcher, (String)"requiresHttpsRedirectMatcher cannot be null");
        this.requiresHttpsRedirectMatcher = requiresHttpsRedirectMatcher;
    }

    private boolean isInsecure(ServerWebExchange exchange) {
        return !"https".equals(exchange.getRequest().getURI().getScheme());
    }

    private URI createRedirectUri(ServerWebExchange exchange) {
        int port = exchange.getRequest().getURI().getPort();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri((URI)exchange.getRequest().getURI());
        if (port > 0) {
            Integer httpsPort = this.portMapper.lookupHttpsPort(port);
            Assert.state((httpsPort != null ? 1 : 0) != 0, () -> "HTTP Port '" + port + "' does not have a corresponding HTTPS Port");
            builder.port(httpsPort.intValue());
        }
        return builder.scheme("https").build().toUri();
    }
}

