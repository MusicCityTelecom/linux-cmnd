/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.logout;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

public class HttpStatusReturningLogoutSuccessHandler
implements LogoutSuccessHandler {
    private final HttpStatus httpStatusToReturn;

    public HttpStatusReturningLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        Assert.notNull((Object)httpStatusToReturn, (String)"The provided HttpStatus must not be null.");
        this.httpStatusToReturn = httpStatusToReturn;
    }

    public HttpStatusReturningLogoutSuccessHandler() {
        this.httpStatusToReturn = HttpStatus.OK;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(this.httpStatusToReturn.value());
        response.getWriter().flush();
    }
}

