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
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.AuthenticationManagerResolver
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package org.springframework.security.web.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter
extends OncePerRequestFilter {
    private RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;
    private AuthenticationConverter authenticationConverter;
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new AuthenticationEntryPointFailureHandler(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    private SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();
    private AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        this((AuthenticationManagerResolver<HttpServletRequest>)((AuthenticationManagerResolver)r -> authenticationManager), authenticationConverter);
    }

    public AuthenticationFilter(AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver, AuthenticationConverter authenticationConverter) {
        Assert.notNull(authenticationManagerResolver, (String)"authenticationManagerResolver cannot be null");
        Assert.notNull((Object)authenticationConverter, (String)"authenticationConverter cannot be null");
        this.authenticationManagerResolver = authenticationManagerResolver;
        this.authenticationConverter = authenticationConverter;
    }

    public RequestMatcher getRequestMatcher() {
        return this.requestMatcher;
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull((Object)requestMatcher, (String)"requestMatcher cannot be null");
        this.requestMatcher = requestMatcher;
    }

    public AuthenticationConverter getAuthenticationConverter() {
        return this.authenticationConverter;
    }

    public void setAuthenticationConverter(AuthenticationConverter authenticationConverter) {
        Assert.notNull((Object)authenticationConverter, (String)"authenticationConverter cannot be null");
        this.authenticationConverter = authenticationConverter;
    }

    public AuthenticationSuccessHandler getSuccessHandler() {
        return this.successHandler;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull((Object)successHandler, (String)"successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public AuthenticationFailureHandler getFailureHandler() {
        return this.failureHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull((Object)failureHandler, (String)"failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    public AuthenticationManagerResolver<HttpServletRequest> getAuthenticationManagerResolver() {
        return this.authenticationManagerResolver;
    }

    public void setAuthenticationManagerResolver(AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver) {
        Assert.notNull(authenticationManagerResolver, (String)"authenticationManagerResolver cannot be null");
        this.authenticationManagerResolver = authenticationManagerResolver;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)("Did not match request to " + this.requestMatcher));
            }
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        try {
            Authentication authenticationResult = this.attemptAuthentication(request, response);
            if (authenticationResult == null) {
                filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
                return;
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                request.changeSessionId();
            }
            this.successfulAuthentication(request, response, filterChain, authenticationResult);
        }
        catch (AuthenticationException ex) {
            this.unsuccessfulAuthentication(request, response, ex);
        }
    }

    private void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

    private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext((SecurityContext)context);
        this.securityContextRepository.saveContext(context, request, response);
        this.successHandler.onAuthenticationSuccess(request, response, chain, authentication);
    }

    private Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, ServletException {
        Authentication authentication = this.authenticationConverter.convert(request);
        if (authentication == null) {
            return null;
        }
        AuthenticationManager authenticationManager = this.authenticationManagerResolver.resolve((Object)request);
        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        if (authenticationResult == null) {
            throw new ServletException("AuthenticationManager should not return null Authentication object.");
        }
        return authenticationResult;
    }
}

