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
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class DelegatingRequestMatcherHeaderWriter
implements HeaderWriter {
    private final RequestMatcher requestMatcher;
    private final HeaderWriter delegateHeaderWriter;

    public DelegatingRequestMatcherHeaderWriter(RequestMatcher requestMatcher, HeaderWriter delegateHeaderWriter) {
        Assert.notNull((Object)requestMatcher, (String)"requestMatcher cannot be null");
        Assert.notNull((Object)delegateHeaderWriter, (String)"delegateHeaderWriter cannot be null");
        this.requestMatcher = requestMatcher;
        this.delegateHeaderWriter = delegateHeaderWriter;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (this.requestMatcher.matches(request)) {
            this.delegateHeaderWriter.writeHeaders(request, response);
        }
    }

    public String toString() {
        return this.getClass().getName() + " [requestMatcher=" + this.requestMatcher + ", delegateHeaderWriter=" + this.delegateHeaderWriter + "]";
    }
}

