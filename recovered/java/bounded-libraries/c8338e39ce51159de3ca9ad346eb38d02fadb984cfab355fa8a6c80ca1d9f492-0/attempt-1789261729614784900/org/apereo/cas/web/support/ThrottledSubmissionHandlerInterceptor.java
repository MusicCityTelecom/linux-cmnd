/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.servlet.AsyncHandlerInterceptor
 *  org.springframework.web.servlet.ModelAndView
 */
package org.apereo.cas.web.support;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public interface ThrottledSubmissionHandlerInterceptor
extends AsyncHandlerInterceptor {
    public static final String BEAN_NAME = "authenticationThrottle";

    public static ThrottledSubmissionHandlerInterceptor noOp() {
        return new ThrottledSubmissionHandlerInterceptor(){};
    }

    default public void recordSubmissionFailure(HttpServletRequest request) {
    }

    default public boolean exceedsThreshold(HttpServletRequest request) {
        return false;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public void release() {
    }

    default public Collection getRecords() {
        return List.of();
    }

    default public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    default public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
    }

    default public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    }
}

