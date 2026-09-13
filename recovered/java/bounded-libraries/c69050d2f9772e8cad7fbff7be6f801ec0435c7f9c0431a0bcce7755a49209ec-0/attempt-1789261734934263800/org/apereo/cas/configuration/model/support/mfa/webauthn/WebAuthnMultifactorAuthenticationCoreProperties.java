/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-webauthn")
@JsonFilter(value="WebAuthnMultifactorAuthenticationCoreProperties")
public class WebAuthnMultifactorAuthenticationCoreProperties
implements Serializable {
    private static final long serialVersionUID = -919073482703977440L;
    private String displayNameAttribute = "displayName";
    private boolean trustedDeviceEnabled;
    @NestedConfigurationProperty
    private SpringResourceProperties trustedDeviceMetadata = new SpringResourceProperties();
    @RequiredProperty
    private String applicationId;
    @RequiredProperty
    private String relyingPartyName;
    @RequiredProperty
    private String relyingPartyId;
    private boolean enabled = true;
    private long expireDevices = 30L;
    private TimeUnit expireDevicesTimeUnit = TimeUnit.DAYS;
    private String allowedOrigins;
    private boolean allowUntrustedAttestation;
    private boolean validateSignatureCounter = true;
    private String attestationConveyancePreference = "DIRECT";
    private boolean allowPrimaryAuthentication;

    public WebAuthnMultifactorAuthenticationCoreProperties() {
        this.trustedDeviceMetadata.setLocation((Resource)new ClassPathResource("webauthn-metadata.json"));
    }

    @Generated
    public String getDisplayNameAttribute() {
        return this.displayNameAttribute;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public SpringResourceProperties getTrustedDeviceMetadata() {
        return this.trustedDeviceMetadata;
    }

    @Generated
    public String getApplicationId() {
        return this.applicationId;
    }

    @Generated
    public String getRelyingPartyName() {
        return this.relyingPartyName;
    }

    @Generated
    public String getRelyingPartyId() {
        return this.relyingPartyId;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public long getExpireDevices() {
        return this.expireDevices;
    }

    @Generated
    public TimeUnit getExpireDevicesTimeUnit() {
        return this.expireDevicesTimeUnit;
    }

    @Generated
    public String getAllowedOrigins() {
        return this.allowedOrigins;
    }

    @Generated
    public boolean isAllowUntrustedAttestation() {
        return this.allowUntrustedAttestation;
    }

    @Generated
    public boolean isValidateSignatureCounter() {
        return this.validateSignatureCounter;
    }

    @Generated
    public String getAttestationConveyancePreference() {
        return this.attestationConveyancePreference;
    }

    @Generated
    public boolean isAllowPrimaryAuthentication() {
        return this.allowPrimaryAuthentication;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setDisplayNameAttribute(String displayNameAttribute) {
        this.displayNameAttribute = displayNameAttribute;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setTrustedDeviceMetadata(SpringResourceProperties trustedDeviceMetadata) {
        this.trustedDeviceMetadata = trustedDeviceMetadata;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setRelyingPartyName(String relyingPartyName) {
        this.relyingPartyName = relyingPartyName;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setRelyingPartyId(String relyingPartyId) {
        this.relyingPartyId = relyingPartyId;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setExpireDevices(long expireDevices) {
        this.expireDevices = expireDevices;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setExpireDevicesTimeUnit(TimeUnit expireDevicesTimeUnit) {
        this.expireDevicesTimeUnit = expireDevicesTimeUnit;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setAllowUntrustedAttestation(boolean allowUntrustedAttestation) {
        this.allowUntrustedAttestation = allowUntrustedAttestation;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setValidateSignatureCounter(boolean validateSignatureCounter) {
        this.validateSignatureCounter = validateSignatureCounter;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setAttestationConveyancePreference(String attestationConveyancePreference) {
        this.attestationConveyancePreference = attestationConveyancePreference;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties setAllowPrimaryAuthentication(boolean allowPrimaryAuthentication) {
        this.allowPrimaryAuthentication = allowPrimaryAuthentication;
        return this;
    }
}

