/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates
 *  org.apereo.cas.monitor.Monitorable
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.security.GeneralSecurityException;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.monitor.Monitorable;
import org.springframework.core.Ordered;

@FunctionalInterface
@Monitorable
public interface AuthenticationHandler
extends Ordered {
    public static final String SUCCESSFUL_AUTHENTICATION_HANDLERS = "successfulAuthenticationHandlers";

    public static AuthenticationHandler disabled() {
        return new AuthenticationHandler(){

            @Override
            public AuthenticationHandlerExecutionResult authenticate(Credential credential, Service service) throws PreventedException {
                throw new PreventedException("Authentication handler is disabled");
            }

            @Override
            public boolean supports(Credential credential) {
                return false;
            }

            @Override
            public boolean supports(Class<? extends Credential> clazz) {
                return false;
            }
        };
    }

    public AuthenticationHandlerExecutionResult authenticate(Credential var1, Service var2) throws GeneralSecurityException, PreventedException;

    default public boolean supports(Credential credential) {
        return false;
    }

    default public boolean supports(Class<? extends Credential> clazz) {
        return false;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public AuthenticationHandlerStates getState() {
        return AuthenticationHandlerStates.ACTIVE;
    }
}

