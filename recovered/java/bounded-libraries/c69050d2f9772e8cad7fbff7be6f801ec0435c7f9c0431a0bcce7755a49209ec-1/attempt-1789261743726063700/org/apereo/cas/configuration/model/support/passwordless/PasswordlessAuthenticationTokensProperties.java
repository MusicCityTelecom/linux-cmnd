/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.passwordless;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.passwordless.token.PasswordlessAuthenticationJpaTokensProperties;
import org.apereo.cas.configuration.model.support.passwordless.token.PasswordlessAuthenticationRestTokensProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-passwordless")
@JsonFilter(value="PasswordlessAuthenticationTokensProperties")
public class PasswordlessAuthenticationTokensProperties
implements Serializable {
    private static final long serialVersionUID = 8371063350377031703L;
    private int expireInSeconds = 180;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationRestTokensProperties rest = new PasswordlessAuthenticationRestTokensProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationJpaTokensProperties jpa = new PasswordlessAuthenticationJpaTokensProperties();
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();

    public PasswordlessAuthenticationTokensProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public int getExpireInSeconds() {
        return this.expireInSeconds;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public PasswordlessAuthenticationRestTokensProperties getRest() {
        return this.rest;
    }

    @Generated
    public PasswordlessAuthenticationJpaTokensProperties getJpa() {
        return this.jpa;
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
    public PasswordlessAuthenticationTokensProperties setExpireInSeconds(int expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationTokensProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationTokensProperties setRest(PasswordlessAuthenticationRestTokensProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationTokensProperties setJpa(PasswordlessAuthenticationJpaTokensProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationTokensProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationTokensProperties setSms(SmsProperties sms) {
        this.sms = sms;
        return this;
    }
}

