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
@JsonFilter(value="OAuthDeviceUserCodeProperties")
public class OAuthDeviceUserCodeProperties
implements Serializable {
    private static final long serialVersionUID = -1232081675586528350L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT1M";
    private int userCodeLength = 8;
    private String storageName = "oauthDeviceUserCodesCache";

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public int getUserCodeLength() {
        return this.userCodeLength;
    }

    @Generated
    public String getStorageName() {
        return this.storageName;
    }

    @Generated
    public OAuthDeviceUserCodeProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public OAuthDeviceUserCodeProperties setUserCodeLength(int userCodeLength) {
        this.userCodeLength = userCodeLength;
        return this;
    }

    @Generated
    public OAuthDeviceUserCodeProperties setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }
}

