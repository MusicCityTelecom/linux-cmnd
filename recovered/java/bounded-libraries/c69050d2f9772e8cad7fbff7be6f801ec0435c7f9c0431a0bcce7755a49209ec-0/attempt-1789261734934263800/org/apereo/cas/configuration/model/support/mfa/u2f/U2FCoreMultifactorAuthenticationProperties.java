/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f")
@JsonFilter(value="U2FCoreMultifactorAuthenticationProperties")
public class U2FCoreMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    private static final long serialVersionUID = 6152350313777066398L;
    private long expireRegistrations = 30L;
    private TimeUnit expireRegistrationsTimeUnit = TimeUnit.SECONDS;
    private long expireDevices = 30L;
    private TimeUnit expireDevicesTimeUnit = TimeUnit.DAYS;
    private boolean trustedDeviceEnabled;

    @Generated
    public long getExpireRegistrations() {
        return this.expireRegistrations;
    }

    @Generated
    public TimeUnit getExpireRegistrationsTimeUnit() {
        return this.expireRegistrationsTimeUnit;
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
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public U2FCoreMultifactorAuthenticationProperties setExpireRegistrations(long expireRegistrations) {
        this.expireRegistrations = expireRegistrations;
        return this;
    }

    @Generated
    public U2FCoreMultifactorAuthenticationProperties setExpireRegistrationsTimeUnit(TimeUnit expireRegistrationsTimeUnit) {
        this.expireRegistrationsTimeUnit = expireRegistrationsTimeUnit;
        return this;
    }

    @Generated
    public U2FCoreMultifactorAuthenticationProperties setExpireDevices(long expireDevices) {
        this.expireDevices = expireDevices;
        return this;
    }

    @Generated
    public U2FCoreMultifactorAuthenticationProperties setExpireDevicesTimeUnit(TimeUnit expireDevicesTimeUnit) {
        this.expireDevicesTimeUnit = expireDevicesTimeUnit;
        return this;
    }

    @Generated
    public U2FCoreMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }
}

