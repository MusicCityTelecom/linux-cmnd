/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatAjpProperties")
public class CasEmbeddedApacheTomcatAjpProperties
implements Serializable {
    private static final long serialVersionUID = -32143821503580896L;
    private String protocol = "AJP/1.3";
    private int port = 8009;
    private boolean secure;
    private String secret;
    private boolean allowTrace;
    private String scheme = "http";
    @RequiredProperty
    private boolean enabled;
    @DurationCapable
    private String asyncTimeout = "PT5S";
    private boolean enableLookups;
    private int maxPostSize = 0x1400000;
    private int proxyPort = -1;
    private int redirectPort = -1;
    private Map<String, String> attributes = new LinkedHashMap<String, String>(0);

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public boolean isSecure() {
        return this.secure;
    }

    @Generated
    public String getSecret() {
        return this.secret;
    }

    @Generated
    public boolean isAllowTrace() {
        return this.allowTrace;
    }

    @Generated
    public String getScheme() {
        return this.scheme;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getAsyncTimeout() {
        return this.asyncTimeout;
    }

    @Generated
    public boolean isEnableLookups() {
        return this.enableLookups;
    }

    @Generated
    public int getMaxPostSize() {
        return this.maxPostSize;
    }

    @Generated
    public int getProxyPort() {
        return this.proxyPort;
    }

    @Generated
    public int getRedirectPort() {
        return this.redirectPort;
    }

    @Generated
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setAllowTrace(boolean allowTrace) {
        this.allowTrace = allowTrace;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setAsyncTimeout(String asyncTimeout) {
        this.asyncTimeout = asyncTimeout;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setEnableLookups(boolean enableLookups) {
        this.enableLookups = enableLookups;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setMaxPostSize(int maxPostSize) {
        this.maxPostSize = maxPostSize;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setRedirectPort(int redirectPort) {
        this.redirectPort = redirectPort;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatAjpProperties setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}

