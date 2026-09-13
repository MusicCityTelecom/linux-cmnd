/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.web.servlet.support.ServletUriComponentsBuilder
 */
package org.springframework.security.web.session;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class RequestedUrlRedirectInvalidSessionStrategy
implements InvalidSessionStrategy {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String destinationUrl = ServletUriComponentsBuilder.fromRequest((HttpServletRequest)request).host(null).scheme(null).port(null).toUriString();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Starting new session (if required) and redirecting to '" + destinationUrl + "'"));
        }
        if (this.createNewSession) {
            request.getSession();
        }
        this.redirectStrategy.sendRedirect(request, response, destinationUrl);
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}

