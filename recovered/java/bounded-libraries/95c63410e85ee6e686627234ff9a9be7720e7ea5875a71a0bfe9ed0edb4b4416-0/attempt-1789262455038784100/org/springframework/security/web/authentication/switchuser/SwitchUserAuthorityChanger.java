/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.userdetails.UserDetails
 */
package org.springframework.security.web.authentication.switchuser;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface SwitchUserAuthorityChanger {
    public Collection<? extends GrantedAuthority> modifyGrantedAuthorities(UserDetails var1, Authentication var2, Collection<? extends GrantedAuthority> var3);
}

