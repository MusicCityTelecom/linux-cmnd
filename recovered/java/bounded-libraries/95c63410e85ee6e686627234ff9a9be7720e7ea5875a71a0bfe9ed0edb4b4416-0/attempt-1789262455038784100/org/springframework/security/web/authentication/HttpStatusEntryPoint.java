/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public final class HttpStatusEntryPoint
implements AuthenticationEntryPoint {
    private final HttpStatus httpStatus;

    public HttpStatusEntryPoint(HttpStatus httpStatus) {
        Assert.notNull((Object)httpStatus, (String)"httpStatus cannot be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        response.setStatus(this.httpStatus.value());
    }
}

