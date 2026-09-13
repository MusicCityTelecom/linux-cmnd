/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

public class ReferrerPolicyHeaderWriter
implements HeaderWriter {
    private static final String REFERRER_POLICY_HEADER = "Referrer-Policy";
    private ReferrerPolicy policy;

    public ReferrerPolicyHeaderWriter() {
        this(ReferrerPolicy.NO_REFERRER);
    }

    public ReferrerPolicyHeaderWriter(ReferrerPolicy policy) {
        this.setPolicy(policy);
    }

    public void setPolicy(ReferrerPolicy policy) {
        Assert.notNull((Object)((Object)policy), (String)"policy can not be null");
        this.policy = policy;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!response.containsHeader(REFERRER_POLICY_HEADER)) {
            response.setHeader(REFERRER_POLICY_HEADER, this.policy.getPolicy());
        }
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

        public static ReferrerPolicy get(String referrerPolicy) {
            return REFERRER_POLICIES.get(referrerPolicy);
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

