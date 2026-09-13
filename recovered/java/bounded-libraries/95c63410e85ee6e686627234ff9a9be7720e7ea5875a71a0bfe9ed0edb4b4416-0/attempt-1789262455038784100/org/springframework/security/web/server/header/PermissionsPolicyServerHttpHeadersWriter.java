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

public final class PermissionsPolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String PERMISSIONS_POLICY = "Permissions-Policy";
    private ServerHttpHeadersWriter delegate;

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate != null ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    private static ServerHttpHeadersWriter createDelegate(String policyDirectives) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(PERMISSIONS_POLICY, policyDirectives);
        return builder.build();
    }

    public void setPolicy(String policy) {
        Assert.notNull((Object)policy, (String)"policy must not be null");
        this.delegate = PermissionsPolicyServerHttpHeadersWriter.createDelegate(policy);
    }
}

