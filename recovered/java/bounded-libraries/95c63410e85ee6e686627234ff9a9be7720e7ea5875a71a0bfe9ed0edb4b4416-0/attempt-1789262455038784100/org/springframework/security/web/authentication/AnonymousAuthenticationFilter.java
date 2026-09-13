/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AnonymousAuthenticationToken
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.authority.AuthorityUtils
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.authentication;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class AnonymousAuthenticationFilter
extends GenericFilterBean
implements InitializingBean {
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private String key;
    private Object principal;
    private List<GrantedAuthority> authorities;

    public AnonymousAuthenticationFilter(String key) {
        this(key, "anonymousUser", AuthorityUtils.createAuthorityList((String[])new String[]{"ROLE_ANONYMOUS"}));
    }

    public AnonymousAuthenticationFilter(String key, Object principal, List<GrantedAuthority> authorities) {
        Assert.hasLength((String)key, (String)"key cannot be null or empty");
        Assert.notNull((Object)principal, (String)"Anonymous authentication principal must be set");
        Assert.notNull(authorities, (String)"Anonymous authorities must be set");
        this.key = key;
        this.principal = principal;
        this.authorities = authorities;
    }

    public void afterPropertiesSet() {
        Assert.hasLength((String)this.key, (String)"key must have length");
        Assert.notNull((Object)this.principal, (String)"Anonymous authentication principal must be set");
        Assert.notNull(this.authorities, (String)"Anonymous authorities must be set");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication authentication = this.createAuthentication((HttpServletRequest)req);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext((SecurityContext)context);
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.of(() -> "Set SecurityContextHolder to " + SecurityContextHolder.getContext().getAuthentication()));
            } else {
                this.logger.debug((Object)"Set SecurityContextHolder to anonymous SecurityContext");
            }
        } else if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)LogMessage.of(() -> "Did not set SecurityContextHolder since already authenticated " + SecurityContextHolder.getContext().getAuthentication()));
        }
        chain.doFilter(req, res);
    }

    protected Authentication createAuthentication(HttpServletRequest request) {
        AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(this.key, this.principal, this.authorities);
        token.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
        return token;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, (String)"AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public List<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}

