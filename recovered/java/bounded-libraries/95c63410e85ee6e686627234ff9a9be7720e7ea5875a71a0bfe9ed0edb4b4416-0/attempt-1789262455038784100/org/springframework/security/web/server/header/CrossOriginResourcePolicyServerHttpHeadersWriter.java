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

public final class CrossOriginResourcePolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String RESOURCE_POLICY = "Cross-Origin-Resource-Policy";
    private ServerHttpHeadersWriter delegate;

    public void setPolicy(CrossOriginResourcePolicy resourcePolicy) {
        Assert.notNull((Object)((Object)resourcePolicy), (String)"resourcePolicy cannot be null");
        this.delegate = CrossOriginResourcePolicyServerHttpHeadersWriter.createDelegate(resourcePolicy);
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate != null ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    private static ServerHttpHeadersWriter createDelegate(CrossOriginResourcePolicy resourcePolicy) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(RESOURCE_POLICY, resourcePolicy.getPolicy());
        return builder.build();
    }

    public static enum CrossOriginResourcePolicy {
        SAME_SITE("same-site"),
        SAME_ORIGIN("same-origin"),
        CROSS_ORIGIN("cross-origin");

        private final String policy;

        private CrossOriginResourcePolicy(String policy) {
            this.policy = policy;
        }

        public String getPolicy() {
            return this.policy;
        }
    }
}

