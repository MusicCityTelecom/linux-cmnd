/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 */
package org.apereo.cas.web.cookie;

import java.io.Serializable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface CookieValueManager
extends Serializable {
    public String buildCookieValue(String var1, HttpServletRequest var2);

    default public String obtainCookieValue(Cookie cookie, HttpServletRequest request) {
        return this.obtainCookieValue(cookie.getValue(), request);
    }

    public String obtainCookieValue(String var1, HttpServletRequest var2);
}

