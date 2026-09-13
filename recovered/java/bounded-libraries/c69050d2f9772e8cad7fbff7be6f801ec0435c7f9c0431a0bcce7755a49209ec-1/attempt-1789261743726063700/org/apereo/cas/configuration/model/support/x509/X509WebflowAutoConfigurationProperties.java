/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.x509;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-x509-webflow")
@JsonFilter(value="X509WebflowAutoConfigurationProperties")
public class X509WebflowAutoConfigurationProperties
extends WebflowAutoConfigurationProperties {
    private static final long serialVersionUID = 2744305877450488111L;
    @RequiredProperty
    private int port;
    private String clientAuth = "want";

    public X509WebflowAutoConfigurationProperties() {
        this.setOrder(10);
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public String getClientAuth() {
        return this.clientAuth;
    }

    @Generated
    public X509WebflowAutoConfigurationProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public X509WebflowAutoConfigurationProperties setClientAuth(String clientAuth) {
        this.clientAuth = clientAuth;
        return this;
    }
}

