/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authentication.event.AbstractAuthenticationEvent
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.session;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class SessionFixationProtectionEvent
extends AbstractAuthenticationEvent {
    private final String oldSessionId;
    private final String newSessionId;

    public SessionFixationProtectionEvent(Authentication authentication, String oldSessionId, String newSessionId) {
        super(authentication);
        Assert.hasLength((String)oldSessionId, (String)"oldSessionId must have length");
        Assert.hasLength((String)newSessionId, (String)"newSessionId must have length");
        this.oldSessionId = oldSessionId;
        this.newSessionId = newSessionId;
    }

    public String getOldSessionId() {
        return this.oldSessionId;
    }

    public String getNewSessionId() {
        return this.newSessionId;
    }
}

