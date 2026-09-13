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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.Assert;

public class AuthenticationEntryPointFailureHandler
implements AuthenticationFailureHandler {
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public AuthenticationEntryPointFailureHandler(AuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull((Object)authenticationEntryPoint, (String)"authenticationEntryPoint cannot be null");
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        this.authenticationEntryPoint.commence(request, response, exception);
    }
}

