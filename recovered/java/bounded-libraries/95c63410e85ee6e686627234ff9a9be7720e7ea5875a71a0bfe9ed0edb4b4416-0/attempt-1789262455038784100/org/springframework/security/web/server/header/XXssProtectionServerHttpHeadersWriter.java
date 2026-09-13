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

public class XXssProtectionServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String X_XSS_PROTECTION = "X-XSS-Protection";
    private boolean enabled = true;
    private boolean block = true;
    private ServerHttpHeadersWriter delegate;

    public XXssProtectionServerHttpHeadersWriter() {
        this.updateDelegate();
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate.writeHttpHeaders(exchange);
    }

    public void setEnabled(boolean enabled) {
        if (!enabled) {
            this.setBlock(false);
        }
        this.enabled = enabled;
        this.updateDelegate();
    }

    public void setBlock(boolean block) {
        Assert.isTrue((this.enabled || !block ? 1 : 0) != 0, (String)"Cannot set block to true with enabled false");
        this.block = block;
        this.updateDelegate();
    }

    private void updateDelegate() {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(X_XSS_PROTECTION, this.createHeaderValue());
        this.delegate = builder.build();
    }

    private String createHeaderValue() {
        if (!this.enabled) {
            return "0";
        }
        if (!this.block) {
            return "1";
        }
        return "1 ; mode=block";
    }
}

