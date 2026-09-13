/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.Assert;

public class DelegatingAuthenticationFailureHandler
implements AuthenticationFailureHandler {
    private final LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> handlers;
    private final AuthenticationFailureHandler defaultHandler;

    public DelegatingAuthenticationFailureHandler(LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> handlers, AuthenticationFailureHandler defaultHandler) {
        Assert.notEmpty(handlers, (String)"handlers cannot be null or empty");
        Assert.notNull((Object)defaultHandler, (String)"defaultHandler cannot be null");
        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        for (Map.Entry<Class<? extends AuthenticationException>, AuthenticationFailureHandler> entry : this.handlers.entrySet()) {
            Class<? extends AuthenticationException> handlerMappedExceptionClass = entry.getKey();
            if (!handlerMappedExceptionClass.isAssignableFrom(((Object)((Object)exception)).getClass())) continue;
            AuthenticationFailureHandler handler = entry.getValue();
            handler.onAuthenticationFailure(request, response, exception);
            return;
        }
        this.defaultHandler.onAuthenticationFailure(request, response, exception);
    }
}

