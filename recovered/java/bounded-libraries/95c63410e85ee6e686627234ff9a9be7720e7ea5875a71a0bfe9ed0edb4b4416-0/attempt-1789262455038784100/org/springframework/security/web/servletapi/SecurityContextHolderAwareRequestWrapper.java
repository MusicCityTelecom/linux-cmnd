/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  org.springframework.security.authentication.AbstractAuthenticationToken
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.servletapi;

import java.security.Principal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class SecurityContextHolderAwareRequestWrapper
extends HttpServletRequestWrapper {
    private final AuthenticationTrustResolver trustResolver;
    private final String rolePrefix;

    public SecurityContextHolderAwareRequestWrapper(HttpServletRequest request, String rolePrefix) {
        this(request, (AuthenticationTrustResolver)new AuthenticationTrustResolverImpl(), rolePrefix);
    }

    public SecurityContextHolderAwareRequestWrapper(HttpServletRequest request, AuthenticationTrustResolver trustResolver, String rolePrefix) {
        super(request);
        Assert.notNull((Object)trustResolver, (String)"trustResolver cannot be null");
        this.rolePrefix = rolePrefix;
        this.trustResolver = trustResolver;
    }

    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !this.trustResolver.isAnonymous(auth) ? auth : null;
    }

    public String getRemoteUser() {
        Authentication auth = this.getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }
        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails)auth.getPrincipal()).getUsername();
        }
        if (auth instanceof AbstractAuthenticationToken) {
            return auth.getName();
        }
        return auth.getPrincipal().toString();
    }

    public Principal getUserPrincipal() {
        Authentication auth = this.getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }
        return auth;
    }

    private boolean isGranted(String role) {
        Authentication auth = this.getAuthentication();
        if (this.rolePrefix != null && role != null && !role.startsWith(this.rolePrefix)) {
            role = this.rolePrefix + role;
        }
        if (auth == null || auth.getPrincipal() == null) {
            return false;
        }
        Collection authorities = auth.getAuthorities();
        if (authorities == null) {
            return false;
        }
        for (GrantedAuthority grantedAuthority : authorities) {
            if (!role.equals(grantedAuthority.getAuthority())) continue;
            return true;
        }
        return false;
    }

    public boolean isUserInRole(String role) {
        return this.isGranted(role);
    }

    public String toString() {
        return "SecurityContextHolderAwareRequestWrapper[ " + this.getRequest() + "]";
    }
}

