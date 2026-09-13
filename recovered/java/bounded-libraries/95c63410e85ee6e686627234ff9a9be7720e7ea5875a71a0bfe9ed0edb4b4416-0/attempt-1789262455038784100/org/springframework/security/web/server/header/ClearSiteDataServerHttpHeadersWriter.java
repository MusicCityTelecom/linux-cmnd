/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class ClearSiteDataServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String CLEAR_SITE_DATA_HEADER = "Clear-Site-Data";
    private final StaticServerHttpHeadersWriter headerWriterDelegate;

    public ClearSiteDataServerHttpHeadersWriter(Directive ... directives) {
        Assert.notEmpty((Object[])directives, (String)"directives cannot be empty or null");
        this.headerWriterDelegate = StaticServerHttpHeadersWriter.builder().header(CLEAR_SITE_DATA_HEADER, this.transformToHeaderValue(directives)).build();
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        if (this.isSecure(exchange)) {
            return this.headerWriterDelegate.writeHttpHeaders(exchange);
        }
        return Mono.empty();
    }

    private String transformToHeaderValue(Directive ... directives) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < directives.length - 1; ++i) {
            sb.append(directives[i].headerValue).append(", ");
        }
        sb.append(directives[directives.length - 1].headerValue);
        return sb.toString();
    }

    private boolean isSecure(ServerWebExchange exchange) {
        String scheme = exchange.getRequest().getURI().getScheme();
        return scheme != null && scheme.equalsIgnoreCase("https");
    }

    public static enum Directive {
        CACHE("cache"),
        COOKIES("cookies"),
        STORAGE("storage"),
        EXECUTION_CONTEXTS("executionContexts"),
        ALL("*");

        private final String headerValue;

        private Directive(String headerValue) {
            this.headerValue = "\"" + headerValue + "\"";
        }

        public String getHeaderValue() {
            return this.headerValue;
        }
    }
}

