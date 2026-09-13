/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.authority.GrantedAuthoritiesContainer
 *  org.springframework.security.core.userdetails.AuthenticationUserDetailsService
 *  org.springframework.security.core.userdetails.User
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.preauth;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

public class PreAuthenticatedGrantedAuthoritiesUserDetailsService
implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    public final UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws AuthenticationException {
        Assert.notNull((Object)token.getDetails(), (String)"token.getDetails() cannot be null");
        Assert.isInstanceOf(GrantedAuthoritiesContainer.class, (Object)token.getDetails());
        Collection authorities = ((GrantedAuthoritiesContainer)token.getDetails()).getGrantedAuthorities();
        return this.createUserDetails((Authentication)token, authorities);
    }

    protected UserDetails createUserDetails(Authentication token, Collection<? extends GrantedAuthority> authorities) {
        return new User(token.getName(), "N/A", true, true, true, true, authorities);
    }
}

