/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpMethod
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.security.authentication.AccountStatusUserDetailsChecker
 *  org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextImpl
 *  org.springframework.security.core.userdetails.ReactiveUserDetailsService
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.security.core.userdetails.UserDetailsChecker
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class SwitchUserWebFilter
implements WebFilter {
    private final Log logger = LogFactory.getLog(this.getClass());
    public static final String SPRING_SECURITY_SWITCH_USERNAME_KEY = "username";
    public static final String ROLE_PREVIOUS_ADMINISTRATOR = "ROLE_PREVIOUS_ADMINISTRATOR";
    private final ServerAuthenticationSuccessHandler successHandler;
    private final ServerAuthenticationFailureHandler failureHandler;
    private final ReactiveUserDetailsService userDetailsService;
    private final UserDetailsChecker userDetailsChecker;
    private ServerSecurityContextRepository securityContextRepository;
    private ServerWebExchangeMatcher switchUserMatcher = SwitchUserWebFilter.createMatcher("/login/impersonate");
    private ServerWebExchangeMatcher exitUserMatcher = SwitchUserWebFilter.createMatcher("/logout/impersonate");

    public SwitchUserWebFilter(ReactiveUserDetailsService userDetailsService, ServerAuthenticationSuccessHandler successHandler, @Nullable ServerAuthenticationFailureHandler failureHandler) {
        Assert.notNull((Object)userDetailsService, (String)"userDetailsService must be specified");
        Assert.notNull((Object)successHandler, (String)"successHandler must be specified");
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.securityContextRepository = new WebSessionServerSecurityContextRepository();
        this.userDetailsChecker = new AccountStatusUserDetailsChecker();
    }

    public SwitchUserWebFilter(ReactiveUserDetailsService userDetailsService, String successTargetUrl, @Nullable String failureTargetUrl) {
        Assert.notNull((Object)userDetailsService, (String)"userDetailsService must be specified");
        Assert.notNull((Object)successTargetUrl, (String)"successTargetUrl must be specified");
        this.userDetailsService = userDetailsService;
        this.successHandler = new RedirectServerAuthenticationSuccessHandler(successTargetUrl);
        this.failureHandler = failureTargetUrl != null ? new RedirectServerAuthenticationFailureHandler(failureTargetUrl) : null;
        this.securityContextRepository = new WebSessionServerSecurityContextRepository();
        this.userDetailsChecker = new AccountStatusUserDetailsChecker();
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
        return this.switchUser(webFilterExchange).switchIfEmpty(Mono.defer(() -> this.exitSwitchUser(webFilterExchange))).switchIfEmpty(Mono.defer(() -> {
            this.logger.trace((Object)LogMessage.format((String)"Did not attempt to switch user since request did not match [%s] or [%s]", (Object)this.switchUserMatcher, (Object)this.exitUserMatcher));
            return chain.filter(exchange).then(Mono.empty());
        })).flatMap(authentication -> this.onAuthenticationSuccess((Authentication)authentication, webFilterExchange)).onErrorResume(SwitchUserAuthenticationException.class, exception -> Mono.empty());
    }

    protected Mono<Authentication> switchUser(WebFilterExchange webFilterExchange) {
        return this.switchUserMatcher.matches(webFilterExchange.getExchange()).filter(ServerWebExchangeMatcher.MatchResult::isMatch).flatMap(matchResult -> ReactiveSecurityContextHolder.getContext()).map(SecurityContext::getAuthentication).flatMap(currentAuthentication -> {
            String username = this.getUsername(webFilterExchange.getExchange());
            return this.attemptSwitchUser((Authentication)currentAuthentication, username);
        }).onErrorResume(AuthenticationException.class, ex -> this.onAuthenticationFailure((AuthenticationException)((Object)ex), webFilterExchange).then(Mono.error((Throwable)new SwitchUserAuthenticationException((AuthenticationException)((Object)ex)))));
    }

    protected Mono<Authentication> exitSwitchUser(WebFilterExchange webFilterExchange) {
        return this.exitUserMatcher.matches(webFilterExchange.getExchange()).filter(ServerWebExchangeMatcher.MatchResult::isMatch).flatMap(matchResult -> ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).switchIfEmpty(Mono.error(this::noCurrentUserException))).map(this::attemptExitUser);
    }

    protected String getUsername(ServerWebExchange exchange) {
        return (String)exchange.getRequest().getQueryParams().getFirst((Object)SPRING_SECURITY_SWITCH_USERNAME_KEY);
    }

    @NonNull
    private Mono<Authentication> attemptSwitchUser(Authentication currentAuthentication, String userName) {
        Assert.notNull((Object)userName, (String)"The userName can not be null.");
        this.logger.debug((Object)LogMessage.format((String)"Attempting to switch to user [%s]", (Object)userName));
        return this.userDetailsService.findByUsername(userName).switchIfEmpty(Mono.error(this::noTargetAuthenticationException)).doOnNext(arg_0 -> ((UserDetailsChecker)this.userDetailsChecker).check(arg_0)).map(userDetails -> this.createSwitchUserToken((UserDetails)userDetails, currentAuthentication));
    }

    @NonNull
    private Authentication attemptExitUser(Authentication currentAuthentication) {
        Optional<Authentication> sourceAuthentication = this.extractSourceAuthentication(currentAuthentication);
        if (!sourceAuthentication.isPresent()) {
            this.logger.debug((Object)"Failed to find original user");
            throw this.noOriginalAuthenticationException();
        }
        return sourceAuthentication.get();
    }

    private Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        SecurityContextImpl securityContext = new SecurityContextImpl(authentication);
        return this.securityContextRepository.save(exchange, (SecurityContext)securityContext).doOnSuccess(v -> this.logger.debug((Object)LogMessage.format((String)"Switched user to %s", (Object)authentication))).then(this.successHandler.onAuthenticationSuccess(webFilterExchange, authentication)).subscriberContext(ReactiveSecurityContextHolder.withSecurityContext((Mono)Mono.just((Object)securityContext)));
    }

    private Mono<Void> onAuthenticationFailure(AuthenticationException exception, WebFilterExchange webFilterExchange) {
        return Mono.justOrEmpty((Object)this.failureHandler).switchIfEmpty(Mono.defer(() -> {
            this.logger.debug((Object)"Failed to switch user", (Throwable)exception);
            return Mono.error((Throwable)exception);
        })).flatMap(failureHandler -> failureHandler.onAuthenticationFailure(webFilterExchange, exception));
    }

    private Authentication createSwitchUserToken(UserDetails targetUser, Authentication currentAuthentication) {
        Optional<Authentication> sourceAuthentication = this.extractSourceAuthentication(currentAuthentication);
        if (sourceAuthentication.isPresent()) {
            this.logger.debug((Object)LogMessage.format((String)"Found original switch user granted authority [%s]", (Object)sourceAuthentication.get()));
            currentAuthentication = sourceAuthentication.get();
        }
        SwitchUserGrantedAuthority switchAuthority = new SwitchUserGrantedAuthority(ROLE_PREVIOUS_ADMINISTRATOR, currentAuthentication);
        Collection targetUserAuthorities = targetUser.getAuthorities();
        ArrayList<SwitchUserGrantedAuthority> extendedTargetUserAuthorities = new ArrayList<SwitchUserGrantedAuthority>(targetUserAuthorities);
        extendedTargetUserAuthorities.add(switchAuthority);
        return UsernamePasswordAuthenticationToken.authenticated((Object)targetUser, (Object)targetUser.getPassword(), extendedTargetUserAuthorities);
    }

    private Optional<Authentication> extractSourceAuthentication(Authentication currentAuthentication) {
        for (GrantedAuthority authority : currentAuthentication.getAuthorities()) {
            if (!(authority instanceof SwitchUserGrantedAuthority)) continue;
            SwitchUserGrantedAuthority switchAuthority = (SwitchUserGrantedAuthority)authority;
            return Optional.of(switchAuthority.getSource());
        }
        return Optional.empty();
    }

    private static ServerWebExchangeMatcher createMatcher(String pattern) {
        return ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, pattern);
    }

    private AuthenticationCredentialsNotFoundException noCurrentUserException() {
        return new AuthenticationCredentialsNotFoundException("No current user associated with this request");
    }

    private AuthenticationCredentialsNotFoundException noOriginalAuthenticationException() {
        return new AuthenticationCredentialsNotFoundException("Could not find original Authentication object");
    }

    private AuthenticationCredentialsNotFoundException noTargetAuthenticationException() {
        return new AuthenticationCredentialsNotFoundException("No target user for the given username");
    }

    public void setSecurityContextRepository(ServerSecurityContextRepository securityContextRepository) {
        Assert.notNull((Object)securityContextRepository, (String)"securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    public void setExitUserUrl(String exitUserUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(exitUserUrl), (String)"exitUserUrl cannot be empty and must be a valid redirect URL");
        this.exitUserMatcher = SwitchUserWebFilter.createMatcher(exitUserUrl);
    }

    public void setExitUserMatcher(ServerWebExchangeMatcher exitUserMatcher) {
        Assert.notNull((Object)exitUserMatcher, (String)"exitUserMatcher cannot be null");
        this.exitUserMatcher = exitUserMatcher;
    }

    public void setSwitchUserUrl(String switchUserUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(switchUserUrl), (String)"switchUserUrl cannot be empty and must be a valid redirect URL");
        this.switchUserMatcher = SwitchUserWebFilter.createMatcher(switchUserUrl);
    }

    public void setSwitchUserMatcher(ServerWebExchangeMatcher switchUserMatcher) {
        Assert.notNull((Object)switchUserMatcher, (String)"switchUserMatcher cannot be null");
        this.switchUserMatcher = switchUserMatcher;
    }

    private static class SwitchUserAuthenticationException
    extends RuntimeException {
        SwitchUserAuthenticationException(AuthenticationException exception) {
            super(exception);
        }
    }
}

