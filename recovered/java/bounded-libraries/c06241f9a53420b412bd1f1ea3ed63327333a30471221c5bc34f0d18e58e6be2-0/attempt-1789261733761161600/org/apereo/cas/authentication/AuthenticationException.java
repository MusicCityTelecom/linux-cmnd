/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.RootCasException;

public class AuthenticationException
extends RootCasException {
    private static final String CODE = "INVALID_AUTHN_REQUEST";
    private static final long serialVersionUID = -6032827784134751797L;
    private final Map<String, Throwable> handlerErrors;
    private final Map<String, AuthenticationHandlerExecutionResult> handlerSuccesses;

    public AuthenticationException(String msg) {
        this(msg, new HashMap<String, Throwable>(0), new HashMap<String, AuthenticationHandlerExecutionResult>(0));
    }

    public AuthenticationException() {
        this("The authentication attempt has failed for given credentials");
    }

    public AuthenticationException(Map<String, Throwable> handlerErrors) {
        this(handlerErrors, new HashMap<String, AuthenticationHandlerExecutionResult>(0));
    }

    public AuthenticationException(Throwable handlerError) {
        this(Collections.singletonMap(handlerError.getClass().getSimpleName(), handlerError));
    }

    public AuthenticationException(Map<String, Throwable> handlerErrors, Map<String, AuthenticationHandlerExecutionResult> handlerSuccesses) {
        this(String.format("%s errors, %s successes", handlerErrors.size(), handlerSuccesses.size()), handlerErrors, handlerSuccesses);
    }

    public AuthenticationException(String message, Map<String, Throwable> handlerErrors, Map<String, AuthenticationHandlerExecutionResult> handlerSuccesses) {
        super(CODE, message);
        this.handlerErrors = new HashMap<String, Throwable>(handlerErrors);
        this.handlerSuccesses = new HashMap<String, AuthenticationHandlerExecutionResult>(handlerSuccesses);
    }

    @Generated
    public Map<String, Throwable> getHandlerErrors() {
        return this.handlerErrors;
    }

    @Generated
    public Map<String, AuthenticationHandlerExecutionResult> getHandlerSuccesses() {
        return this.handlerSuccesses;
    }
}

