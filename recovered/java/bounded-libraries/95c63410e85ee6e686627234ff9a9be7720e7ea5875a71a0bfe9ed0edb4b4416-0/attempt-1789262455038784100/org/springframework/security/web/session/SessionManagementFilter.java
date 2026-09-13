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
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationTrustResolver
 *  org.springframework.security.authentication.AuthenticationTrustResolverImpl
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.session;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class SessionManagementFilter
extends GenericFilterBean {
    static final String FILTER_APPLIED = "__spring_security_session_mgmt_filter_applied";
    private final SecurityContextRepository securityContextRepository;
    private SessionAuthenticationStrategy sessionAuthenticationStrategy;
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private InvalidSessionStrategy invalidSessionStrategy = null;
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    public SessionManagementFilter(SecurityContextRepository securityContextRepository) {
        this(securityContextRepository, new SessionFixationProtectionStrategy());
    }

    public SessionManagementFilter(SecurityContextRepository securityContextRepository, SessionAuthenticationStrategy sessionStrategy) {
        Assert.notNull((Object)securityContextRepository, (String)"SecurityContextRepository cannot be null");
        Assert.notNull((Object)sessionStrategy, (String)"SessionAuthenticationStrategy cannot be null");
        this.securityContextRepository = securityContextRepository;
        this.sessionAuthenticationStrategy = sessionStrategy;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        request.setAttribute(FILTER_APPLIED, (Object)Boolean.TRUE);
        if (!this.securityContextRepository.containsContext(request)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !this.trustResolver.isAnonymous(authentication)) {
                try {
                    this.sessionAuthenticationStrategy.onAuthentication(authentication, request, response);
                }
                catch (SessionAuthenticationException ex) {
                    this.logger.debug((Object)"SessionAuthenticationStrategy rejected the authentication object", (Throwable)((Object)ex));
                    SecurityContextHolder.clearContext();
                    this.failureHandler.onAuthenticationFailure(request, response, ex);
                    return;
                }
                this.securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
            } else if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)LogMessage.format((String)"Request requested invalid session id %s", (Object)request.getRequestedSessionId()));
                }
                if (this.invalidSessionStrategy != null) {
                    this.invalidSessionStrategy.onInvalidSessionDetected(request, response);
                    return;
                }
            }
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    public void setInvalidSessionStrategy(InvalidSessionStrategy invalidSessionStrategy) {
        this.invalidSessionStrategy = invalidSessionStrategy;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull((Object)failureHandler, (String)"failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull((Object)trustResolver, (String)"trustResolver cannot be null");
        this.trustResolver = trustResolver;
    }
}

