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
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.support.MessageSourceAccessor
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.authentication.AccountStatusUserDetailsChecker
 *  org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
 *  org.springframework.security.authentication.AuthenticationDetailsSource
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.SpringSecurityMessageSource
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsChecker
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.util.Assert
 *  org.springframework.web.filter.GenericFilterBean
 *  org.springframework.web.util.UrlPathHelper
 */
package org.springframework.security.web.authentication.switchuser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.security.web.authentication.switchuser.SwitchUserAuthorityChanger;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

public class SwitchUserFilter
extends GenericFilterBean
implements ApplicationEventPublisherAware,
MessageSourceAware {
    public static final String SPRING_SECURITY_SWITCH_USERNAME_KEY = "username";
    public static final String ROLE_PREVIOUS_ADMINISTRATOR = "ROLE_PREVIOUS_ADMINISTRATOR";
    private ApplicationEventPublisher eventPublisher;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private RequestMatcher exitUserMatcher = SwitchUserFilter.createMatcher("/logout/impersonate");
    private RequestMatcher switchUserMatcher = SwitchUserFilter.createMatcher("/login/impersonate");
    private String targetUrl;
    private String switchFailureUrl;
    private String usernameParameter = "username";
    private String switchAuthorityRole = "ROLE_PREVIOUS_ADMINISTRATOR";
    private SwitchUserAuthorityChanger switchUserAuthorityChanger;
    private UserDetailsService userDetailsService;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public void afterPropertiesSet() {
        Assert.notNull((Object)this.userDetailsService, (String)"userDetailsService must be specified");
        Assert.isTrue((this.successHandler != null || this.targetUrl != null ? 1 : 0) != 0, (String)"You must set either a successHandler or the targetUrl");
        if (this.targetUrl != null) {
            Assert.isNull((Object)this.successHandler, (String)"You cannot set both successHandler and targetUrl");
            this.successHandler = new SimpleUrlAuthenticationSuccessHandler(this.targetUrl);
        }
        if (this.failureHandler == null) {
            this.failureHandler = this.switchFailureUrl != null ? new SimpleUrlAuthenticationFailureHandler(this.switchFailureUrl) : new SimpleUrlAuthenticationFailureHandler();
        } else {
            Assert.isNull((Object)this.switchFailureUrl, (String)"You cannot set both a switchFailureUrl and a failureHandler");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this.requiresSwitchUser(request)) {
            try {
                Authentication targetUser = this.attemptSwitchUser(request);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(targetUser);
                SecurityContextHolder.setContext((SecurityContext)context);
                this.logger.debug((Object)LogMessage.format((String)"Set SecurityContextHolder to %s", (Object)targetUser));
                this.successHandler.onAuthenticationSuccess(request, response, targetUser);
            }
            catch (AuthenticationException ex) {
                this.logger.debug((Object)"Failed to switch user", (Throwable)ex);
                this.failureHandler.onAuthenticationFailure(request, response, ex);
            }
            return;
        }
        if (this.requiresExitUser(request)) {
            Authentication originalUser = this.attemptExitUser(request);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(originalUser);
            SecurityContextHolder.setContext((SecurityContext)context);
            this.logger.debug((Object)LogMessage.format((String)"Set SecurityContextHolder to %s", (Object)originalUser));
            this.successHandler.onAuthenticationSuccess(request, response, originalUser);
            return;
        }
        this.logger.trace((Object)LogMessage.format((String)"Did not attempt to switch user since request did not match [%s] or [%s]", (Object)this.switchUserMatcher, (Object)this.exitUserMatcher));
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }

    protected Authentication attemptSwitchUser(HttpServletRequest request) throws AuthenticationException {
        String username = request.getParameter(this.usernameParameter);
        username = username != null ? username : "";
        this.logger.debug((Object)LogMessage.format((String)"Attempting to switch to user [%s]", (Object)username));
        UserDetails targetUser = this.userDetailsService.loadUserByUsername(username);
        this.userDetailsChecker.check(targetUser);
        UsernamePasswordAuthenticationToken targetUserRequest = this.createSwitchUserToken(request, targetUser);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent((ApplicationEvent)new AuthenticationSwitchUserEvent(SecurityContextHolder.getContext().getAuthentication(), targetUser));
        }
        return targetUserRequest;
    }

    protected Authentication attemptExitUser(HttpServletRequest request) throws AuthenticationCredentialsNotFoundException {
        Authentication current = SecurityContextHolder.getContext().getAuthentication();
        if (current == null) {
            throw new AuthenticationCredentialsNotFoundException(this.messages.getMessage("SwitchUserFilter.noCurrentUser", "No current user associated with this request"));
        }
        Authentication original = this.getSourceAuthentication(current);
        if (original == null) {
            this.logger.debug((Object)"Failed to find original user");
            throw new AuthenticationCredentialsNotFoundException(this.messages.getMessage("SwitchUserFilter.noOriginalAuthentication", "Failed to find original user"));
        }
        UserDetails originalUser = null;
        Object obj = original.getPrincipal();
        if (obj != null && obj instanceof UserDetails) {
            originalUser = (UserDetails)obj;
        }
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent((ApplicationEvent)new AuthenticationSwitchUserEvent(current, originalUser));
        }
        return original;
    }

    private UsernamePasswordAuthenticationToken createSwitchUserToken(HttpServletRequest request, UserDetails targetUser) {
        Authentication currentAuthentication = this.getCurrentAuthentication(request);
        SwitchUserGrantedAuthority switchAuthority = new SwitchUserGrantedAuthority(this.switchAuthorityRole, currentAuthentication);
        Collection<? extends GrantedAuthority> orig = targetUser.getAuthorities();
        if (this.switchUserAuthorityChanger != null) {
            orig = this.switchUserAuthorityChanger.modifyGrantedAuthorities(targetUser, currentAuthentication, orig);
        }
        ArrayList<SwitchUserGrantedAuthority> newAuths = new ArrayList<SwitchUserGrantedAuthority>(orig);
        newAuths.add(switchAuthority);
        UsernamePasswordAuthenticationToken targetUserRequest = UsernamePasswordAuthenticationToken.authenticated((Object)targetUser, (Object)targetUser.getPassword(), newAuths);
        targetUserRequest.setDetails(this.authenticationDetailsSource.buildDetails((Object)request));
        return targetUserRequest;
    }

    private Authentication getCurrentAuthentication(HttpServletRequest request) {
        try {
            return this.attemptExitUser(request);
        }
        catch (AuthenticationCredentialsNotFoundException ex) {
            return SecurityContextHolder.getContext().getAuthentication();
        }
    }

    private Authentication getSourceAuthentication(Authentication current) {
        Authentication original = null;
        Collection authorities = current.getAuthorities();
        for (GrantedAuthority auth : authorities) {
            if (!(auth instanceof SwitchUserGrantedAuthority)) continue;
            original = ((SwitchUserGrantedAuthority)auth).getSource();
            this.logger.debug((Object)LogMessage.format((String)"Found original switch user granted authority [%s]", (Object)original));
        }
        return original;
    }

    protected boolean requiresExitUser(HttpServletRequest request) {
        return this.exitUserMatcher.matches(request);
    }

    protected boolean requiresSwitchUser(HttpServletRequest request) {
        return this.switchUserMatcher.matches(request);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) throws BeansException {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, (String)"AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull((Object)messageSource, (String)"messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setExitUserUrl(String exitUserUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(exitUserUrl), (String)"exitUserUrl cannot be empty and must be a valid redirect URL");
        this.exitUserMatcher = SwitchUserFilter.createMatcher(exitUserUrl);
    }

    public void setExitUserMatcher(RequestMatcher exitUserMatcher) {
        Assert.notNull((Object)exitUserMatcher, (String)"exitUserMatcher cannot be null");
        this.exitUserMatcher = exitUserMatcher;
    }

    public void setSwitchUserUrl(String switchUserUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(switchUserUrl), (String)"switchUserUrl cannot be empty and must be a valid redirect URL");
        this.switchUserMatcher = SwitchUserFilter.createMatcher(switchUserUrl);
    }

    public void setSwitchUserMatcher(RequestMatcher switchUserMatcher) {
        Assert.notNull((Object)switchUserMatcher, (String)"switchUserMatcher cannot be null");
        this.switchUserMatcher = switchUserMatcher;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull((Object)successHandler, (String)"successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setSwitchFailureUrl(String switchFailureUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(switchFailureUrl), (String)"switchFailureUrl must be a valid redirect URL");
        this.switchFailureUrl = switchFailureUrl;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull((Object)failureHandler, (String)"failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    public void setSwitchUserAuthorityChanger(SwitchUserAuthorityChanger switchUserAuthorityChanger) {
        this.switchUserAuthorityChanger = switchUserAuthorityChanger;
    }

    public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
        this.userDetailsChecker = userDetailsChecker;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public void setSwitchAuthorityRole(String switchAuthorityRole) {
        Assert.notNull((Object)switchAuthorityRole, (String)"switchAuthorityRole cannot be null");
        this.switchAuthorityRole = switchAuthorityRole;
    }

    private static RequestMatcher createMatcher(String pattern) {
        return new AntPathRequestMatcher(pattern, "POST", true, new UrlPathHelper());
    }
}

