/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.jaas.JaasAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.jaasapi;

import java.io.IOException;
import java.security.PrivilegedActionException;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JaasApiIntegrationFilter
extends GenericFilterBean {
    private boolean createEmptySubject;

    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Subject subject = this.obtainSubject(request);
        if (subject == null && this.createEmptySubject) {
            this.logger.debug((Object)"Subject returned was null and createEmptySubject is true; creating new empty subject to run as.");
            subject = new Subject();
        }
        if (subject == null) {
            this.logger.debug((Object)"Subject is null continue running with no Subject.");
            chain.doFilter(request, response);
            return;
        }
        this.logger.debug((Object)LogMessage.format((String)"Running as Subject %s", (Object)subject));
        try {
            Subject.doAs(subject, () -> {
                chain.doFilter(request, response);
                return null;
            });
        }
        catch (PrivilegedActionException ex) {
            throw new ServletException(ex.getMessage(), (Throwable)ex);
        }
    }

    protected Subject obtainSubject(ServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.logger.debug((Object)LogMessage.format((String)"Attempting to obtainSubject using authentication : %s", (Object)authentication));
        if (authentication == null) {
            return null;
        }
        if (!authentication.isAuthenticated()) {
            return null;
        }
        if (!(authentication instanceof JaasAuthenticationToken)) {
            return null;
        }
        JaasAuthenticationToken token = (JaasAuthenticationToken)authentication;
        LoginContext loginContext = token.getLoginContext();
        if (loginContext == null) {
            return null;
        }
        return loginContext.getSubject();
    }

    public final void setCreateEmptySubject(boolean createEmptySubject) {
        this.createEmptySubject = createEmptySubject;
    }
}

