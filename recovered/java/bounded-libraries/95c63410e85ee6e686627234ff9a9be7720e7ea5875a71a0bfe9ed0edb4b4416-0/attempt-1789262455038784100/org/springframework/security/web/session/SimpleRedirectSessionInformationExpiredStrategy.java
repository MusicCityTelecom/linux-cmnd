/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.session;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public final class SimpleRedirectSessionInformationExpiredStrategy
implements SessionInformationExpiredStrategy {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final String destinationUrl;
    private final RedirectStrategy redirectStrategy;

    public SimpleRedirectSessionInformationExpiredStrategy(String invalidSessionUrl) {
        this(invalidSessionUrl, new DefaultRedirectStrategy());
    }

    public SimpleRedirectSessionInformationExpiredStrategy(String invalidSessionUrl, RedirectStrategy redirectStrategy) {
        Assert.isTrue((boolean)UrlUtils.isValidRedirectUrl(invalidSessionUrl), (String)"url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        this.logger.debug((Object)("Redirecting to '" + this.destinationUrl + "'"));
        this.redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), this.destinationUrl);
    }
}

