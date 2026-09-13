/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.util.StringUtils;

class ELRequestMatcherContext {
    private final HttpServletRequest request;

    ELRequestMatcherContext(HttpServletRequest request) {
        this.request = request;
    }

    public boolean hasIpAddress(String ipAddress) {
        return new IpAddressMatcher(ipAddress).matches(this.request);
    }

    public boolean hasHeader(String headerName, String value) {
        String header = this.request.getHeader(headerName);
        return StringUtils.hasText((String)header) && header.contains(value);
    }
}

