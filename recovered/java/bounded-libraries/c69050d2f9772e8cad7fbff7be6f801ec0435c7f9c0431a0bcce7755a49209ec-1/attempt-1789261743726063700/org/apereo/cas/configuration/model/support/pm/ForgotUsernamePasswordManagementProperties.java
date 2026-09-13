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
import org.apereo.cas.configuration.model.support.captcha.GoogleRecaptchaProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pm-webflow")
@JsonFilter(value="ForgotUsernamePasswordManagementProperties")
public class ForgotUsernamePasswordManagementProperties
implements Serializable {
    private static final long serialVersionUID = 4850199066765183587L;
    private boolean enabled = true;
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private GoogleRecaptchaProperties googleRecaptcha = new GoogleRecaptchaProperties();

    public ForgotUsernamePasswordManagementProperties() {
        this.mail.setAttributeName("mail");
        this.mail.setText("Your current username is: ${username}");
        this.mail.setSubject("Forgot Username");
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public EmailProperties getMail() {
        return this.mail;
    }

    @Generated
    public GoogleRecaptchaProperties getGoogleRecaptcha() {
        return this.googleRecaptcha;
    }

    @Generated
    public ForgotUsernamePasswordManagementProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public ForgotUsernamePasswordManagementProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public ForgotUsernamePasswordManagementProperties setGoogleRecaptcha(GoogleRecaptchaProperties googleRecaptcha) {
        this.googleRecaptcha = googleRecaptcha;
        return this;
    }
}

