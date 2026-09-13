/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.security.authentication.AnonymousAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.web.access.WebInvocationPrivilegeEvaluator
 *  org.springframework.web.util.UrlPathHelper
 */
package org.springframework.boot.web.servlet.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.web.util.UrlPathHelper;

public class ErrorPageSecurityFilter
implements Filter {
    private static final WebInvocationPrivilegeEvaluator ALWAYS = new AlwaysAllowWebInvocationPrivilegeEvaluator();
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();
    private final ApplicationContext context;
    private volatile WebInvocationPrivilegeEvaluator privilegeEvaluator;

    public ErrorPageSecurityFilter(ApplicationContext context) {
        this.context = context;
        this.urlPathHelper.setAlwaysUseFullPath(true);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Integer errorCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (DispatcherType.ERROR.equals((Object)request.getDispatcherType()) && !this.isAllowed(request, errorCode)) {
            response.sendError(errorCode != null ? errorCode : 401);
            return;
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    private boolean isAllowed(HttpServletRequest request, Integer errorCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (this.isUnauthenticated(authentication) && this.isNotAuthenticationError(errorCode)) {
            return true;
        }
        return this.getPrivilegeEvaluator().isAllowed(this.urlPathHelper.getPathWithinApplication(request), authentication);
    }

    private boolean isUnauthenticated(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private boolean isNotAuthenticationError(Integer errorCode) {
        return errorCode == null || errorCode != 401 && errorCode != 403;
    }

    private WebInvocationPrivilegeEvaluator getPrivilegeEvaluator() {
        WebInvocationPrivilegeEvaluator privilegeEvaluator = this.privilegeEvaluator;
        if (privilegeEvaluator == null) {
            this.privilegeEvaluator = privilegeEvaluator = this.getPrivilegeEvaluatorBean();
        }
        return privilegeEvaluator;
    }

    private WebInvocationPrivilegeEvaluator getPrivilegeEvaluatorBean() {
        try {
            return (WebInvocationPrivilegeEvaluator)this.context.getBean(WebInvocationPrivilegeEvaluator.class);
        }
        catch (NoSuchBeanDefinitionException ex) {
            return ALWAYS;
        }
    }

    public void destroy() {
    }

    private static class AlwaysAllowWebInvocationPrivilegeEvaluator
    implements WebInvocationPrivilegeEvaluator {
        private AlwaysAllowWebInvocationPrivilegeEvaluator() {
        }

        public boolean isAllowed(String uri, Authentication authentication) {
            return true;
        }

        public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication) {
            return true;
        }
    }
}

