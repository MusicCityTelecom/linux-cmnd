/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.radius.RadiusClientProperties;
import org.apereo.cas.configuration.model.support.radius.RadiusServerProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-radius-mfa")
@JsonFilter(value="RadiusMultifactorProperties")
public class RadiusMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-radius";
    private static final long serialVersionUID = 7021301814775348087L;
    private boolean failoverOnException;
    private boolean failoverOnAuthenticationFailure;
    @NestedConfigurationProperty
    private RadiusServerProperties server = new RadiusServerProperties();
    @NestedConfigurationProperty
    private RadiusClientProperties client = new RadiusClientProperties();
    private long allowedAuthenticationAttempts = -1L;
    private boolean trustedDeviceEnabled;

    public RadiusMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public boolean isFailoverOnException() {
        return this.failoverOnException;
    }

    @Generated
    public boolean isFailoverOnAuthenticationFailure() {
        return this.failoverOnAuthenticationFailure;
    }

    @Generated
    public RadiusServerProperties getServer() {
        return this.server;
    }

    @Generated
    public RadiusClientProperties getClient() {
        return this.client;
    }

    @Generated
    public long getAllowedAuthenticationAttempts() {
        return this.allowedAuthenticationAttempts;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties setFailoverOnException(boolean failoverOnException) {
        this.failoverOnException = failoverOnException;
        return this;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties setFailoverOnAuthenticationFailure(boolean failoverOnAuthenticationFailure) {
        this.failoverOnAuthenticationFailure = failoverOnAuthenticationFailure;
        return this;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties setServer(RadiusServerProperties server) {
        this.server = server;
        return this;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties setClient(RadiusClientProperties client) {
        this.client = client;
        return this;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties setAllowedAuthenticationAttempts(long allowedAuthenticationAttempts) {
        this.allowedAuthenticationAttempts = allowedAuthenticationAttempts;
        return this;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }
}

