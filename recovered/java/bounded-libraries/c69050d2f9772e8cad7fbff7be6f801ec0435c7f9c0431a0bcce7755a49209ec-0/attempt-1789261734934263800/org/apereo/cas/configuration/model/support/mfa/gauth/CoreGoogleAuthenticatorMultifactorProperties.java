/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.gauth.GoogleAuthenticatorMultifactorScratchCodeProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-gauth")
@JsonFilter(value="CoreGoogleAuthenticatorMultifactorProperties")
public class CoreGoogleAuthenticatorMultifactorProperties
implements Serializable {
    private static final long serialVersionUID = -7451748853833491119L;
    @RequiredProperty
    private String issuer = "CASIssuer";
    @RequiredProperty
    private String label = "CASLabel";
    private int codeDigits = 6;
    private long timeStepSize = 30L;
    private int windowSize = 3;
    private boolean multipleDeviceRegistrationEnabled;
    private boolean trustedDeviceEnabled;
    @NestedConfigurationProperty
    private GoogleAuthenticatorMultifactorScratchCodeProperties scratchCodes = new GoogleAuthenticatorMultifactorScratchCodeProperties();

    @Generated
    public String getIssuer() {
        return this.issuer;
    }

    @Generated
    public String getLabel() {
        return this.label;
    }

    @Generated
    public int getCodeDigits() {
        return this.codeDigits;
    }

    @Generated
    public long getTimeStepSize() {
        return this.timeStepSize;
    }

    @Generated
    public int getWindowSize() {
        return this.windowSize;
    }

    @Generated
    public boolean isMultipleDeviceRegistrationEnabled() {
        return this.multipleDeviceRegistrationEnabled;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public GoogleAuthenticatorMultifactorScratchCodeProperties getScratchCodes() {
        return this.scratchCodes;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setLabel(String label) {
        this.label = label;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setCodeDigits(int codeDigits) {
        this.codeDigits = codeDigits;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setTimeStepSize(long timeStepSize) {
        this.timeStepSize = timeStepSize;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setWindowSize(int windowSize) {
        this.windowSize = windowSize;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setMultipleDeviceRegistrationEnabled(boolean multipleDeviceRegistrationEnabled) {
        this.multipleDeviceRegistrationEnabled = multipleDeviceRegistrationEnabled;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties setScratchCodes(GoogleAuthenticatorMultifactorScratchCodeProperties scratchCodes) {
        this.scratchCodes = scratchCodes;
        return this;
    }
}

