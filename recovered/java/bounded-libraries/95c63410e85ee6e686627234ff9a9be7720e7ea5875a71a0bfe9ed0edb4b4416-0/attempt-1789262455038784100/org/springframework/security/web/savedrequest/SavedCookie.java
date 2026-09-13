/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 */
package org.springframework.security.web.savedrequest;

import java.io.Serializable;
import javax.servlet.http.Cookie;

public class SavedCookie
implements Serializable {
    private static final long serialVersionUID = 570L;
    private final String name;
    private final String value;
    private final String comment;
    private final String domain;
    private final int maxAge;
    private final String path;
    private final boolean secure;
    private final int version;

    public SavedCookie(String name, String value, String comment, String domain, int maxAge, String path, boolean secure, int version) {
        this.name = name;
        this.value = value;
        this.comment = comment;
        this.domain = domain;
        this.maxAge = maxAge;
        this.path = path;
        this.secure = secure;
        this.version = version;
    }

    public SavedCookie(Cookie cookie) {
        this(cookie.getName(), cookie.getValue(), cookie.getComment(), cookie.getDomain(), cookie.getMaxAge(), cookie.getPath(), cookie.getSecure(), cookie.getVersion());
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getComment() {
        return this.comment;
    }

    public String getDomain() {
        return this.domain;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public int getVersion() {
        return this.version;
    }

    public Cookie getCookie() {
        Cookie cookie = new Cookie(this.getName(), this.getValue());
        if (this.getComment() != null) {
            cookie.setComment(this.getComment());
        }
        if (this.getDomain() != null) {
            cookie.setDomain(this.getDomain());
        }
        if (this.getPath() != null) {
            cookie.setPath(this.getPath());
        }
        cookie.setVersion(this.getVersion());
        cookie.setMaxAge(this.getMaxAge());
        cookie.setSecure(this.isSecure());
        return cookie;
    }
}

