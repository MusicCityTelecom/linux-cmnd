/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.cookie;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-cookie", automated=true)
@JsonFilter(value="CookieProperties")
public class CookieProperties
implements Serializable {
    private static final long serialVersionUID = 6804770601645126835L;
    private String name;
    private String path = "";
    private String domain = "";
    private String comment = "CAS Cookie";
    private boolean secure = true;
    private boolean httpOnly = true;
    private int maxAge = -1;
    private String sameSitePolicy = "";

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getPath() {
        return this.path;
    }

    @Generated
    public String getDomain() {
        return this.domain;
    }

    @Generated
    public String getComment() {
        return this.comment;
    }

    @Generated
    public boolean isSecure() {
        return this.secure;
    }

    @Generated
    public boolean isHttpOnly() {
        return this.httpOnly;
    }

    @Generated
    public int getMaxAge() {
        return this.maxAge;
    }

    @Generated
    public String getSameSitePolicy() {
        return this.sameSitePolicy;
    }

    @Generated
    public CookieProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public CookieProperties setPath(String path) {
        this.path = path;
        return this;
    }

    @Generated
    public CookieProperties setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Generated
    public CookieProperties setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Generated
    public CookieProperties setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    @Generated
    public CookieProperties setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    @Generated
    public CookieProperties setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    @Generated
    public CookieProperties setSameSitePolicy(String sameSitePolicy) {
        this.sameSitePolicy = sameSitePolicy;
        return this;
    }
}

