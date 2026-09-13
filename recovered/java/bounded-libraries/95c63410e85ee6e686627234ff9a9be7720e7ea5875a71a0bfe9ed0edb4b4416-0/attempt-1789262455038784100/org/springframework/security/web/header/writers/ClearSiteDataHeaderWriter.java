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

public final class ClearSiteDataHeaderWriter
implements HeaderWriter {
    private static final String CLEAR_SITE_DATA_HEADER = "Clear-Site-Data";
    private final Log logger = LogFactory.getLog(this.getClass());
    private final RequestMatcher requestMatcher;
    private String headerValue;

    public ClearSiteDataHeaderWriter(Directive ... directives) {
        Assert.notEmpty((Object[])directives, (String)"directives cannot be empty or null");
        this.requestMatcher = new SecureRequestMatcher();
        this.headerValue = this.transformToHeaderValue(directives);
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (this.requestMatcher.matches(request) && !response.containsHeader(CLEAR_SITE_DATA_HEADER)) {
            response.setHeader(CLEAR_SITE_DATA_HEADER, this.headerValue);
        }
        this.logger.debug((Object)LogMessage.format((String)"Not injecting Clear-Site-Data header since it did not match the requestMatcher %s", (Object)this.requestMatcher));
    }

    private String transformToHeaderValue(Directive ... directives) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < directives.length - 1; ++i) {
            sb.append(directives[i].headerValue).append(", ");
        }
        sb.append(directives[directives.length - 1].headerValue);
        return sb.toString();
    }

    public String toString() {
        return this.getClass().getName() + " [headerValue=" + this.headerValue + "]";
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

    public static enum Directive {
        CACHE("cache"),
        COOKIES("cookies"),
        STORAGE("storage"),
        EXECUTION_CONTEXTS("executionContexts"),
        ALL("*");

        private final String headerValue;

        private Directive(String headerValue) {
            this.headerValue = "\"" + headerValue + "\"";
        }

        public String getHeaderValue() {
            return this.headerValue;
        }
    }
}

