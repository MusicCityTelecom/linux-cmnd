/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.session.SessionRegistry
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;

public class RegisterSessionAuthenticationStrategy
implements SessionAuthenticationStrategy {
    private final SessionRegistry sessionRegistry;

    public RegisterSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        Assert.notNull((Object)sessionRegistry, (String)"The sessionRegistry cannot be null");
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
    }
}

