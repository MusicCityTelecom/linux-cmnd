/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.account;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.model.support.account.AccountManagementRegistrationCoreProperties;
import org.apereo.cas.configuration.model.support.account.provision.AccountManagementRegistrationProvisioningProperties;
import org.apereo.cas.configuration.model.support.captcha.GoogleRecaptchaProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-account-mgmt")
@JsonFilter(value="AccountManagementRegistrationProperties")
public class AccountManagementRegistrationProperties
implements Serializable {
    private static final long serialVersionUID = -4679683905941523034L;
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties webflow = new WebflowAutoConfigurationProperties().setOrder(100);
    @NestedConfigurationProperty
    private GoogleRecaptchaProperties googleRecaptcha = new GoogleRecaptchaProperties();
    @NestedConfigurationProperty
    private AccountManagementRegistrationCoreProperties core = new AccountManagementRegistrationCoreProperties();
    @NestedConfigurationProperty
    private AccountManagementRegistrationProvisioningProperties provisioning = new AccountManagementRegistrationProvisioningProperties();

    public AccountManagementRegistrationProperties() {
        this.mail.setAttributeName("mail");
        this.mail.setText("Activate your account registration via this link: ${url}");
        this.sms.setText("Activate your account registration via this link: ${url}");
        this.mail.setSubject("Account Registration");
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
    public WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public GoogleRecaptchaProperties getGoogleRecaptcha() {
        return this.googleRecaptcha;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public AccountManagementRegistrationProvisioningProperties getProvisioning() {
        return this.provisioning;
    }

    @Generated
    public AccountManagementRegistrationProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProperties setSms(SmsProperties sms) {
        this.sms = sms;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProperties setWebflow(WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProperties setGoogleRecaptcha(GoogleRecaptchaProperties googleRecaptcha) {
        this.googleRecaptcha = googleRecaptcha;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProperties setCore(AccountManagementRegistrationCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProperties setProvisioning(AccountManagementRegistrationProvisioningProperties provisioning) {
        this.provisioning = provisioning;
        return this;
    }
}

