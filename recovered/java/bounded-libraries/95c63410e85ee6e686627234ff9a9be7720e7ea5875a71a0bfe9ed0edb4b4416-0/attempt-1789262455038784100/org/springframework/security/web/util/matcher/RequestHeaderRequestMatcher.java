/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class RequestHeaderRequestMatcher
implements RequestMatcher {
    private final String expectedHeaderName;
    private final String expectedHeaderValue;

    public RequestHeaderRequestMatcher(String expectedHeaderName) {
        this(expectedHeaderName, null);
    }

    public RequestHeaderRequestMatcher(String expectedHeaderName, String expectedHeaderValue) {
        Assert.notNull((Object)expectedHeaderName, (String)"headerName cannot be null");
        this.expectedHeaderName = expectedHeaderName;
        this.expectedHeaderValue = expectedHeaderValue;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String actualHeaderValue = request.getHeader(this.expectedHeaderName);
        if (this.expectedHeaderValue == null) {
            return actualHeaderValue != null;
        }
        return this.expectedHeaderValue.equals(actualHeaderValue);
    }

    public String toString() {
        return "RequestHeaderRequestMatcher [expectedHeaderName=" + this.expectedHeaderName + ", expectedHeaderValue=" + this.expectedHeaderValue + "]";
    }
}

