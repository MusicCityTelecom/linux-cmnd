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

public final class FeaturePolicyHeaderWriter
implements HeaderWriter {
    private static final String FEATURE_POLICY_HEADER = "Feature-Policy";
    private String policyDirectives;

    public FeaturePolicyHeaderWriter(String policyDirectives) {
        this.setPolicyDirectives(policyDirectives);
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!response.containsHeader(FEATURE_POLICY_HEADER)) {
            response.setHeader(FEATURE_POLICY_HEADER, this.policyDirectives);
        }
    }

    public void setPolicyDirectives(String policyDirectives) {
        Assert.hasLength((String)policyDirectives, (String)"policyDirectives must not be null or empty");
        this.policyDirectives = policyDirectives;
    }

    public String toString() {
        return this.getClass().getName() + " [policyDirectives=" + this.policyDirectives + "]";
    }
}

