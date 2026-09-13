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
 *  org.jasig.cas.client.proxy.ProxyGrantingTicketStorage
 *  org.jasig.cas.client.util.CommonUtils
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AnonymousAuthenticationToken
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
 *  org.springframework.security.web.authentication.AuthenticationFailureHandler
 *  org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
 *  org.springframework.security.web.context.NullSecurityContextRepository
 *  org.springframework.security.web.context.SecurityContextRepository
 *  org.springframework.security.web.util.matcher.AntPathRequestMatcher
 *  org.springframework.security.web.util.matcher.RequestMatcher
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public class CasAuthenticationFilter
extends AbstractAuthenticationProcessingFilter {
    public static final String CAS_STATEFUL_IDENTIFIER = "_cas_stateful_";
    public static final String CAS_STATELESS_IDENTIFIER = "_cas_stateless_";
    private RequestMatcher proxyReceptorMatcher;
    private ProxyGrantingTicketStorage proxyGrantingTicketStorage;
    private String artifactParameter = "ticket";
    private boolean authenticateAllArtifacts;
    private AuthenticationFailureHandler proxyFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    private SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();

    public CasAuthenticationFilter() {
        super("/login/cas");
        this.setAuthenticationFailureHandler((AuthenticationFailureHandler)new SimpleUrlAuthenticationFailureHandler());
    }

    protected final void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        boolean continueFilterChain = this.proxyTicketRequest(this.serviceTicketRequest(request, response), request);
        if (!continueFilterChain) {
            super.successfulAuthentication(request, response, chain, authResult);
            return;
        }
        this.logger.debug((Object)LogMessage.format((String)"Authentication success. Updating SecurityContextHolder to contain: %s", (Object)authResult));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext((SecurityContext)context);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent((ApplicationEvent)new InteractiveAuthenticationSuccessEvent(authResult, ((Object)((Object)this)).getClass()));
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (this.proxyReceptorRequest(request)) {
            this.logger.debug((Object)"Responding to proxy receptor request");
            CommonUtils.readAndRespondToProxyReceptorRequest((HttpServletRequest)request, (HttpServletResponse)response, (ProxyGrantingTicketStorage)this.proxyGrantingTicketStorage);
            return null;
        }
        boolean serviceTicketRequest = this.serviceTicketRequest(request, response);
        String username = serviceTicketRequest ? CAS_STATEFUL_IDENTIFIER : CAS_STATELESS_IDENTIFIER;
        String password = this.obtainArtifact(request);
        if (password == null) {
            this.logger.debug((Object)"Failed to obtain an artifact (cas ticket)");
            password = "";
        }
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated((Object)username, (Object)password);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
        return this.getAuthenticationManager().authenticate((Authentication)authRequest);
    }

    protected String obtainArtifact(HttpServletRequest request) {
        return request.getParameter(this.artifactParameter);
    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        boolean result;
        boolean serviceTicketRequest = this.serviceTicketRequest(request, response);
        boolean bl = result = serviceTicketRequest || this.proxyReceptorRequest(request) || this.proxyTicketRequest(serviceTicketRequest, request);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("requiresAuthentication = " + result));
        }
        return result;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    public final void setProxyAuthenticationFailureHandler(AuthenticationFailureHandler proxyFailureHandler) {
        Assert.notNull((Object)proxyFailureHandler, (String)"proxyFailureHandler cannot be null");
        this.proxyFailureHandler = proxyFailureHandler;
    }

    public final void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler((AuthenticationFailureHandler)new CasAuthenticationFailureHandler(failureHandler));
    }

    public final void setProxyReceptorUrl(String proxyReceptorUrl) {
        this.proxyReceptorMatcher = new AntPathRequestMatcher("/**" + proxyReceptorUrl);
    }

    public final void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
        this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
    }

    public final void setServiceProperties(ServiceProperties serviceProperties) {
        this.artifactParameter = serviceProperties.getArtifactParameter();
        this.authenticateAllArtifacts = serviceProperties.isAuthenticateAllArtifacts();
    }

    private boolean serviceTicketRequest(HttpServletRequest request, HttpServletResponse response) {
        boolean result = super.requiresAuthentication(request, response);
        this.logger.debug((Object)LogMessage.format((String)"serviceTicketRequest = %s", (Object)result));
        return result;
    }

    private boolean proxyTicketRequest(boolean serviceTicketRequest, HttpServletRequest request) {
        if (serviceTicketRequest) {
            return false;
        }
        boolean result = this.authenticateAllArtifacts && this.obtainArtifact(request) != null && !this.authenticated();
        this.logger.debug((Object)LogMessage.format((String)"proxyTicketRequest = %s", (Object)result));
        return result;
    }

    private boolean authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private boolean proxyReceptorRequest(HttpServletRequest request) {
        boolean result = this.proxyReceptorConfigured() && this.proxyReceptorMatcher.matches(request);
        this.logger.debug((Object)LogMessage.format((String)"proxyReceptorRequest = %s", (Object)result));
        return result;
    }

    private boolean proxyReceptorConfigured() {
        boolean result = this.proxyGrantingTicketStorage != null && this.proxyReceptorMatcher != null;
        this.logger.debug((Object)LogMessage.format((String)"proxyReceptorConfigured = %s", (Object)result));
        return result;
    }

    private class CasAuthenticationFailureHandler
    implements AuthenticationFailureHandler {
        private final AuthenticationFailureHandler serviceTicketFailureHandler;

        CasAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
            Assert.notNull((Object)failureHandler, (String)"failureHandler");
            this.serviceTicketFailureHandler = failureHandler;
        }

        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            if (CasAuthenticationFilter.this.serviceTicketRequest(request, response)) {
                this.serviceTicketFailureHandler.onAuthenticationFailure(request, response, exception);
            } else {
                CasAuthenticationFilter.this.proxyFailureHandler.onAuthenticationFailure(request, response, exception);
            }
        }
    }
}

