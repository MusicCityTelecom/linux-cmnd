/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnDynamoDbMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnJpaMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnJsonMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnLdapMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnMongoDbMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnMultifactorAuthenticationCoreProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnRedisMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnRestfulMultifactorProperties;
import org.apereo.cas.configuration.model.support.quartz.ScheduledJobProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-webauthn")
@JsonFilter(value="WebAuthnMultifactorProperties")
public class WebAuthnMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-webauthn";
    private static final long serialVersionUID = 4211350313777066398L;
    @NestedConfigurationProperty
    private WebAuthnMultifactorAuthenticationCoreProperties core = new WebAuthnMultifactorAuthenticationCoreProperties();
    @NestedConfigurationProperty
    private WebAuthnJsonMultifactorProperties json = new WebAuthnJsonMultifactorProperties();
    @NestedConfigurationProperty
    private WebAuthnMongoDbMultifactorProperties mongo = new WebAuthnMongoDbMultifactorProperties();
    @NestedConfigurationProperty
    private WebAuthnRedisMultifactorProperties redis = new WebAuthnRedisMultifactorProperties();
    @NestedConfigurationProperty
    private WebAuthnDynamoDbMultifactorProperties dynamoDb = new WebAuthnDynamoDbMultifactorProperties();
    @NestedConfigurationProperty
    private WebAuthnLdapMultifactorProperties ldap = new WebAuthnLdapMultifactorProperties();
    @NestedConfigurationProperty
    private WebAuthnJpaMultifactorProperties jpa = new WebAuthnJpaMultifactorProperties();
    @NestedConfigurationProperty
    private WebAuthnRestfulMultifactorProperties rest = new WebAuthnRestfulMultifactorProperties();
    @NestedConfigurationProperty
    private ScheduledJobProperties cleaner = new ScheduledJobProperties("PT10S", "PT1M");
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public WebAuthnMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public WebAuthnMultifactorAuthenticationCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public WebAuthnJsonMultifactorProperties getJson() {
        return this.json;
    }

    @Generated
    public WebAuthnMongoDbMultifactorProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public WebAuthnRedisMultifactorProperties getRedis() {
        return this.redis;
    }

    @Generated
    public WebAuthnDynamoDbMultifactorProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public WebAuthnLdapMultifactorProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public WebAuthnJpaMultifactorProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public WebAuthnRestfulMultifactorProperties getRest() {
        return this.rest;
    }

    @Generated
    public ScheduledJobProperties getCleaner() {
        return this.cleaner;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setCore(WebAuthnMultifactorAuthenticationCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setJson(WebAuthnJsonMultifactorProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setMongo(WebAuthnMongoDbMultifactorProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setRedis(WebAuthnRedisMultifactorProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setDynamoDb(WebAuthnDynamoDbMultifactorProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setLdap(WebAuthnLdapMultifactorProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setJpa(WebAuthnJpaMultifactorProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setRest(WebAuthnRestfulMultifactorProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setCleaner(ScheduledJobProperties cleaner) {
        this.cleaner = cleaner;
        return this;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

