/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.apereo.cas.throttle;

import java.util.List;
import org.apereo.cas.throttle.ThrottledRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

public interface AuthenticationThrottlingExecutionPlan {
    public static final String BEAN_NAME = "authenticationThrottlingExecutionPlan";

    public AuthenticationThrottlingExecutionPlan registerAuthenticationThrottleInterceptor(HandlerInterceptor var1);

    public AuthenticationThrottlingExecutionPlan registerAuthenticationThrottleFilter(ThrottledRequestFilter var1);

    public List<HandlerInterceptor> getAuthenticationThrottleInterceptors();

    public ThrottledRequestFilter getAuthenticationThrottleFilter();
}

