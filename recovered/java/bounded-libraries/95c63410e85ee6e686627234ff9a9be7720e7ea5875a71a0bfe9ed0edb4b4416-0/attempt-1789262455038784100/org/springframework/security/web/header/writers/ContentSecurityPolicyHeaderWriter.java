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

public final class ContentSecurityPolicyHeaderWriter
implements HeaderWriter {
    private static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";
    private static final String CONTENT_SECURITY_POLICY_REPORT_ONLY_HEADER = "Content-Security-Policy-Report-Only";
    private static final String DEFAULT_SRC_SELF_POLICY = "default-src 'self'";
    private String policyDirectives;
    private boolean reportOnly;

    public ContentSecurityPolicyHeaderWriter() {
        this.setPolicyDirectives(DEFAULT_SRC_SELF_POLICY);
        this.reportOnly = false;
    }

    public ContentSecurityPolicyHeaderWriter(String policyDirectives) {
        this.setPolicyDirectives(policyDirectives);
        this.reportOnly = false;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        String headerName;
        String string = headerName = !this.reportOnly ? CONTENT_SECURITY_POLICY_HEADER : CONTENT_SECURITY_POLICY_REPORT_ONLY_HEADER;
        if (!response.containsHeader(headerName)) {
            response.setHeader(headerName, this.policyDirectives);
        }
    }

    public void setPolicyDirectives(String policyDirectives) {
        Assert.hasLength((String)policyDirectives, (String)"policyDirectives cannot be null or empty");
        this.policyDirectives = policyDirectives;
    }

    public void setReportOnly(boolean reportOnly) {
        this.reportOnly = reportOnly;
    }

    public String toString() {
        return this.getClass().getName() + " [policyDirectives=" + this.policyDirectives + "; reportOnly=" + this.reportOnly + "]";
    }
}

