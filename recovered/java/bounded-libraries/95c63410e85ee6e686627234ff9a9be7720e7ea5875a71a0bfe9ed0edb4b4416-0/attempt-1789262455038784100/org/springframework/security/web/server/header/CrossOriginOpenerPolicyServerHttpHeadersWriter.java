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

public final class CrossOriginOpenerPolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String OPENER_POLICY = "Cross-Origin-Opener-Policy";
    private ServerHttpHeadersWriter delegate;

    public void setPolicy(CrossOriginOpenerPolicy openerPolicy) {
        Assert.notNull((Object)((Object)openerPolicy), (String)"openerPolicy cannot be null");
        this.delegate = CrossOriginOpenerPolicyServerHttpHeadersWriter.createDelegate(openerPolicy);
    }

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate != null ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    private static ServerHttpHeadersWriter createDelegate(CrossOriginOpenerPolicy openerPolicy) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(OPENER_POLICY, openerPolicy.getPolicy());
        return builder.build();
    }

    public static enum CrossOriginOpenerPolicy {
        UNSAFE_NONE("unsafe-none"),
        SAME_ORIGIN_ALLOW_POPUPS("same-origin-allow-popups"),
        SAME_ORIGIN("same-origin");

        private final String policy;

        private CrossOriginOpenerPolicy(String policy) {
            this.policy = policy;
        }

        public String getPolicy() {
            return this.policy;
        }
    }
}

