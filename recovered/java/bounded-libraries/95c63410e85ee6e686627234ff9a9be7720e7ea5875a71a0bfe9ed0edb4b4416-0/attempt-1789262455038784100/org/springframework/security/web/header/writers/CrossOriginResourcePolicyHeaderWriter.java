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

public final class CrossOriginResourcePolicyHeaderWriter
implements HeaderWriter {
    private static final String RESOURCE_POLICY = "Cross-Origin-Resource-Policy";
    private CrossOriginResourcePolicy policy;

    public void setPolicy(CrossOriginResourcePolicy resourcePolicy) {
        Assert.notNull((Object)((Object)resourcePolicy), (String)"resourcePolicy cannot be null");
        this.policy = resourcePolicy;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (this.policy != null && !response.containsHeader(RESOURCE_POLICY)) {
            response.addHeader(RESOURCE_POLICY, this.policy.getPolicy());
        }
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

        public static CrossOriginResourcePolicy from(String resourcePolicy) {
            for (CrossOriginResourcePolicy policy : CrossOriginResourcePolicy.values()) {
                if (!policy.getPolicy().equals(resourcePolicy)) continue;
                return policy;
            }
            return null;
        }
    }
}

