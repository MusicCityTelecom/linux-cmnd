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

public final class CrossOriginEmbedderPolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String EMBEDDER_POLICY = "Cross-Origin-Embedder-Policy";
    private ServerHttpHeadersWriter delegate;

    public void setPolicy(CrossOriginEmbedderPolicy embedderPolicy) {
        Assert.notNull((Object)((Object)embedderPolicy), (String)"embedderPolicy cannot be null");
        this.delegate = CrossOriginEmbedderPolicyServerHttpHeadersWriter.createDelegate(embedderPolicy);
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate != null ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    private static ServerHttpHeadersWriter createDelegate(CrossOriginEmbedderPolicy embedderPolicy) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(EMBEDDER_POLICY, embedderPolicy.getPolicy());
        return builder.build();
    }

    public static enum CrossOriginEmbedderPolicy {
        UNSAFE_NONE("unsafe-none"),
        REQUIRE_CORP("require-corp");

        private final String policy;

        private CrossOriginEmbedderPolicy(String policy) {
            this.policy = policy;
        }

        public String getPolicy() {
            return this.policy;
        }
    }
}

