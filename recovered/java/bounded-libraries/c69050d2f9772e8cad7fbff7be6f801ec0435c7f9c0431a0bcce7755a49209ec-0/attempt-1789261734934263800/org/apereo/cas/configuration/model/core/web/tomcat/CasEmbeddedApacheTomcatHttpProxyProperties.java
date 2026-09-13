/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
public class CasEmbeddedApacheTomcatHttpProxyProperties
implements Serializable {
    private static final long serialVersionUID = 9129851352067677264L;
    @RequiredProperty
    private boolean enabled;
    private String scheme = "https";
    private boolean secure = true;
    private int redirectPort;
    private int proxyPort;
    private String protocol = "AJP/1.3";
    private String secret;
    private Map<String, String> attributes = new LinkedHashMap<String, String>();

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getScheme() {
        return this.scheme;
    }

    @Generated
    public boolean isSecure() {
        return this.secure;
    }

    @Generated
    public int getRedirectPort() {
        return this.redirectPort;
    }

    @Generated
    public int getProxyPort() {
        return this.proxyPort;
    }

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public String getSecret() {
        return this.secret;
    }

    @Generated
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setRedirectPort(int redirectPort) {
        this.redirectPort = redirectPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProxyProperties setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}

