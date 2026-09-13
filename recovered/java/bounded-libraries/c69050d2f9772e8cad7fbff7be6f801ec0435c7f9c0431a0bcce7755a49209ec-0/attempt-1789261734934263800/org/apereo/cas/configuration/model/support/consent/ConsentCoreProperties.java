/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.consent;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-consent-webflow")
@JsonFilter(value="ConsentCoreProperties")
public class ConsentCoreProperties
implements Serializable {
    private static final long serialVersionUID = 5211308051524438384L;
    private boolean enabled = true;
    private boolean active = true;
    private long reminder = 30L;
    private ChronoUnit reminderTimeUnit = ChronoUnit.DAYS;
    private List<String> excludedAttributes = Stream.of("eduPersonTargetedID").collect(Collectors.toList());
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties webflow = new WebflowAutoConfigurationProperties().setOrder(100);

    public ConsentCoreProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isActive() {
        return this.active;
    }

    @Generated
    public long getReminder() {
        return this.reminder;
    }

    @Generated
    public ChronoUnit getReminderTimeUnit() {
        return this.reminderTimeUnit;
    }

    @Generated
    public List<String> getExcludedAttributes() {
        return this.excludedAttributes;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public ConsentCoreProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public ConsentCoreProperties setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Generated
    public ConsentCoreProperties setReminder(long reminder) {
        this.reminder = reminder;
        return this;
    }

    @Generated
    public ConsentCoreProperties setReminderTimeUnit(ChronoUnit reminderTimeUnit) {
        this.reminderTimeUnit = reminderTimeUnit;
        return this;
    }

    @Generated
    public ConsentCoreProperties setExcludedAttributes(List<String> excludedAttributes) {
        this.excludedAttributes = excludedAttributes;
        return this;
    }

    @Generated
    public ConsentCoreProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public ConsentCoreProperties setWebflow(WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }
}

