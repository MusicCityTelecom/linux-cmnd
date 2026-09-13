/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.captcha;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-captcha")
@JsonFilter(value="GoogleRecaptchaProperties")
public class GoogleRecaptchaProperties
implements Serializable {
    private static final long serialVersionUID = -8955074129123813915L;
    private RecaptchaVersions version = RecaptchaVersions.GOOGLE_RECAPTCHA_V2;
    @RequiredProperty
    private boolean enabled = true;
    @RequiredProperty
    private String siteKey;
    private String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
    @RequiredProperty
    private String secret;
    private boolean invisible;
    private String position = "bottomright";
    @RequiredProperty
    private double score = 0.5;
    private String activateForIpAddressPattern;

    @Generated
    public RecaptchaVersions getVersion() {
        return this.version;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getSiteKey() {
        return this.siteKey;
    }

    @Generated
    public String getVerifyUrl() {
        return this.verifyUrl;
    }

    @Generated
    public String getSecret() {
        return this.secret;
    }

    @Generated
    public boolean isInvisible() {
        return this.invisible;
    }

    @Generated
    public String getPosition() {
        return this.position;
    }

    @Generated
    public double getScore() {
        return this.score;
    }

    @Generated
    public String getActivateForIpAddressPattern() {
        return this.activateForIpAddressPattern;
    }

    @Generated
    public GoogleRecaptchaProperties setVersion(RecaptchaVersions version) {
        this.version = version;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setSiteKey(String siteKey) {
        this.siteKey = siteKey;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setVerifyUrl(String verifyUrl) {
        this.verifyUrl = verifyUrl;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setInvisible(boolean invisible) {
        this.invisible = invisible;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setPosition(String position) {
        this.position = position;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setScore(double score) {
        this.score = score;
        return this;
    }

    @Generated
    public GoogleRecaptchaProperties setActivateForIpAddressPattern(String activateForIpAddressPattern) {
        this.activateForIpAddressPattern = activateForIpAddressPattern;
        return this;
    }

    public static enum RecaptchaVersions {
        GOOGLE_RECAPTCHA_V2,
        GOOGLE_RECAPTCHA_V3,
        HCAPTCHA;

    }
}

