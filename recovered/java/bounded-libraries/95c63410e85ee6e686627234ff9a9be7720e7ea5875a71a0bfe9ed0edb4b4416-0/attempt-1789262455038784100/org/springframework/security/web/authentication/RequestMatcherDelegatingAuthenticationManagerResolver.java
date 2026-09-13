/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.AuthenticationManagerResolver
 *  org.springframework.security.authentication.AuthenticationServiceException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.util.Assert;

public final class RequestMatcherDelegatingAuthenticationManagerResolver
implements AuthenticationManagerResolver<HttpServletRequest> {
    private final List<RequestMatcherEntry<AuthenticationManager>> authenticationManagers;
    private AuthenticationManager defaultAuthenticationManager = authentication -> {
        throw new AuthenticationServiceException("Cannot authenticate " + authentication);
    };

    RequestMatcherDelegatingAuthenticationManagerResolver(RequestMatcherEntry<AuthenticationManager> ... authenticationManagers) {
        Assert.notEmpty((Object[])authenticationManagers, (String)"authenticationManagers cannot be empty");
        this.authenticationManagers = Arrays.asList(authenticationManagers);
    }

    RequestMatcherDelegatingAuthenticationManagerResolver(List<RequestMatcherEntry<AuthenticationManager>> authenticationManagers) {
        Assert.notEmpty(authenticationManagers, (String)"authenticationManagers cannot be empty");
        this.authenticationManagers = authenticationManagers;
    }

    public AuthenticationManager resolve(HttpServletRequest context) {
        for (RequestMatcherEntry<AuthenticationManager> entry : this.authenticationManagers) {
            if (!entry.getRequestMatcher().matches(context)) continue;
            return entry.getEntry();
        }
        return this.defaultAuthenticationManager;
    }

    public void setDefaultAuthenticationManager(AuthenticationManager defaultAuthenticationManager) {
        Assert.notNull((Object)defaultAuthenticationManager, (String)"defaultAuthenticationManager cannot be null");
        this.defaultAuthenticationManager = defaultAuthenticationManager;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<RequestMatcherEntry<AuthenticationManager>> entries = new ArrayList<RequestMatcherEntry<AuthenticationManager>>();

        private Builder() {
        }

        public Builder add(RequestMatcher matcher, AuthenticationManager manager) {
            Assert.notNull((Object)matcher, (String)"matcher cannot be null");
            Assert.notNull((Object)manager, (String)"manager cannot be null");
            this.entries.add(new RequestMatcherEntry<AuthenticationManager>(matcher, manager));
            return this;
        }

        public RequestMatcherDelegatingAuthenticationManagerResolver build() {
            return new RequestMatcherDelegatingAuthenticationManagerResolver(this.entries);
        }
    }
}

