/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.Map;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;

public class PrincipalException
extends AuthenticationException {
    private static final long serialVersionUID = -6590363469748313596L;
    private static final String CODE = "service.principal.resolution.error";

    public PrincipalException(String message, Map<String, Throwable> handlerErrors, Map<String, AuthenticationHandlerExecutionResult> handlerSuccesses) {
        super(message, handlerErrors, handlerSuccesses);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}

