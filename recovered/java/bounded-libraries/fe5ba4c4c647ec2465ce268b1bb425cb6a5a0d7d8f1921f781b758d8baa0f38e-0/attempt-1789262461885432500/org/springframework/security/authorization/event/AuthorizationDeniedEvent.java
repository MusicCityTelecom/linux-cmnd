/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.security.authorization.event;

import java.util.function.Supplier;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;

public class AuthorizationDeniedEvent<T>
extends ApplicationEvent {
    private final Supplier<Authentication> authentication;
    private final AuthorizationDecision decision;

    public AuthorizationDeniedEvent(Supplier<Authentication> authentication, T object, AuthorizationDecision decision) {
        super(object);
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

