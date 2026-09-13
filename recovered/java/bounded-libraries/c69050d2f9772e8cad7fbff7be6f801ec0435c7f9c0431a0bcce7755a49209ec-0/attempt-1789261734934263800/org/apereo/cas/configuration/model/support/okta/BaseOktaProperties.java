/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.okta;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-okta-authentication")
public abstract class BaseOktaProperties
implements Serializable {
    private static final long serialVersionUID = -23245764438426360L;
    private int order = Integer.MAX_VALUE;
    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;
    private int connectionTimeout = 5000;
    @RequiredProperty
    private String organizationUrl;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getProxyHost() {
        return this.proxyHost;
    }

    @Generated
    public int getProxyPort() {
        return this.proxyPort;
    }

    @Generated
    public String getProxyUsername() {
        return this.proxyUsername;
    }

    @Generated
    public String getProxyPassword() {
        return this.proxyPassword;
    }

    @Generated
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    @Generated
    public String getOrganizationUrl() {
        return this.organizationUrl;
    }

    @Generated
    public BaseOktaProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public BaseOktaProperties setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    @Generated
    public BaseOktaProperties setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    @Generated
    public BaseOktaProperties setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
        return this;
    }

    @Generated
    public BaseOktaProperties setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
        return this;
    }

    @Generated
    public BaseOktaProperties setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    @Generated
    public BaseOktaProperties setOrganizationUrl(String organizationUrl) {
        this.organizationUrl = organizationUrl;
        return this;
    }
}

