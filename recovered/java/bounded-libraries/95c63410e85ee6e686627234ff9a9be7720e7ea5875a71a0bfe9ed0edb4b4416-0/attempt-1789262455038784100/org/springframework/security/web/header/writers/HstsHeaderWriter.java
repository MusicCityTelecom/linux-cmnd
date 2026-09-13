/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class HstsHeaderWriter
implements HeaderWriter {
    private static final long DEFAULT_MAX_AGE_SECONDS = 31536000L;
    private static final String HSTS_HEADER_NAME = "Strict-Transport-Security";
    private final Log logger = LogFactory.getLog(this.getClass());
    private RequestMatcher requestMatcher;
    private long maxAgeInSeconds;
    private boolean includeSubDomains;
    private boolean preload;
    private String hstsHeaderValue;

    public HstsHeaderWriter(RequestMatcher requestMatcher, long maxAgeInSeconds, boolean includeSubDomains, boolean preload) {
        this.requestMatcher = requestMatcher;
        this.maxAgeInSeconds = maxAgeInSeconds;
        this.includeSubDomains = includeSubDomains;
        this.preload = preload;
        this.updateHstsHeaderValue();
    }

    public HstsHeaderWriter(RequestMatcher requestMatcher, long maxAgeInSeconds, boolean includeSubDomains) {
        this(requestMatcher, maxAgeInSeconds, includeSubDomains, false);
    }

    public HstsHeaderWriter(long maxAgeInSeconds, boolean includeSubDomains, boolean preload) {
        this(new SecureRequestMatcher(), maxAgeInSeconds, includeSubDomains, preload);
    }

    public HstsHeaderWriter(long maxAgeInSeconds, boolean includeSubDomains) {
        this(new SecureRequestMatcher(), maxAgeInSeconds, includeSubDomains, false);
    }

    public HstsHeaderWriter(long maxAgeInSeconds) {
        this(new SecureRequestMatcher(), maxAgeInSeconds, true, false);
    }

    public HstsHeaderWriter(boolean includeSubDomains) {
        this(new SecureRequestMatcher(), 31536000L, includeSubDomains, false);
    }

    public HstsHeaderWriter() {
        this(31536000L);
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!this.requestMatcher.matches(request)) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace((Object)LogMessage.format((String)"Not injecting HSTS header since it did not match request to [%s]", (Object)this.requestMatcher));
            }
            return;
        }
        if (!response.containsHeader(HSTS_HEADER_NAME)) {
            response.setHeader(HSTS_HEADER_NAME, this.hstsHeaderValue);
        }
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull((Object)requestMatcher, (String)"requestMatcher cannot be null");
        this.requestMatcher = requestMatcher;
    }

    public void setMaxAgeInSeconds(long maxAgeInSeconds) {
        Assert.isTrue((maxAgeInSeconds >= 0L ? 1 : 0) != 0, () -> "maxAgeInSeconds must be non-negative. Got " + maxAgeInSeconds);
        this.maxAgeInSeconds = maxAgeInSeconds;
        this.updateHstsHeaderValue();
    }

    public void setIncludeSubDomains(boolean includeSubDomains) {
        this.includeSubDomains = includeSubDomains;
        this.updateHstsHeaderValue();
    }

    public void setPreload(boolean preload) {
        this.preload = preload;
        this.updateHstsHeaderValue();
    }

    private void updateHstsHeaderValue() {
        String headerValue = "max-age=" + this.maxAgeInSeconds;
        if (this.includeSubDomains) {
            headerValue = headerValue + " ; includeSubDomains";
        }
        if (this.preload) {
            headerValue = headerValue + " ; preload";
        }
        this.hstsHeaderValue = headerValue;
    }

    private static final class SecureRequestMatcher
    implements RequestMatcher {
        private SecureRequestMatcher() {
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return request.isSecure();
        }

        public String toString() {
            return "Is Secure";
        }
    }
}

