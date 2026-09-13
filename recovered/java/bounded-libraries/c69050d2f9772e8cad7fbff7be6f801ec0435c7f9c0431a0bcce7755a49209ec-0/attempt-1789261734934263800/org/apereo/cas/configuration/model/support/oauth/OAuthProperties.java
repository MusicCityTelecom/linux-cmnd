/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.oauth;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionOptionalSigningOptionalJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthAccessTokenProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthCodeProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthCoreProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthCsrfCookieProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthDeviceTokenProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthDeviceUserCodeProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthGrantsProperties;
import org.apereo.cas.configuration.model.support.oauth.OAuthRefreshTokenProperties;
import org.apereo.cas.configuration.model.support.replication.SessionReplicationProperties;
import org.apereo.cas.configuration.model.support.uma.UmaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oauth")
public class OAuthProperties
implements Serializable {
    private static final long serialVersionUID = 2677128037234123907L;
    @NestedConfigurationProperty
    private SessionReplicationProperties sessionReplication = new SessionReplicationProperties();
    @NestedConfigurationProperty
    private OAuthCsrfCookieProperties csrfCookie = new OAuthCsrfCookieProperties();
    @NestedConfigurationProperty
    private EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto = new EncryptionOptionalSigningOptionalJwtCryptographyProperties();
    @NestedConfigurationProperty
    private OAuthGrantsProperties grants = new OAuthGrantsProperties();
    @NestedConfigurationProperty
    private OAuthCodeProperties code = new OAuthCodeProperties();
    @NestedConfigurationProperty
    private OAuthAccessTokenProperties accessToken = new OAuthAccessTokenProperties();
    @NestedConfigurationProperty
    private OAuthRefreshTokenProperties refreshToken = new OAuthRefreshTokenProperties();
    @NestedConfigurationProperty
    private OAuthDeviceTokenProperties deviceToken = new OAuthDeviceTokenProperties();
    @NestedConfigurationProperty
    private OAuthDeviceUserCodeProperties deviceUserCode = new OAuthDeviceUserCodeProperties();
    @NestedConfigurationProperty
    private UmaProperties uma = new UmaProperties();
    @NestedConfigurationProperty
    private OAuthCoreProperties core = new OAuthCoreProperties();

    public OAuthProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public SessionReplicationProperties getSessionReplication() {
        return this.sessionReplication;
    }

    @Generated
    public OAuthCsrfCookieProperties getCsrfCookie() {
        return this.csrfCookie;
    }

    @Generated
    public EncryptionOptionalSigningOptionalJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public OAuthGrantsProperties getGrants() {
        return this.grants;
    }

    @Generated
    public OAuthCodeProperties getCode() {
        return this.code;
    }

    @Generated
    public OAuthAccessTokenProperties getAccessToken() {
        return this.accessToken;
    }

    @Generated
    public OAuthRefreshTokenProperties getRefreshToken() {
        return this.refreshToken;
    }

    @Generated
    public OAuthDeviceTokenProperties getDeviceToken() {
        return this.deviceToken;
    }

    @Generated
    public OAuthDeviceUserCodeProperties getDeviceUserCode() {
        return this.deviceUserCode;
    }

    @Generated
    public UmaProperties getUma() {
        return this.uma;
    }

    @Generated
    public OAuthCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public OAuthProperties setSessionReplication(SessionReplicationProperties sessionReplication) {
        this.sessionReplication = sessionReplication;
        return this;
    }

    @Generated
    public OAuthProperties setCsrfCookie(OAuthCsrfCookieProperties csrfCookie) {
        this.csrfCookie = csrfCookie;
        return this;
    }

    @Generated
    public OAuthProperties setCrypto(EncryptionOptionalSigningOptionalJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public OAuthProperties setGrants(OAuthGrantsProperties grants) {
        this.grants = grants;
        return this;
    }

    @Generated
    public OAuthProperties setCode(OAuthCodeProperties code) {
        this.code = code;
        return this;
    }

    @Generated
    public OAuthProperties setAccessToken(OAuthAccessTokenProperties accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Generated
    public OAuthProperties setRefreshToken(OAuthRefreshTokenProperties refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Generated
    public OAuthProperties setDeviceToken(OAuthDeviceTokenProperties deviceToken) {
        this.deviceToken = deviceToken;
        return this;
    }

    @Generated
    public OAuthProperties setDeviceUserCode(OAuthDeviceUserCodeProperties deviceUserCode) {
        this.deviceUserCode = deviceUserCode;
        return this;
    }

    @Generated
    public OAuthProperties setUma(UmaProperties uma) {
        this.uma = uma;
        return this;
    }

    @Generated
    public OAuthProperties setCore(OAuthCoreProperties core) {
        this.core = core;
        return this;
    }
}

