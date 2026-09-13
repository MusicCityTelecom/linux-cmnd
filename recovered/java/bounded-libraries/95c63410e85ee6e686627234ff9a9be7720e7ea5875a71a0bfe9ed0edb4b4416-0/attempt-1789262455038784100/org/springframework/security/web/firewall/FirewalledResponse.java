/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpServletResponseWrapper
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.firewall;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.util.Assert;

class FirewalledResponse
extends HttpServletResponseWrapper {
    private static final String LOCATION_HEADER = "Location";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    FirewalledResponse(HttpServletResponse response) {
        super(response);
    }

    public void sendRedirect(String location) throws IOException {
        this.validateCrlf(LOCATION_HEADER, location);
        super.sendRedirect(location);
    }

    public void setHeader(String name, String value) {
        this.validateCrlf(name, value);
        super.setHeader(name, value);
    }

    public void addHeader(String name, String value) {
        this.validateCrlf(name, value);
        super.addHeader(name, value);
    }

    public void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.validateCrlf(SET_COOKIE_HEADER, cookie.getName());
            this.validateCrlf(SET_COOKIE_HEADER, cookie.getValue());
            this.validateCrlf(SET_COOKIE_HEADER, cookie.getPath());
            this.validateCrlf(SET_COOKIE_HEADER, cookie.getDomain());
            this.validateCrlf(SET_COOKIE_HEADER, cookie.getComment());
        }
        super.addCookie(cookie);
    }

    void validateCrlf(String name, String value) {
        Assert.isTrue((!this.hasCrlf(name) && !this.hasCrlf(value) ? 1 : 0) != 0, () -> "Invalid characters (CR/LF) in header " + name);
    }

    private boolean hasCrlf(String value) {
        return value != null && (value.indexOf(10) != -1 || value.indexOf(13) != -1);
    }
}

