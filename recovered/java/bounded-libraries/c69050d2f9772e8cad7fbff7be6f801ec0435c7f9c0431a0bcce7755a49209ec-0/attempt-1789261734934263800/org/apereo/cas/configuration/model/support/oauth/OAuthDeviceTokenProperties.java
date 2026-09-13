/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth")
@JsonFilter(value="OAuthDeviceTokenProperties")
public class OAuthDeviceTokenProperties
implements Serializable {
    private static final long serialVersionUID = -6832081675586528350L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT5M";
    @DurationCapable
    private String refreshInterval = "PT15S";
    private String storageName = "oauthDeviceTokensCache";

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public String getRefreshInterval() {
        return this.refreshInterval;
    }

    @Generated
    public String getStorageName() {
        return this.storageName;
    }

    @Generated
    public OAuthDeviceTokenProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public OAuthDeviceTokenProperties setRefreshInterval(String refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    @Generated
    public OAuthDeviceTokenProperties setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }
}

