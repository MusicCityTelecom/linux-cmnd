/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

public final class CrossOriginEmbedderPolicyHeaderWriter
implements HeaderWriter {
    private static final String EMBEDDER_POLICY = "Cross-Origin-Embedder-Policy";
    private CrossOriginEmbedderPolicy policy;

    public void setPolicy(CrossOriginEmbedderPolicy embedderPolicy) {
        Assert.notNull((Object)((Object)embedderPolicy), (String)"embedderPolicy cannot be null");
        this.policy = embedderPolicy;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (this.policy != null && !response.containsHeader(EMBEDDER_POLICY)) {
            response.addHeader(EMBEDDER_POLICY, this.policy.getPolicy());
        }
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

        public static CrossOriginEmbedderPolicy from(String embedderPolicy) {
            for (CrossOriginEmbedderPolicy policy : CrossOriginEmbedderPolicy.values()) {
                if (!policy.getPolicy().equals(embedderPolicy)) continue;
                return policy;
            }
            return null;
        }
    }
}

