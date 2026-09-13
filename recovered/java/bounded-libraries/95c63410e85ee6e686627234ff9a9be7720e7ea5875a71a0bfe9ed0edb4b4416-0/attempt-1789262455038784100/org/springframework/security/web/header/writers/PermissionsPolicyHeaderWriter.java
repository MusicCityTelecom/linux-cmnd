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

public final class PermissionsPolicyHeaderWriter
implements HeaderWriter {
    private static final String PERMISSIONS_POLICY_HEADER = "Permissions-Policy";
    private String policy;

    public PermissionsPolicyHeaderWriter() {
    }

    public PermissionsPolicyHeaderWriter(String policy) {
        this.setPolicy(policy);
    }

    public void setPolicy(String policy) {
        Assert.hasLength((String)policy, (String)"policy can not be null or empty");
        this.policy = policy;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!response.containsHeader(PERMISSIONS_POLICY_HEADER)) {
            response.setHeader(PERMISSIONS_POLICY_HEADER, this.policy);
        }
    }

    public String toString() {
        return this.getClass().getName() + " [policy=" + this.policy + "]";
    }
}

