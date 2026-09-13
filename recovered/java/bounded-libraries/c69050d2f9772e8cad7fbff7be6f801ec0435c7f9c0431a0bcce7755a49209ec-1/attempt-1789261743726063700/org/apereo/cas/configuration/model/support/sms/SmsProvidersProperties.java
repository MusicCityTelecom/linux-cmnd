/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.sms;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.sms.AmazonSnsProperties;
import org.apereo.cas.configuration.model.support.sms.ClickatellProperties;
import org.apereo.cas.configuration.model.support.sms.GroovySmsProperties;
import org.apereo.cas.configuration.model.support.sms.NexmoProperties;
import org.apereo.cas.configuration.model.support.sms.RestfulSmsProperties;
import org.apereo.cas.configuration.model.support.sms.SmsModeProperties;
import org.apereo.cas.configuration.model.support.sms.TextMagicProperties;
import org.apereo.cas.configuration.model.support.sms.TwilioProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-util", automated=true)
public class SmsProvidersProperties
implements Serializable {
    private static final long serialVersionUID = -3713886839517507306L;
    @NestedConfigurationProperty
    private TwilioProperties twilio = new TwilioProperties();
    @NestedConfigurationProperty
    private TextMagicProperties textMagic = new TextMagicProperties();
    @NestedConfigurationProperty
    private ClickatellProperties clickatell = new ClickatellProperties();
    @NestedConfigurationProperty
    private SmsModeProperties smsMode = new SmsModeProperties();
    @NestedConfigurationProperty
    private AmazonSnsProperties sns = new AmazonSnsProperties();
    @NestedConfigurationProperty
    private NexmoProperties nexmo = new NexmoProperties();
    @NestedConfigurationProperty
    private GroovySmsProperties groovy = new GroovySmsProperties();
    @NestedConfigurationProperty
    private RestfulSmsProperties rest = new RestfulSmsProperties();

    @Generated
    public TwilioProperties getTwilio() {
        return this.twilio;
    }

    @Generated
    public TextMagicProperties getTextMagic() {
        return this.textMagic;
    }

    @Generated
    public ClickatellProperties getClickatell() {
        return this.clickatell;
    }

    @Generated
    public SmsModeProperties getSmsMode() {
        return this.smsMode;
    }

    @Generated
    public AmazonSnsProperties getSns() {
        return this.sns;
    }

    @Generated
    public NexmoProperties getNexmo() {
        return this.nexmo;
    }

    @Generated
    public GroovySmsProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public RestfulSmsProperties getRest() {
        return this.rest;
    }

    @Generated
    public SmsProvidersProperties setTwilio(TwilioProperties twilio) {
        this.twilio = twilio;
        return this;
    }

    @Generated
    public SmsProvidersProperties setTextMagic(TextMagicProperties textMagic) {
        this.textMagic = textMagic;
        return this;
    }

    @Generated
    public SmsProvidersProperties setClickatell(ClickatellProperties clickatell) {
        this.clickatell = clickatell;
        return this;
    }

    @Generated
    public SmsProvidersProperties setSmsMode(SmsModeProperties smsMode) {
        this.smsMode = smsMode;
        return this;
    }

    @Generated
    public SmsProvidersProperties setSns(AmazonSnsProperties sns) {
        this.sns = sns;
        return this;
    }

    @Generated
    public SmsProvidersProperties setNexmo(NexmoProperties nexmo) {
        this.nexmo = nexmo;
        return this;
    }

    @Generated
    public SmsProvidersProperties setGroovy(GroovySmsProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public SmsProvidersProperties setRest(RestfulSmsProperties rest) {
        this.rest = rest;
        return this;
    }
}

