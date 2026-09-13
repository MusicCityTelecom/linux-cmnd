/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.util.matcher;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class OrRequestMatcher
implements RequestMatcher {
    private final List<RequestMatcher> requestMatchers;

    public OrRequestMatcher(List<RequestMatcher> requestMatchers) {
        Assert.notEmpty(requestMatchers, (String)"requestMatchers must contain a value");
        Assert.noNullElements(requestMatchers, (String)"requestMatchers cannot contain null values");
        this.requestMatchers = requestMatchers;
    }

    public OrRequestMatcher(RequestMatcher ... requestMatchers) {
        this(Arrays.asList(requestMatchers));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : this.requestMatchers) {
            if (!matcher.matches(request)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        return "Or " + this.requestMatchers;
    }
}

