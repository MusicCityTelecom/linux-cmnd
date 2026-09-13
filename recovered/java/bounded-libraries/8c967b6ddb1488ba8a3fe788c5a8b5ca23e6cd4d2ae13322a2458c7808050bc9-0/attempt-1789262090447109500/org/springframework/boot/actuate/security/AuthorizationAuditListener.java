/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.access.event.AbstractAuthorizationEvent
 *  org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent
 *  org.springframework.security.access.event.AuthorizationFailureEvent
 */
package org.springframework.boot.actuate.security;

import java.util.HashMap;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;

public class AuthorizationAuditListener
extends AbstractAuthorizationAuditListener {
    public static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

    public void onApplicationEvent(AbstractAuthorizationEvent event) {
        if (event instanceof AuthenticationCredentialsNotFoundEvent) {
            this.onAuthenticationCredentialsNotFoundEvent((AuthenticationCredentialsNotFoundEvent)event);
        } else if (event instanceof AuthorizationFailureEvent) {
            this.onAuthorizationFailureEvent((AuthorizationFailureEvent)event);
        }
    }

    private void onAuthenticationCredentialsNotFoundEvent(AuthenticationCredentialsNotFoundEvent event) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("type", event.getCredentialsNotFoundException().getClass().getName());
        data.put("message", event.getCredentialsNotFoundException().getMessage());
        this.publish(new AuditEvent("<unknown>", "AUTHENTICATION_FAILURE", data));
    }

    private void onAuthorizationFailureEvent(AuthorizationFailureEvent event) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("type", event.getAccessDeniedException().getClass().getName());
        data.put("message", event.getAccessDeniedException().getMessage());
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        this.publish(new AuditEvent(event.getAuthentication().getName(), AUTHORIZATION_FAILURE, data));
    }
}

