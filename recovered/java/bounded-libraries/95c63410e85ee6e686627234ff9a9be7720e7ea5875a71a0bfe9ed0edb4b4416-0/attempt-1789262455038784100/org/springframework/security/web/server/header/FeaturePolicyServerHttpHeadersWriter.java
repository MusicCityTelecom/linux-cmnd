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

public final class FeaturePolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String FEATURE_POLICY = "Feature-Policy";
    private ServerHttpHeadersWriter delegate;

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate != null ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    public void setPolicyDirectives(String policyDirectives) {
        Assert.hasLength((String)policyDirectives, (String)"policyDirectives must not be null or empty");
        this.delegate = FeaturePolicyServerHttpHeadersWriter.createDelegate(policyDirectives);
    }

    private static ServerHttpHeadersWriter createDelegate(String policyDirectives) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(FEATURE_POLICY, policyDirectives);
        return builder.build();
    }
}

