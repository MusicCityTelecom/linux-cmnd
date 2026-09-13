/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.HttpDateFormat;

public class CookiesParser {
    private static final Logger LOGGER = Logger.getLogger(CookiesParser.class.getName());

    public static Map<String, Cookie> parseCookies(String header) {
        String[] bites = header.split("[;,]");
        LinkedHashMap<String, Cookie> cookies = new LinkedHashMap<String, Cookie>();
        int version = 0;
        MutableCookie cookie = null;
        for (String bite : bites) {
            String value;
            String[] crumbs = bite.split("=", 2);
            String name = crumbs.length > 0 ? crumbs[0].trim() : "";
            String string = value = crumbs.length > 1 ? crumbs[1].trim() : "";
            if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 1) {
                value = value.substring(1, value.length() - 1);
            }
            if (!name.startsWith("$")) {
                CookiesParser.checkSimilarCookieName(cookies, cookie);
                cookie = new MutableCookie(name, value);
                cookie.version = version;
                continue;
            }
            if (name.startsWith("$Version")) {
                version = Integer.parseInt(value);
                continue;
            }
            if (name.startsWith("$Path") && cookie != null) {
                cookie.path = value;
                continue;
            }
            if (!name.startsWith("$Domain") || cookie == null) continue;
            cookie.domain = value;
        }
        CookiesParser.checkSimilarCookieName(cookies, cookie);
        return cookies;
    }

    private static void checkSimilarCookieName(Map<String, Cookie> cookies, MutableCookie cookie) {
        if (cookie != null) {
            if (cookies.containsKey(cookie.name)) {
                if (cookie.value.length() > cookies.get(cookie.name).getValue().length()) {
                    cookies.put(cookie.name, cookie.getImmutableCookie());
                }
            } else {
                cookies.put(cookie.name, cookie.getImmutableCookie());
            }
        }
    }

    public static Cookie parseCookie(String header) {
        Map<String, Cookie> cookies = CookiesParser.parseCookies(header);
        return cookies.entrySet().iterator().next().getValue();
    }

    public static NewCookie parseNewCookie(String header) {
        String[] bites = header.split("[;,]");
        MutableNewCookie cookie = null;
        for (int i = 0; i < bites.length; ++i) {
            String value;
            String[] crumbs = bites[i].split("=", 2);
            String name = crumbs.length > 0 ? crumbs[0].trim() : "";
            String string = value = crumbs.length > 1 ? crumbs[1].trim() : "";
            if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 1) {
                value = value.substring(1, value.length() - 1);
            }
            if (cookie == null) {
                cookie = new MutableNewCookie(name, value);
                continue;
            }
            String param = name.toLowerCase(Locale.ROOT);
            if (param.startsWith("comment")) {
                cookie.comment = value;
                continue;
            }
            if (param.startsWith("domain")) {
                cookie.domain = value;
                continue;
            }
            if (param.startsWith("max-age")) {
                cookie.maxAge = Integer.parseInt(value);
                continue;
            }
            if (param.startsWith("path")) {
                cookie.path = value;
                continue;
            }
            if (param.startsWith("secure")) {
                cookie.secure = true;
                continue;
            }
            if (param.startsWith("version")) {
                cookie.version = Integer.parseInt(value);
                continue;
            }
            if (param.startsWith("httponly")) {
                cookie.httpOnly = true;
                continue;
            }
            if (!param.startsWith("expires")) continue;
            try {
                cookie.expiry = HttpDateFormat.readDate(value + ", " + bites[++i]);
                continue;
            }
            catch (ParseException e) {
                LOGGER.log(Level.FINE, LocalizationMessages.ERROR_NEWCOOKIE_EXPIRES(value), e);
            }
        }
        return cookie.getImmutableNewCookie();
    }

    private CookiesParser() {
    }

    private static class MutableNewCookie {
        String name = null;
        String value = null;
        String path = null;
        String domain = null;
        int version = 1;
        String comment = null;
        int maxAge = -1;
        boolean secure = false;
        boolean httpOnly = false;
        Date expiry = null;

        public MutableNewCookie(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public NewCookie getImmutableNewCookie() {
            return new NewCookie(this.name, this.value, this.path, this.domain, this.version, this.comment, this.maxAge, this.expiry, this.secure, this.httpOnly);
        }
    }

    private static class MutableCookie {
        String name;
        String value;
        int version = 1;
        String path = null;
        String domain = null;

        public MutableCookie(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public Cookie getImmutableCookie() {
            return new Cookie(this.name, this.value, this.path, this.domain, this.version);
        }
    }
}

