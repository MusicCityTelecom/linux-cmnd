/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.accept.PathExtensionContentNegotiationStrategy
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.springframework.boot.actuate.endpoint.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.HandlerInterceptor;

final class SkipPathExtensionContentNegotiation
implements HandlerInterceptor {
    private static final String SKIP_ATTRIBUTE = PathExtensionContentNegotiationStrategy.class.getName() + ".SKIP";

    SkipPathExtensionContentNegotiation() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(SKIP_ATTRIBUTE, (Object)Boolean.TRUE);
        return true;
    }
}

