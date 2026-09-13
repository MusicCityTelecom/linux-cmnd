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

public final class ContentSecurityPolicyServerHttpHeadersWriter
implements ServerHttpHeadersWriter {
    public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    public static final String CONTENT_SECURITY_POLICY_REPORT_ONLY = "Content-Security-Policy-Report-Only";
    private String policyDirectives;
    private boolean reportOnly;
    private ServerHttpHeadersWriter delegate;

    @Override
    public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
        return this.delegate != null ? this.delegate.writeHttpHeaders(exchange) : Mono.empty();
    }

    public void setPolicyDirectives(String policyDirectives) {
        Assert.hasLength((String)policyDirectives, (String)"policyDirectives must not be null or empty");
        this.policyDirectives = policyDirectives;
        this.delegate = this.createDelegate();
    }

    public void setReportOnly(boolean reportOnly) {
        this.reportOnly = reportOnly;
        this.delegate = this.createDelegate();
    }

    private ServerHttpHeadersWriter createDelegate() {
        if (this.policyDirectives == null) {
            return null;
        }
        StaticServerHttpHeadersWriter.Builder builder = StaticServerHttpHeadersWriter.builder();
        builder.header(ContentSecurityPolicyServerHttpHeadersWriter.resolveHeader(this.reportOnly), this.policyDirectives);
        return builder.build();
    }

    private static String resolveHeader(boolean reportOnly) {
        return reportOnly ? CONTENT_SECURITY_POLICY_REPORT_ONLY : CONTENT_SECURITY_POLICY;
    }
}

