/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.util.StringUtils;

public final class WebServerNamespace {
    public static final WebServerNamespace SERVER = new WebServerNamespace("server");
    public static final WebServerNamespace MANAGEMENT = new WebServerNamespace("management");
    private final String value;

    private WebServerNamespace(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static WebServerNamespace from(String value) {
        if (StringUtils.hasText((String)value)) {
            return new WebServerNamespace(value);
        }
        return SERVER;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        WebServerNamespace other = (WebServerNamespace)obj;
        return this.value.equals(other.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }
}

