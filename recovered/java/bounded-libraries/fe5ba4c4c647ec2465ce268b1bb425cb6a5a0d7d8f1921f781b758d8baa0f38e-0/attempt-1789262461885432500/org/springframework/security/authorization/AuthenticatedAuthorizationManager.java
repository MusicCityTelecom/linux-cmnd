/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.authorization;

import java.util.function.Supplier;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

public final class AuthenticatedAuthorizationManager<T>
implements AuthorizationManager<T> {
    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    public static <T> AuthenticatedAuthorizationManager<T> authenticated() {
        return new AuthenticatedAuthorizationManager<T>();
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, T object) {
        boolean granted = this.isGranted(authentication.get());
        return new AuthorizationDecision(granted);
    }

    private boolean isGranted(Authentication authentication) {
        return authentication != null && this.isNotAnonymous(authentication) && authentication.isAuthenticated();
    }

    private boolean isNotAnonymous(Authentication authentication) {
        return !this.trustResolver.isAnonymous(authentication);
    }
}

