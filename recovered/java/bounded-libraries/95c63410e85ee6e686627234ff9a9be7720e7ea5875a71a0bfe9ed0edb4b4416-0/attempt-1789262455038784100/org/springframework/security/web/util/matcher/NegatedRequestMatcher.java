/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public class NegatedRequestMatcher
implements RequestMatcher {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final RequestMatcher requestMatcher;

    public NegatedRequestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull((Object)requestMatcher, (String)"requestMatcher cannot be null");
        this.requestMatcher = requestMatcher;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !this.requestMatcher.matches(request);
    }

    public String toString() {
        return "Not [" + this.requestMatcher + "]";
    }
}

