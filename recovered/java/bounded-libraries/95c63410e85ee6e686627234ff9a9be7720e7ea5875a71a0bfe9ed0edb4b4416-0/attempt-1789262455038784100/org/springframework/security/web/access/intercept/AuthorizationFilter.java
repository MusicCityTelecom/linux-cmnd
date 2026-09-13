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
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
 *  org.springframework.security.authorization.AuthorizationDecision
 *  org.springframework.security.authorization.AuthorizationEventPublisher
 *  org.springframework.security.authorization.AuthorizationManager
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web.access.intercept;

import java.io.IOException;
import java.util.function.Supplier;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationFilter
extends OncePerRequestFilter {
    private final AuthorizationManager<HttpServletRequest> authorizationManager;
    private AuthorizationEventPublisher eventPublisher = AuthorizationFilter::noPublish;
    private boolean shouldFilterAllDispatcherTypes = false;

    public AuthorizationFilter(AuthorizationManager<HttpServletRequest> authorizationManager) {
        Assert.notNull(authorizationManager, (String)"authorizationManager cannot be null");
        this.authorizationManager = authorizationManager;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthorizationDecision decision = this.authorizationManager.check(this::getAuthentication, (Object)request);
        this.eventPublisher.publishAuthorizationEvent(this::getAuthentication, (Object)request, decision);
        if (decision != null && !decision.isGranted()) {
            throw new AccessDeniedException("Access Denied");
        }
        filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("An Authentication object was not found in the SecurityContext");
        }
        return authentication;
    }

    protected void doFilterNestedErrorDispatch(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.doFilterInternal(request, response, filterChain);
    }

    protected boolean shouldNotFilterAsyncDispatch() {
        return !this.shouldFilterAllDispatcherTypes;
    }

    protected boolean shouldNotFilterErrorDispatch() {
        return !this.shouldFilterAllDispatcherTypes;
    }

    public void setAuthorizationEventPublisher(AuthorizationEventPublisher eventPublisher) {
        Assert.notNull((Object)eventPublisher, (String)"eventPublisher cannot be null");
        this.eventPublisher = eventPublisher;
    }

    public AuthorizationManager<HttpServletRequest> getAuthorizationManager() {
        return this.authorizationManager;
    }

    public void setShouldFilterAllDispatcherTypes(boolean shouldFilterAllDispatcherTypes) {
        this.shouldFilterAllDispatcherTypes = shouldFilterAllDispatcherTypes;
    }

    private static <T> void noPublish(Supplier<Authentication> authentication, T object, AuthorizationDecision decision) {
    }
}

