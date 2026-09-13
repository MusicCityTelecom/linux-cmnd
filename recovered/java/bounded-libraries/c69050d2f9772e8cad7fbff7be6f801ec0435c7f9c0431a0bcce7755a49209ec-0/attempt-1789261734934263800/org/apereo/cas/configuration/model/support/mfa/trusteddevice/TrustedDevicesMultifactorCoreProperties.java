/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa")
@JsonFilter(value="TrustedDevicesMultifactorCoreProperties")
public class TrustedDevicesMultifactorCoreProperties
implements Serializable {
    private static final long serialVersionUID = 1585013239016790473L;
    private String authenticationContextAttribute = "isFromTrustedMultifactorAuthentication";
    private boolean deviceRegistrationEnabled = true;
    private boolean autoAssignDeviceName;
    private TrustedDevicesKeyGeneratorTypes keyGeneratorType = TrustedDevicesKeyGeneratorTypes.DEFAULT;

    @Generated
    public String getAuthenticationContextAttribute() {
        return this.authenticationContextAttribute;
    }

    @Generated
    public boolean isDeviceRegistrationEnabled() {
        return this.deviceRegistrationEnabled;
    }

    @Generated
    public boolean isAutoAssignDeviceName() {
        return this.autoAssignDeviceName;
    }

    @Generated
    public TrustedDevicesKeyGeneratorTypes getKeyGeneratorType() {
        return this.keyGeneratorType;
    }

    @Generated
    public TrustedDevicesMultifactorCoreProperties setAuthenticationContextAttribute(String authenticationContextAttribute) {
        this.authenticationContextAttribute = authenticationContextAttribute;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorCoreProperties setDeviceRegistrationEnabled(boolean deviceRegistrationEnabled) {
        this.deviceRegistrationEnabled = deviceRegistrationEnabled;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorCoreProperties setAutoAssignDeviceName(boolean autoAssignDeviceName) {
        this.autoAssignDeviceName = autoAssignDeviceName;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorCoreProperties setKeyGeneratorType(TrustedDevicesKeyGeneratorTypes keyGeneratorType) {
        this.keyGeneratorType = keyGeneratorType;
        return this;
    }

    public static enum TrustedDevicesKeyGeneratorTypes {
        DEFAULT,
        LEGACY;

    }
}

