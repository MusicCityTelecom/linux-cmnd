/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.util;

import org.springframework.util.Assert;

public class RedirectUrlBuilder {
    private String scheme;
    private String serverName;
    private int port;
    private String contextPath;
    private String servletPath;
    private String pathInfo;
    private String query;

    public void setScheme(String scheme) {
        Assert.isTrue(("http".equals(scheme) || "https".equals(scheme) ? 1 : 0) != 0, () -> "Unsupported scheme '" + scheme + "'");
        this.scheme = scheme;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        Assert.notNull((Object)this.scheme, (String)"scheme cannot be null");
        Assert.notNull((Object)this.serverName, (String)"serverName cannot be null");
        sb.append(this.scheme).append("://").append(this.serverName);
        if (this.port != (this.scheme.equals("http") ? 80 : 443)) {
            sb.append(":").append(this.port);
        }
        if (this.contextPath != null) {
            sb.append(this.contextPath);
        }
        if (this.servletPath != null) {
            sb.append(this.servletPath);
        }
        if (this.pathInfo != null) {
            sb.append(this.pathInfo);
        }
        if (this.query != null) {
            sb.append("?").append(this.query);
        }
        return sb.toString();
    }
}

