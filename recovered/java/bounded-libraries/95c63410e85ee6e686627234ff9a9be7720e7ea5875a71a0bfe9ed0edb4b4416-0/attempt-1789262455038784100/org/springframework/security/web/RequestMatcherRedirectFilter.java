/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public final class RequestMatcherRedirectFilter
extends OncePerRequestFilter {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final RequestMatcher requestMatcher;
    private final String redirectUrl;

    public RequestMatcherRedirectFilter(RequestMatcher requestMatcher, String redirectUrl) {
        Assert.notNull((Object)requestMatcher, (String)"requestMatcher cannot be null");
        Assert.hasText((String)redirectUrl, (String)"redirectUrl cannot be empty");
        this.requestMatcher = requestMatcher;
        this.redirectUrl = redirectUrl;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.requestMatcher.matches(request)) {
            this.redirectStrategy.sendRedirect(request, response, this.redirectUrl);
        } else {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
        }
    }
}

