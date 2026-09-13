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
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 */
package org.springframework.security.web.authentication.rememberme;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class RememberMeAuthenticationFilter
extends GenericFilterBean
implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationManager authenticationManager;
    private RememberMeServices rememberMeServices;
    private SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();

    public RememberMeAuthenticationFilter(AuthenticationManager authenticationManager, RememberMeServices rememberMeServices) {
        Assert.notNull((Object)authenticationManager, (String)"authenticationManager cannot be null");
        Assert.notNull((Object)rememberMeServices, (String)"rememberMeServices cannot be null");
        this.authenticationManager = authenticationManager;
        this.rememberMeServices = rememberMeServices;
    }

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.authenticationManager, (String)"authenticationManager must be specified");
        Assert.notNull((Object)this.rememberMeServices, (String)"rememberMeServices must be specified");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            this.logger.debug((Object)LogMessage.of(() -> "SecurityContextHolder not populated with remember-me token, as it already contained: '" + SecurityContextHolder.getContext().getAuthentication() + "'"));
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        Authentication rememberMeAuth = this.rememberMeServices.autoLogin(request, response);
        if (rememberMeAuth != null) {
            try {
                rememberMeAuth = this.authenticationManager.authenticate(rememberMeAuth);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(rememberMeAuth);
                SecurityContextHolder.setContext((SecurityContext)context);
                this.onSuccessfulAuthentication(request, response, rememberMeAuth);
                this.logger.debug((Object)LogMessage.of(() -> "SecurityContextHolder populated with remember-me token: '" + SecurityContextHolder.getContext().getAuthentication() + "'"));
                this.securityContextRepository.saveContext(context, request, response);
                if (this.eventPublisher != null) {
                    this.eventPublisher.publishEvent((ApplicationEvent)new InteractiveAuthenticationSuccessEvent(SecurityContextHolder.getContext().getAuthentication(), ((Object)((Object)this)).getClass()));
                }
                if (this.successHandler != null) {
                    this.successHandler.onAuthenticationSuccess(request, response, rememberMeAuth);
                    return;
                }
            }
            catch (AuthenticationException ex) {
                this.logger.debug((Object)LogMessage.format((String)"SecurityContextHolder not populated with remember-me token, as AuthenticationManager rejected Authentication returned by RememberMeServices: '%s'; invalidating remember-me token", (Object)rememberMeAuth), (Throwable)ex);
                this.rememberMeServices.loginFail(request, response);
                this.onUnsuccessfulAuthentication(request, response, ex);
            }
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    }

    public RememberMeServices getRememberMeServices() {
        return this.rememberMeServices;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull((Object)successHandler, (String)"successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }
}

