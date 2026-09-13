/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.web.server.header.ServerHttpHeadersWriter;
import org.springframework.security.web.server.header.StaticServerHttpHeadersWriter;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class ReferrerPolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String REFERRER_POLICY = "Referrer-Policy";
    private ServerHttpHeadersWriter delegate = ReferrerPolicyServerHttpHeadersWriter.createDelegate(ReferrerPolicy.NO_REFERRER);

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate.writeHttpHeaders(exchange);
    }

    public void setPolicy(ReferrerPolicy policy) {
        Assert.notNull((Object)((Object)policy), (String)"policy must not be null");
        this.delegate = ReferrerPolicyServerHttpHeadersWriter.createDelegate(policy);
    }

    private static ServerHttpHeadersWriter createDelegate(ReferrerPolicy policy) {
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(REFERRER_POLICY, policy.getPolicy());
        return builder.build();
    }

    public static enum ReferrerPolicy {
        NO_REFERRER("no-referrer"),
        NO_REFERRER_WHEN_DOWNGRADE("no-referrer-when-downgrade"),
        SAME_ORIGIN("same-origin"),
        ORIGIN("origin"),
        STRICT_ORIGIN("strict-origin"),
        ORIGIN_WHEN_CROSS_ORIGIN("origin-when-cross-origin"),
        STRICT_ORIGIN_WHEN_CROSS_ORIGIN("strict-origin-when-cross-origin"),
        UNSAFE_URL("unsafe-url");

        private static final Map<String, ReferrerPolicy> REFERRER_POLICIES;
        private final String policy;

        private ReferrerPolicy(String policy) {
            this.policy = policy;
        }

        public String getPolicy() {
            return this.policy;
        }

        static {
            HashMap<String, ReferrerPolicy> referrerPolicies = new HashMap<String, ReferrerPolicy>();
            for (ReferrerPolicy referrerPolicy : ReferrerPolicy.values()) {
                referrerPolicies.put(referrerPolicy.getPolicy(), referrerPolicy);
            }
            REFERRER_POLICIES = Collections.unmodifiableMap(referrerPolicies);
        }
    }
}

