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

public final class CrossOriginOpenerPolicyHeaderWriter
implements HeaderWriter {
    private static final String OPENER_POLICY = "Cross-Origin-Opener-Policy";
    private CrossOriginOpenerPolicy policy;

    public void setPolicy(CrossOriginOpenerPolicy openerPolicy) {
        Assert.notNull((Object)((Object)openerPolicy), (String)"openerPolicy cannot be null");
        this.policy = openerPolicy;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (this.policy != null && !response.containsHeader(OPENER_POLICY)) {
            response.addHeader(OPENER_POLICY, this.policy.getPolicy());
        }
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

        public static CrossOriginOpenerPolicy from(String openerPolicy) {
            for (CrossOriginOpenerPolicy policy : CrossOriginOpenerPolicy.values()) {
                if (!policy.getPolicy().equals(openerPolicy)) continue;
                return policy;
            }
            return null;
        }
    }
}

