/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authentication.event.AbstractAuthenticationEvent
 *  org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
 *  org.springframework.security.authentication.event.AuthenticationSuccessEvent
 *  org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.actuate.security;

import java.util.HashMap;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthenticationAuditListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.util.ClassUtils;

public class AuthenticationAuditListener
extends AbstractAuthenticationAuditListener {
    public static final String AUTHENTICATION_SUCCESS = "AUTHENTICATION_SUCCESS";
    public static final String AUTHENTICATION_FAILURE = "AUTHENTICATION_FAILURE";
    public static final String AUTHENTICATION_SWITCH = "AUTHENTICATION_SWITCH";
    private static final String WEB_LISTENER_CHECK_CLASS = "org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent";
    private WebAuditListener webListener = AuthenticationAuditListener.maybeCreateWebListener();

    private static WebAuditListener maybeCreateWebListener() {
        if (ClassUtils.isPresent((String)WEB_LISTENER_CHECK_CLASS, null)) {
            return new WebAuditListener();
        }
        return null;
    }

    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AbstractAuthenticationFailureEvent) {
            this.onAuthenticationFailureEvent((AbstractAuthenticationFailureEvent)event);
        } else if (this.webListener != null && this.webListener.accepts(event)) {
            this.webListener.process(this, event);
        } else if (event instanceof AuthenticationSuccessEvent) {
            this.onAuthenticationSuccessEvent((AuthenticationSuccessEvent)event);
        }
    }

    private void onAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("type", event.getException().getClass().getName());
        data.put("message", event.getException().getMessage());
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        this.publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTICATION_FAILURE, data));
    }

    private void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        this.publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTICATION_SUCCESS, data));
    }

    private static class WebAuditListener {
        private WebAuditListener() {
        }

        void process(AuthenticationAuditListener listener, AbstractAuthenticationEvent input) {
            if (listener != null) {
                AuthenticationSwitchUserEvent event = (AuthenticationSwitchUserEvent)input;
                HashMap<String, Object> data = new HashMap<String, Object>();
                if (event.getAuthentication().getDetails() != null) {
                    data.put("details", event.getAuthentication().getDetails());
                }
                if (event.getTargetUser() != null) {
                    data.put("target", event.getTargetUser().getUsername());
                }
                listener.publish(new AuditEvent(event.getAuthentication().getName(), AuthenticationAuditListener.AUTHENTICATION_SWITCH, data));
            }
        }

        boolean accepts(AbstractAuthenticationEvent event) {
            return event instanceof AuthenticationSwitchUserEvent;
        }
    }
}

