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
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-authy")
@JsonFilter(value="AuthyMultifactorProperties")
public class AuthyMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties
implements CasFeatureModule {
    public static final String DEFAULT_IDENTIFIER = "mfa-authy";
    private static final long serialVersionUID = -3746749663459157641L;
    @RequiredProperty
    private String apiKey;
    @RequiredProperty
    private String apiUrl;
    @RequiredProperty
    private String phoneAttribute = "phone";
    @RequiredProperty
    private String mailAttribute = "mail";
    private String countryCode = "1";
    private boolean forceVerification = true;
    private boolean trustedDeviceEnabled;

    public AuthyMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public String getApiKey() {
        return this.apiKey;
    }

    @Generated
    public String getApiUrl() {
        return this.apiUrl;
    }

    @Generated
    public String getPhoneAttribute() {
        return this.phoneAttribute;
    }

    @Generated
    public String getMailAttribute() {
        return this.mailAttribute;
    }

    @Generated
    public String getCountryCode() {
        return this.countryCode;
    }

    @Generated
    public boolean isForceVerification() {
        return this.forceVerification;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setPhoneAttribute(String phoneAttribute) {
        this.phoneAttribute = phoneAttribute;
        return this;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setMailAttribute(String mailAttribute) {
        this.mailAttribute = mailAttribute;
        return this;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setForceVerification(boolean forceVerification) {
        this.forceVerification = forceVerification;
        return this;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }
}

