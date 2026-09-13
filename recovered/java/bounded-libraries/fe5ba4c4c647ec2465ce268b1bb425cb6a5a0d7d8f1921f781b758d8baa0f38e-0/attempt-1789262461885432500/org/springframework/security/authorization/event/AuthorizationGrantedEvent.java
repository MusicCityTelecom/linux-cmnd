/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.util.Assert
 */
package org.springframework.security.authorization.event;

import java.util.function.Supplier;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class AuthorizationGrantedEvent<T>
extends ApplicationEvent {
    private final Supplier<Authentication> authentication;
    private final AuthorizationDecision decision;

    public AuthorizationGrantedEvent(Supplier<Authentication> authentication, T object, AuthorizationDecision decision) {
        super(object);
        Assert.notNull(authentication, (String)"authentication supplier cannot be null");
        this.authentication = authentication;
        this.decision = decision;
    }

    public Supplier<Authentication> getAuthentication() {
        return this.authentication;
    }

    public AuthorizationDecision getAuthorizationDecision() {
        return this.decision;
    }
}

