/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.adaptive;

import java.util.HashMap;
import java.util.Map;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;

public class UnauthorizedAuthenticationException
extends AuthenticationException {
    private static final long serialVersionUID = 4386330975702952112L;

    public UnauthorizedAuthenticationException(String message, Map<String, Throwable> handlerErrors) {
        super(message, handlerErrors, new HashMap<String, AuthenticationHandlerExecutionResult>(0));
    }

    public UnauthorizedAuthenticationException(String message) {
        super(message, new HashMap<String, Throwable>(0), new HashMap<String, AuthenticationHandlerExecutionResult>(0));
    }

    public UnauthorizedAuthenticationException(Map<String, Throwable> handlerErrors) {
        super(handlerErrors);
    }

    public UnauthorizedAuthenticationException(Map<String, Throwable> handlerErrors, Map<String, AuthenticationHandlerExecutionResult> handlerSuccesses) {
        super(handlerErrors, handlerSuccesses);
    }

    public UnauthorizedAuthenticationException(String message, Map<String, Throwable> handlerErrors, Map<String, AuthenticationHandlerExecutionResult> handlerSuccesses) {
        super(message, handlerErrors, handlerSuccesses);
    }
}

