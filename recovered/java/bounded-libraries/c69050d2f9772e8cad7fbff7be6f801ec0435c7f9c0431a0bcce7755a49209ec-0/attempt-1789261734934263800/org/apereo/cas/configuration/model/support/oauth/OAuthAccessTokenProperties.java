/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.oauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionOptionalSigningOptionalJwtCryptographyProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oauth")
@JsonFilter(value="OAuthAccessTokenProperties")
public class OAuthAccessTokenProperties
implements Serializable {
    private static final long serialVersionUID = -6832081675586528350L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT8H";
    @DurationCapable
    private String timeToKillInSeconds = "PT2H";
    private boolean createAsJwt;
    private String storageName = "oauthAccessTokensCache";
    @NestedConfigurationProperty
    private EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto = new EncryptionOptionalSigningOptionalJwtCryptographyProperties();

    public OAuthAccessTokenProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public String getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public boolean isCreateAsJwt() {
        return this.createAsJwt;
    }

    @Generated
    public String getStorageName() {
        return this.storageName;
    }

    @Generated
    public EncryptionOptionalSigningOptionalJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public OAuthAccessTokenProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public OAuthAccessTokenProperties setTimeToKillInSeconds(String timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public OAuthAccessTokenProperties setCreateAsJwt(boolean createAsJwt) {
        this.createAsJwt = createAsJwt;
        return this;
    }

    @Generated
    public OAuthAccessTokenProperties setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }

    @Generated
    public OAuthAccessTokenProperties setCrypto(EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

