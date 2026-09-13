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

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class AndRequestMatcher
implements RequestMatcher {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final List<RequestMatcher> requestMatchers;

    public AndRequestMatcher(List<RequestMatcher> requestMatchers) {
        Assert.notEmpty(requestMatchers, (String)"requestMatchers must contain a value");
        Assert.noNullElements(requestMatchers, (String)"requestMatchers cannot contain null values");
        this.requestMatchers = requestMatchers;
    }

    public AndRequestMatcher(RequestMatcher ... requestMatchers) {
        this(Arrays.asList(requestMatchers));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : this.requestMatchers) {
            if (matcher.matches(request)) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return "And " + this.requestMatchers;
    }
}

