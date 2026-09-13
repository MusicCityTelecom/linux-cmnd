/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.Assert;

public final class CsrfLogoutHandler
implements LogoutHandler {
    private final CsrfTokenRepository csrfTokenRepository;

    public CsrfLogoutHandler(CsrfTokenRepository csrfTokenRepository) {
        Assert.notNull((Object)csrfTokenRepository, (String)"csrfTokenRepository cannot be null");
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        this.csrfTokenRepository.saveToken(null, request, response);
    }
}

