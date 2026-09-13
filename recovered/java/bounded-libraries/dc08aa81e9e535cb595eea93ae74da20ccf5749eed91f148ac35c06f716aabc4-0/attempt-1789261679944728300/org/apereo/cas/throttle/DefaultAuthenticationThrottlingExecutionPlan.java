/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.apereo.cas.throttle;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;
import org.apereo.cas.throttle.ThrottledRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

public class DefaultAuthenticationThrottlingExecutionPlan
implements AuthenticationThrottlingExecutionPlan {
    private final List<HandlerInterceptor> authenticationThrottleInterceptors = new ArrayList<HandlerInterceptor>();
    private final List<ThrottledRequestFilter> authenticationThrottleFilters = new ArrayList<ThrottledRequestFilter>();

    @Override
    @CanIgnoreReturnValue
    public AuthenticationThrottlingExecutionPlan registerAuthenticationThrottleInterceptor(HandlerInterceptor handler) {
        this.authenticationThrottleInterceptors.add(handler);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public AuthenticationThrottlingExecutionPlan registerAuthenticationThrottleFilter(ThrottledRequestFilter filter) {
        this.authenticationThrottleFilters.add(filter);
        return this;
    }

    @Override
    public ThrottledRequestFilter getAuthenticationThrottleFilter() {
        return (request, response) -> this.authenticationThrottleFilters.stream().anyMatch(filter -> filter.supports(request, response));
    }

    @Override
    @Generated
    public List<HandlerInterceptor> getAuthenticationThrottleInterceptors() {
        return this.authenticationThrottleInterceptors;
    }
}

