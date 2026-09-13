/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.util.matcher;

import java.util.Collections;
import java.util.Map;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ServerWebExchangeMatcher {
    public Mono<MatchResult> matches(ServerWebExchange var1);

    public static class MatchResult {
        private final boolean match;
        private final Map<String, Object> variables;

        private MatchResult(boolean match, Map<String, Object> variables) {
            this.match = match;
            this.variables = variables;
        }

        public boolean isMatch() {
            return this.match;
        }

        public Map<String, Object> getVariables() {
            return this.variables;
        }

        public static Mono<MatchResult> match() {
            return MatchResult.match(Collections.emptyMap());
        }

        public static Mono<MatchResult> match(Map<String, Object> variables) {
            return Mono.just((Object)new MatchResult(true, variables));
        }

        public static Mono<MatchResult> notMatch() {
            return Mono.just((Object)new MatchResult(false, Collections.emptyMap()));
        }
    }
}

