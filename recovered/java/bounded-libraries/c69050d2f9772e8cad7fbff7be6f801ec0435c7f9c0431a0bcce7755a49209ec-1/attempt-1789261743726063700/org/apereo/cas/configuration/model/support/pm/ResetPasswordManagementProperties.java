/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pm-webflow")
@JsonFilter(value="ResetPasswordManagementProperties")
public class ResetPasswordManagementProperties
implements Serializable {
    private static final long serialVersionUID = 3453970349530670459L;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();
    private boolean securityQuestionsEnabled = true;
    private boolean includeServerIpAddress = true;
    private boolean includeClientIpAddress = true;
    @DurationCapable
    private String expiration = "PT1M";
    private int numberOfUses = -1;

    public ResetPasswordManagementProperties() {
        this.mail.setAttributeName("mail");
        this.mail.setText("Reset your password via this link: ${url}");
        this.sms.setText("Reset your password via this link: ${url}");
        this.mail.setSubject("Password Reset");
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
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
    public boolean isSecurityQuestionsEnabled() {
        return this.securityQuestionsEnabled;
    }

    @Generated
    public boolean isIncludeServerIpAddress() {
        return this.includeServerIpAddress;
    }

    @Generated
    public boolean isIncludeClientIpAddress() {
        return this.includeClientIpAddress;
    }

    @Generated
    public String getExpiration() {
        return this.expiration;
    }

    @Generated
    public int getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public ResetPasswordManagementProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setSms(SmsProperties sms) {
        this.sms = sms;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setSecurityQuestionsEnabled(boolean securityQuestionsEnabled) {
        this.securityQuestionsEnabled = securityQuestionsEnabled;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setIncludeServerIpAddress(boolean includeServerIpAddress) {
        this.includeServerIpAddress = includeServerIpAddress;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setIncludeClientIpAddress(boolean includeClientIpAddress) {
        this.includeClientIpAddress = includeClientIpAddress;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    @Generated
    public ResetPasswordManagementProperties setNumberOfUses(int numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }
}

