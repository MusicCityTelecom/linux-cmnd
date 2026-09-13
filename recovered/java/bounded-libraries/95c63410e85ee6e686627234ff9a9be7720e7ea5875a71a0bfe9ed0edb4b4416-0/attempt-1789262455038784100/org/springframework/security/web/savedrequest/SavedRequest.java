/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 */
package org.springframework.security.web.savedrequest;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;

public interface SavedRequest
extends Serializable {
    public String getRedirectUrl();

    public List<Cookie> getCookies();

    public String getMethod();

    public List<String> getHeaderValues(String var1);

    public Collection<String> getHeaderNames();

    public List<Locale> getLocales();

    public String[] getParameterValues(String var1);

    public Map<String, String[]> getParameterMap();
}

