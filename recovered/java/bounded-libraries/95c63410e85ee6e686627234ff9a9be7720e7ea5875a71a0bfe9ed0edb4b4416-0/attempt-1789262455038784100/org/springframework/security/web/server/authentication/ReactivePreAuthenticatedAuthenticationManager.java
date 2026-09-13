/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authentication.AccountStatusUserDetailsChecker
 *  org.springframework.security.authentication.ReactiveAuthenticationManager
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.userdetails.ReactiveUserDetailsService
 *  org.springframework.security.core.userdetails.UserDetailsChecker
 *  org.springframework.security.core.userdetails.UsernameNotFoundException
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server.authentication;

import java.security.Principal;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import reactor.core.publisher.Mono;

public class ReactivePreAuthenticatedAuthenticationManager
implements ReactiveAuthenticationManager {
    private final ReactiveUserDetailsService userDetailsService;
    private final UserDetailsChecker userDetailsChecker;

    public ReactivePreAuthenticatedAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        this(userDetailsService, (UserDetailsChecker)new AccountStatusUserDetailsChecker());
    }

    public ReactivePreAuthenticatedAuthenticationManager(ReactiveUserDetailsService userDetailsService, UserDetailsChecker userDetailsChecker) {
        this.userDetailsService = userDetailsService;
        this.userDetailsChecker = userDetailsChecker;
    }

    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just((Object)authentication).filter(this::supports).map(Principal::getName).flatMap(arg_0 -> ((ReactiveUserDetailsService)this.userDetailsService).findByUsername(arg_0)).switchIfEmpty(Mono.error(() -> new UsernameNotFoundException("User not found"))).doOnNext(arg_0 -> ((UserDetailsChecker)this.userDetailsChecker).check(arg_0)).map(userDetails -> {
            PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        });
    }

    private boolean supports(Authentication authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }
}

