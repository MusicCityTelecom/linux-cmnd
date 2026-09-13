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
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.servletapi;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servletapi.HttpServlet3RequestFactory;
import org.springframework.security.web.servletapi.HttpServletRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class SecurityContextHolderAwareRequestFilter
extends GenericFilterBean {
    private String rolePrefix = "ROLE_";
    private HttpServletRequestFactory requestFactory;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private List<LogoutHandler> logoutHandlers;
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    public void setRolePrefix(String rolePrefix) {
        Assert.notNull((Object)rolePrefix, (String)"Role prefix must not be null");
        this.rolePrefix = rolePrefix;
        this.updateFactory();
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setLogoutHandlers(List<LogoutHandler> logoutHandlers) {
        this.logoutHandlers = logoutHandlers;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter((ServletRequest)this.requestFactory.create((HttpServletRequest)req, (HttpServletResponse)res), res);
    }

    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        this.updateFactory();
    }

    private void updateFactory() {
        String rolePrefix = this.rolePrefix;
        this.requestFactory = this.createServlet3Factory(rolePrefix);
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull((Object)trustResolver, (String)"trustResolver cannot be null");
        this.trustResolver = trustResolver;
        this.updateFactory();
    }

    private HttpServletRequestFactory createServlet3Factory(String rolePrefix) {
        HttpServlet3RequestFactory factory = new HttpServlet3RequestFactory(rolePrefix);
        factory.setTrustResolver(this.trustResolver);
        factory.setAuthenticationEntryPoint(this.authenticationEntryPoint);
        factory.setAuthenticationManager(this.authenticationManager);
        factory.setLogoutHandlers(this.logoutHandlers);
        return factory;
    }
}

