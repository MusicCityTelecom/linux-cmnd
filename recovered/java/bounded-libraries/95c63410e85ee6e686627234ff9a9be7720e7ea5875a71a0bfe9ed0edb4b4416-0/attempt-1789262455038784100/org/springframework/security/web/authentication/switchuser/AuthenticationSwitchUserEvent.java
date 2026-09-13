/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authentication.event.AbstractAuthenticationEvent
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.userdetails.UserDetails
 */
package org.springframework.security.web.authentication.switchuser;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationSwitchUserEvent
extends AbstractAuthenticationEvent {
    private final UserDetails targetUser;

    public AuthenticationSwitchUserEvent(Authentication authentication, UserDetails targetUser) {
        super(authentication);
        this.targetUser = targetUser;
    }

    public UserDetails getTargetUser() {
        return this.targetUser;
    }
}

