/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access.intercept;

import org.springframework.util.Assert;

public class RequestKey {
    private final String url;
    private final String method;

    public RequestKey(String url) {
        this(url, null);
    }

    public RequestKey(String url, String method) {
        Assert.notNull((Object)url, (String)"url cannot be null");
        this.url = url;
        this.method = method;
    }

    String getUrl() {
        return this.url;
    }

    String getMethod() {
        return this.method;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RequestKey)) {
            return false;
        }
        RequestKey key = (RequestKey)obj;
        if (!this.url.equals(key.url)) {
            return false;
        }
        if (this.method == null) {
            return key.method == null;
        }
        return this.method.equals(key.method);
    }

    public int hashCode() {
        int result = this.url.hashCode();
        result = 31 * result + (this.method != null ? this.method.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.url.length() + 7);
        sb.append("[");
        if (this.method != null) {
            sb.append(this.method).append(",");
        }
        sb.append(this.url);
        sb.append("]");
        return sb.toString();
    }
}

