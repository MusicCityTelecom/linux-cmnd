/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.authorization.AuthorizationDecision
 *  org.springframework.security.authorization.AuthorizationManager
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.web.context.ServletContextAware
 */
package org.springframework.security.web.access;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

public final class AuthorizationManagerWebInvocationPrivilegeEvaluator
implements WebInvocationPrivilegeEvaluator,
ServletContextAware {
    private final AuthorizationManager<HttpServletRequest> authorizationManager;
    private ServletContext servletContext;

    public AuthorizationManagerWebInvocationPrivilegeEvaluator(AuthorizationManager<HttpServletRequest> authorizationManager) {
        Assert.notNull(authorizationManager, (String)"authorizationManager cannot be null");
        this.authorizationManager = authorizationManager;
    }

    @Override
    public boolean isAllowed(String uri, Authentication authentication) {
        return this.isAllowed(null, uri, null, authentication);
    }

    @Override
    public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication) {
        FilterInvocation filterInvocation = new FilterInvocation(contextPath, uri, method, this.servletContext);
        AuthorizationDecision decision = this.authorizationManager.check(() -> authentication, (Object)filterInvocation.getHttpRequest());
        return decision == null || decision.isGranted();
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

