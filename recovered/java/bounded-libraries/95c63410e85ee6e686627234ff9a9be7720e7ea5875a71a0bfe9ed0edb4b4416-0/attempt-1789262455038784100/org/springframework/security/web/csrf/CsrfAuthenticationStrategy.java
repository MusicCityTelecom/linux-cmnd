/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.Assert;

public final class CsrfAuthenticationStrategy
implements SessionAuthenticationStrategy {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final CsrfTokenRepository csrfTokenRepository;

    public CsrfAuthenticationStrategy(CsrfTokenRepository csrfTokenRepository) {
        Assert.notNull((Object)csrfTokenRepository, (String)"csrfTokenRepository cannot be null");
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        boolean containsToken;
        boolean bl = containsToken = this.csrfTokenRepository.loadToken(request) != null;
        if (containsToken) {
            this.csrfTokenRepository.saveToken(null, request, response);
            CsrfToken newToken = this.csrfTokenRepository.generateToken(request);
            this.csrfTokenRepository.saveToken(newToken, request, response);
            request.setAttribute(CsrfToken.class.getName(), (Object)newToken);
            request.setAttribute(newToken.getParameterName(), (Object)newToken);
            this.logger.debug((Object)"Replaced CSRF Token");
        }
    }
}

