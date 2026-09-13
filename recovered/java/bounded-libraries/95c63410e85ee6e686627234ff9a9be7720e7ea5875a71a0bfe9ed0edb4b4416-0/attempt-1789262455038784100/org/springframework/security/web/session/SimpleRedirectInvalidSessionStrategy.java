/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
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
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public final class SimpleRedirectInvalidSessionStrategy
implements InvalidSessionStrategy {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final String destinationUrl;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;

    public SimpleRedirectInvalidSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(invalidSessionUrl), (String)"url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Starting new session (if required) and redirecting to '" + this.destinationUrl + "'"));
        }
        if (this.createNewSession) {
            request.getSession();
        }
        this.redirectStrategy.sendRedirect(request, response, this.destinationUrl);
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}

