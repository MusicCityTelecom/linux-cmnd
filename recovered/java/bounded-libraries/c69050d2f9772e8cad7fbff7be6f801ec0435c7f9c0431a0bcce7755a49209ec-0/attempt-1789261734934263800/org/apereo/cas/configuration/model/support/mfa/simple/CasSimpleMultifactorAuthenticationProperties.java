/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.simple;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.mfa.simple.CasSimpleMultifactorAuthenticationBucket4jProperties;
import org.apereo.cas.configuration.model.support.mfa.simple.CasSimpleMultifactorAuthenticationTokenProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-simple-mfa")
@JsonFilter(value="CasSimpleMultifactorProperties")
public class CasSimpleMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-simple";
    private static final long serialVersionUID = -9211748853833491119L;
    private boolean trustedDeviceEnabled;
    @NestedConfigurationProperty
    private CasSimpleMultifactorAuthenticationTokenProperties token = new CasSimpleMultifactorAuthenticationTokenProperties();
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();
    @NestedConfigurationProperty
    private CasSimpleMultifactorAuthenticationBucket4jProperties bucket4j = new CasSimpleMultifactorAuthenticationBucket4jProperties();

    public CasSimpleMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationTokenProperties getToken() {
        return this.token;
    }

    @Generated
    public EmailProperties getMail() {
        return this.mail;
    }

    @Generated
    public SmsProperties getSms() {
        return this.sms;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationBucket4jProperties getBucket4j() {
        return this.bucket4j;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationProperties setToken(CasSimpleMultifactorAuthenticationTokenProperties token) {
        this.token = token;
        return this;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationProperties setSms(SmsProperties sms) {
        this.sms = sms;
        return this;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationProperties setBucket4j(CasSimpleMultifactorAuthenticationBucket4jProperties bucket4j) {
        this.bucket4j = bucket4j;
        return this;
    }
}

