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
 *  javax.servlet.http.HttpSession
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.authentication.preauth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public abstract class AbstractPreAuthenticatedProcessingFilter
extends GenericFilterBean
implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher = null;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationManager authenticationManager = null;
    private boolean continueFilterChainOnUnsuccessfulAuthentication = true;
    private boolean checkForPrincipalChanges;
    private boolean invalidateSessionOnPrincipalChange = true;
    private AuthenticationSuccessHandler authenticationSuccessHandler = null;
    private AuthenticationFailureHandler authenticationFailureHandler = null;
    private RequestMatcher requiresAuthenticationRequestMatcher = new PreAuthenticatedProcessingRequestMatcher();
    private SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();

    public void afterPropertiesSet() {
        try {
            super.afterPropertiesSet();
        }
        catch (ServletException ex) {
            throw new RuntimeException(ex);
        }
        Assert.notNull((Object)this.authenticationManager, (String)"An AuthenticationManager must be set");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this.requiresAuthenticationRequestMatcher.matches((HttpServletRequest)request)) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)LogMessage.of(() -> "Authenticating " + SecurityContextHolder.getContext().getAuthentication()));
            }
            this.doAuthenticate((HttpServletRequest)request, (HttpServletResponse)response);
        } else if (this.logger.isTraceEnabled()) {
            this.logger.trace((Object)LogMessage.format((String)"Did not authenticate since request did not match [%s]", (Object)this.requiresAuthenticationRequestMatcher));
        }
        chain.doFilter(request, response);
    }

    protected boolean principalChanged(HttpServletRequest request, Authentication currentAuthentication) {
        Object principal = this.getPreAuthenticatedPrincipal(request);
        if (principal instanceof String && currentAuthentication.getName().equals(principal)) {
            return false;
        }
        if (principal != null && principal.equals(currentAuthentication.getPrincipal())) {
            return false;
        }
        this.logger.debug((Object)LogMessage.format((String)"Pre-authenticated principal has changed to %s and will be reauthenticated", (Object)principal));
        return true;
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        block3: {
            Object principal = this.getPreAuthenticatedPrincipal(request);
            if (principal == null) {
                this.logger.debug((Object)"No pre-authenticated principal found in request");
                return;
            }
            this.logger.debug((Object)LogMessage.format((String)"preAuthenticatedPrincipal = %s, trying to authenticate", (Object)principal));
            Object credentials = this.getPreAuthenticatedCredentials(request);
            try {
                PreAuthenticatedAuthenticationToken authenticationRequest = new PreAuthenticatedAuthenticationToken(principal, credentials);
                authenticationRequest.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
                Authentication authenticationResult = this.authenticationManager.authenticate((Authentication)authenticationRequest);
                this.successfulAuthentication(request, response, authenticationResult);
            }
            catch (AuthenticationException ex) {
                this.unsuccessfulAuthentication(request, response, ex);
                if (this.continueFilterChainOnUnsuccessfulAuthentication) break block3;
                throw ex;
            }
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        this.logger.debug((Object)LogMessage.format((String)"Authentication success: %s", (Object)authResult));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext((SecurityContext)context);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent((ApplicationEvent)new InteractiveAuthenticationSuccessEvent(authResult, ((Object)((Object)this)).getClass()));
        }
        if (this.authenticationSuccessHandler != null) {
            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
        }
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.logger.debug((Object)"Cleared security context due to exception", (Throwable)failed);
        request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", (Object)failed);
        if (this.authenticationFailureHandler != null) {
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
        }
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher anApplicationEventPublisher) {
        this.eventPublisher = anApplicationEventPublisher;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, (String)"AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    protected AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return this.authenticationDetailsSource;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setContinueFilterChainOnUnsuccessfulAuthentication(boolean shouldContinue) {
        this.continueFilterChainOnUnsuccessfulAuthentication = shouldContinue;
    }

    public void setCheckForPrincipalChanges(boolean checkForPrincipalChanges) {
        this.checkForPrincipalChanges = checkForPrincipalChanges;
    }

    public void setInvalidateSessionOnPrincipalChange(boolean invalidateSessionOnPrincipalChange) {
        this.invalidateSessionOnPrincipalChange = invalidateSessionOnPrincipalChange;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setRequiresAuthenticationRequestMatcher(RequestMatcher requiresAuthenticationRequestMatcher) {
        Assert.notNull((Object)requiresAuthenticationRequestMatcher, (String)"requestMatcher cannot be null");
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
    }

    protected abstract Object getPreAuthenticatedPrincipal(HttpServletRequest var1);

    protected abstract Object getPreAuthenticatedCredentials(HttpServletRequest var1);

    private class PreAuthenticatedProcessingRequestMatcher
    implements RequestMatcher {
        private PreAuthenticatedProcessingRequestMatcher() {
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
            if (currentUser == null) {
                return true;
            }
            if (!AbstractPreAuthenticatedProcessingFilter.this.checkForPrincipalChanges) {
                return false;
            }
            if (!AbstractPreAuthenticatedProcessingFilter.this.principalChanged(request, currentUser)) {
                return false;
            }
            AbstractPreAuthenticatedProcessingFilter.this.logger.debug((Object)"Pre-authenticated principal has changed and will be reauthenticated");
            if (AbstractPreAuthenticatedProcessingFilter.this.invalidateSessionOnPrincipalChange) {
                SecurityContextHolder.clearContext();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    AbstractPreAuthenticatedProcessingFilter.this.logger.debug((Object)"Invalidating existing session");
                    session.invalidate();
                    request.getSession();
                }
            }
            return true;
        }
    }
}

