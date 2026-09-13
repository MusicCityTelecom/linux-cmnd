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
package org.springframework.security.web.header.writers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class HpkpHeaderWriter
implements HeaderWriter {
    private static final long DEFAULT_MAX_AGE_SECONDS = 5184000L;
    private static final String HPKP_HEADER_NAME = "Public-Key-Pins";
    private static final String HPKP_RO_HEADER_NAME = "Public-Key-Pins-Report-Only";
    private final Log logger = LogFactory.getLog(this.getClass());
    private final RequestMatcher requestMatcher = new SecureRequestMatcher();
    private Map<String, String> pins = new LinkedHashMap<String, String>();
    private long maxAgeInSeconds;
    private boolean includeSubDomains;
    private boolean reportOnly;
    private URI reportUri;
    private String hpkpHeaderValue;

    public HpkpHeaderWriter(long maxAgeInSeconds, boolean includeSubDomains, boolean reportOnly) {
        this.maxAgeInSeconds = maxAgeInSeconds;
        this.includeSubDomains = includeSubDomains;
        this.reportOnly = reportOnly;
        this.updateHpkpHeaderValue();
    }

    public HpkpHeaderWriter(long maxAgeInSeconds, boolean includeSubDomains) {
        this(maxAgeInSeconds, includeSubDomains, true);
    }

    public HpkpHeaderWriter(long maxAgeInSeconds) {
        this(maxAgeInSeconds, false);
    }

    public HpkpHeaderWriter() {
        this(5184000L);
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        String headerName;
        if (!this.requestMatcher.matches(request)) {
            this.logger.debug((Object)"Not injecting HPKP header since it wasn't a secure connection");
            return;
        }
        if (this.pins.isEmpty()) {
            this.logger.debug((Object)"Not injecting HPKP header since there aren't any pins");
            return;
        }
        String string = headerName = this.reportOnly ? HPKP_RO_HEADER_NAME : HPKP_HEADER_NAME;
        if (!response.containsHeader(headerName)) {
            response.setHeader(headerName, this.hpkpHeaderValue);
        }
    }

    public void setPins(Map<String, String> pins) {
        Assert.notNull(pins, (String)"pins cannot be null");
        this.pins = pins;
        this.updateHpkpHeaderValue();
    }

    public void addSha256Pins(String ... pins) {
        for (String pin : pins) {
            Assert.notNull((Object)pin, (String)"pin cannot be null");
            this.pins.put(pin, "sha256");
        }
        this.updateHpkpHeaderValue();
    }

    public void setMaxAgeInSeconds(long maxAgeInSeconds) {
        Assert.isTrue((maxAgeInSeconds > 0L ? 1 : 0) != 0, () -> "maxAgeInSeconds must be non-negative. Got " + maxAgeInSeconds);
        this.maxAgeInSeconds = maxAgeInSeconds;
        this.updateHpkpHeaderValue();
    }

    public void setIncludeSubDomains(boolean includeSubDomains) {
        this.includeSubDomains = includeSubDomains;
        this.updateHpkpHeaderValue();
    }

    public void setReportOnly(boolean reportOnly) {
        this.reportOnly = reportOnly;
    }

    public void setReportUri(URI reportUri) {
        this.reportUri = reportUri;
        this.updateHpkpHeaderValue();
    }

    public void setReportUri(String reportUri) {
        try {
            this.reportUri = new URI(reportUri);
            this.updateHpkpHeaderValue();
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void updateHpkpHeaderValue() {
        String headerValue = "max-age=" + this.maxAgeInSeconds;
        for (Map.Entry<String, String> pin : this.pins.entrySet()) {
            headerValue = headerValue + " ; pin-" + pin.getValue() + "=\"" + pin.getKey() + "\"";
        }
        if (this.reportUri != null) {
            headerValue = headerValue + " ; report-uri=\"" + this.reportUri.toString() + "\"";
        }
        if (this.includeSubDomains) {
            headerValue = headerValue + " ; includeSubDomains";
        }
        this.hpkpHeaderValue = headerValue;
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

