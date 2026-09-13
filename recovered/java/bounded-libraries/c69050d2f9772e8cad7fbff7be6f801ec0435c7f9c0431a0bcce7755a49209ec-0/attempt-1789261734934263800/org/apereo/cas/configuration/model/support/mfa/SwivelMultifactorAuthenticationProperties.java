/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-swivel")
@JsonFilter(value="SwivelMultifactorProperties")
@Deprecated(since="6.6")
public class SwivelMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-swivel";
    private static final long serialVersionUID = -7409451053833491119L;
    @RequiredProperty
    private String swivelTuringImageUrl;
    @RequiredProperty
    private String swivelUrl;
    @RequiredProperty
    private String sharedSecret;
    private boolean ignoreSslErrors;
    private boolean trustedDeviceEnabled;

    public SwivelMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public String getSwivelTuringImageUrl() {
        return this.swivelTuringImageUrl;
    }

    @Generated
    public String getSwivelUrl() {
        return this.swivelUrl;
    }

    @Generated
    public String getSharedSecret() {
        return this.sharedSecret;
    }

    @Generated
    public boolean isIgnoreSslErrors() {
        return this.ignoreSslErrors;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public SwivelMultifactorAuthenticationProperties setSwivelTuringImageUrl(String swivelTuringImageUrl) {
        this.swivelTuringImageUrl = swivelTuringImageUrl;
        return this;
    }

    @Generated
    public SwivelMultifactorAuthenticationProperties setSwivelUrl(String swivelUrl) {
        this.swivelUrl = swivelUrl;
        return this;
    }

    @Generated
    public SwivelMultifactorAuthenticationProperties setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
        return this;
    }

    @Generated
    public SwivelMultifactorAuthenticationProperties setIgnoreSslErrors(boolean ignoreSslErrors) {
        this.ignoreSslErrors = ignoreSslErrors;
        return this;
    }

    @Generated
    public SwivelMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }
}

