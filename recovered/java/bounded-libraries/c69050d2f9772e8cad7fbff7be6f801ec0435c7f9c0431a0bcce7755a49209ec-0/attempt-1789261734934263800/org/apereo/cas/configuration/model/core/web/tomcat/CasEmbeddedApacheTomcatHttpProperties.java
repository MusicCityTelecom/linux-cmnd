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
public class CasEmbeddedApacheTomcatHttpProperties
implements Serializable {
    private static final long serialVersionUID = -8809922027350085888L;
    @RequiredProperty
    private boolean enabled;
    private int port = 8080;
    private int redirectPort;
    private String protocol = "org.apache.coyote.http11.Http11NioProtocol";
    private String scheme = "http";
    private boolean secure;
    private Map<String, String> attributes = new LinkedHashMap<String, String>(0);

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public int getRedirectPort() {
        return this.redirectPort;
    }

    @Generated
    public String getProtocol() {
        return this.protocol;
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
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setRedirectPort(int redirectPort) {
        this.redirectPort = redirectPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatHttpProperties setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}

