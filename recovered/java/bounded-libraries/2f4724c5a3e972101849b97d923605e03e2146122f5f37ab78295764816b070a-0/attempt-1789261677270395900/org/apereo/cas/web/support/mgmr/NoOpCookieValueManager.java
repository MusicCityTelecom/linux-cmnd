/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.web.cookie.CookieValueManager
 */
package org.apereo.cas.web.support.mgmr;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.web.cookie.CookieValueManager;

public class NoOpCookieValueManager
implements CookieValueManager {
    public static final CookieValueManager INSTANCE = new NoOpCookieValueManager();
    private static final long serialVersionUID = -8464839674747772197L;

    public String buildCookieValue(String givenCookieValue, HttpServletRequest request) {
        return givenCookieValue;
    }

    public String obtainCookieValue(String cookie, HttpServletRequest request) {
        return cookie;
    }
}

