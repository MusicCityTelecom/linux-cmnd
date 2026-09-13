/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.http.HttpMethod
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.util.matcher;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

public class DispatcherTypeRequestMatcher
implements RequestMatcher {
    private final DispatcherType dispatcherType;
    @Nullable
    private final HttpMethod httpMethod;

    public DispatcherTypeRequestMatcher(DispatcherType dispatcherType) {
        this(dispatcherType, null);
    }

    public DispatcherTypeRequestMatcher(DispatcherType dispatcherType, @Nullable HttpMethod httpMethod) {
        this.dispatcherType = dispatcherType;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (this.httpMethod != null && StringUtils.hasText((String)request.getMethod()) && this.httpMethod != HttpMethod.resolve((String)request.getMethod())) {
            return false;
        }
        return this.dispatcherType == request.getDispatcherType();
    }

    public String toString() {
        return "DispatcherTypeRequestMatcher{dispatcherType=" + this.dispatcherType + ", httpMethod=" + this.httpMethod + '}';
    }
}

