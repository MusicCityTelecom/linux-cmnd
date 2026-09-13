/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.util.Assert;

public final class InvalidSessionAccessDeniedHandler
implements AccessDeniedHandler {
    private final InvalidSessionStrategy invalidSessionStrategy;

    public InvalidSessionAccessDeniedHandler(InvalidSessionStrategy invalidSessionStrategy) {
        Assert.notNull((Object)invalidSessionStrategy, (String)"invalidSessionStrategy cannot be null");
        this.invalidSessionStrategy = invalidSessionStrategy;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.invalidSessionStrategy.onInvalidSessionDetected(request, response);
    }
}

