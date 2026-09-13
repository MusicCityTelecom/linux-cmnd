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
@JsonFilter(value="OAuthRefreshTokenProperties")
public class OAuthRefreshTokenProperties
implements Serializable {
    private static final long serialVersionUID = -8328568272835831702L;
    @DurationCapable
    private String timeToKillInSeconds = "P14D";
    private String storageName = "oauthRefreshTokensCache";

    @Generated
    public String getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public String getStorageName() {
        return this.storageName;
    }

    @Generated
    public OAuthRefreshTokenProperties setTimeToKillInSeconds(String timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public OAuthRefreshTokenProperties setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }
}

