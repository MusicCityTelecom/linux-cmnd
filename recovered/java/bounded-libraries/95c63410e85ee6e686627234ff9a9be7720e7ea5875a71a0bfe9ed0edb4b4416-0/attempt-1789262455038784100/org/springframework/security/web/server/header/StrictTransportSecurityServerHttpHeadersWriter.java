/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import java.time.Duration;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class StrictTransportSecurityServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    private String maxAge;
    private String subdomain;
    private String preload;
    private ServerHttpHeadersWriter delegate;

    public StrictTransportSecurityServerHttpHeadersWriter() {
        this.setIncludeSubDomains(true);
        this.setMaxAge(Duration.ofDays(365L));
        this.setPreload(false);
        this.updateDelegate();
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.isSecure(exchange) ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    public void setIncludeSubDomains(boolean includeSubDomains) {
        this.subdomain = includeSubDomains ? " ; includeSubDomains" : "";
        this.updateDelegate();
    }

    public void setPreload(boolean preload) {
        this.preload = preload ? " ; preload" : "";
        this.updateDelegate();
    }

    public void setMaxAge(Duration maxAge) {
        this.maxAge = "max-age=" + maxAge.getSeconds();
        this.updateDelegate();
    }

    private void updateDelegate() {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(STRICT_TRANSPORT_SECURITY, this.maxAge + this.subdomain + this.preload);
        this.delegate = builder.build();
    }

    private boolean isSecure(ServerWebExchange exchange) {
        String scheme = exchange.getRequest().getURI().getScheme();
        boolean isSecure = scheme != null && scheme.equalsIgnoreCase("https");
        return isSecure;
    }
}

