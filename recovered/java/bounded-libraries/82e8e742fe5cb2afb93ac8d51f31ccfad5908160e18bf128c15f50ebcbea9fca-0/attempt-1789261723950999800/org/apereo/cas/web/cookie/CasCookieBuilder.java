/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apereo.cas.web.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.web.cookie.CookieValueManager;

public interface CasCookieBuilder {
    public static final String BEAN_NAME_TICKET_GRANTING_COOKIE_BUILDER = "ticketGrantingTicketCookieGenerator";

    public Cookie addCookie(HttpServletRequest var1, HttpServletResponse var2, boolean var3, String var4);

    public Cookie addCookie(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public void addCookie(HttpServletResponse var1, String var2);

    public String retrieveCookieValue(HttpServletRequest var1);

    public void removeCookie(HttpServletResponse var1);

    public String getCookiePath();

    public void setCookiePath(String var1);

    public String getCookieDomain();

    public String getCookieName();

    public void removeAll(HttpServletRequest var1, HttpServletResponse var2);

    public CookieValueManager getCasCookieValueManager();
}

