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
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth")
@JsonFilter(value="OAuthCodeProperties")
public class OAuthCodeProperties
implements Serializable {
    private static final long serialVersionUID = -7687928082301669359L;
    private int numberOfUses = 1;
    private long timeToKillInSeconds = 30L;
    private String storageName = "oauthCodesCache";
    private boolean removeRelatedAccessTokens;

    @Generated
    public int getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public String getStorageName() {
        return this.storageName;
    }

    @Generated
    public boolean isRemoveRelatedAccessTokens() {
        return this.removeRelatedAccessTokens;
    }

    @Generated
    public OAuthCodeProperties setNumberOfUses(int numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    @Generated
    public OAuthCodeProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public OAuthCodeProperties setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }

    @Generated
    public OAuthCodeProperties setRemoveRelatedAccessTokens(boolean removeRelatedAccessTokens) {
        this.removeRelatedAccessTokens = removeRelatedAccessTokens;
        return this;
    }
}

