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
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.CoreGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.CouchDbGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.DynamoDbGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.JpaGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.JsonGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.LdapGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.MongoDbGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.RedisGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.RestfulGoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.quartz.ScheduledJobProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-gauth")
@JsonFilter(value="GoogleAuthenticatorMultifactorProperties")
public class GoogleAuthenticatorMultifactorProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-gauth";
    private static final long serialVersionUID = -7401748853833491119L;
    @NestedConfigurationProperty
    private CoreGoogleAuthenticatorMultifactorProperties core = new CoreGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private MongoDbGoogleAuthenticatorMultifactorProperties mongo = new MongoDbGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private DynamoDbGoogleAuthenticatorMultifactorProperties dynamoDb = new DynamoDbGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private LdapGoogleAuthenticatorMultifactorProperties ldap = new LdapGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private JpaGoogleAuthenticatorMultifactorProperties jpa = new JpaGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private JsonGoogleAuthenticatorMultifactorProperties json = new JsonGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private RestfulGoogleAuthenticatorMultifactorProperties rest = new RestfulGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private CouchDbGoogleAuthenticatorMultifactorProperties couchDb = new CouchDbGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private RedisGoogleAuthenticatorMultifactorProperties redis = new RedisGoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private ScheduledJobProperties cleaner = new ScheduledJobProperties("PT1M", "PT1M");

    public GoogleAuthenticatorMultifactorProperties() {
        this.setId(DEFAULT_IDENTIFIER);
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public CoreGoogleAuthenticatorMultifactorProperties getCore() {
        return this.core;
    }

    @Generated
    public MongoDbGoogleAuthenticatorMultifactorProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public DynamoDbGoogleAuthenticatorMultifactorProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public LdapGoogleAuthenticatorMultifactorProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public JpaGoogleAuthenticatorMultifactorProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public JsonGoogleAuthenticatorMultifactorProperties getJson() {
        return this.json;
    }

    @Generated
    public RestfulGoogleAuthenticatorMultifactorProperties getRest() {
        return this.rest;
    }

    @Generated
    public CouchDbGoogleAuthenticatorMultifactorProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public RedisGoogleAuthenticatorMultifactorProperties getRedis() {
        return this.redis;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public ScheduledJobProperties getCleaner() {
        return this.cleaner;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setCore(CoreGoogleAuthenticatorMultifactorProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setMongo(MongoDbGoogleAuthenticatorMultifactorProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setDynamoDb(DynamoDbGoogleAuthenticatorMultifactorProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setLdap(LdapGoogleAuthenticatorMultifactorProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setJpa(JpaGoogleAuthenticatorMultifactorProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setJson(JsonGoogleAuthenticatorMultifactorProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setRest(RestfulGoogleAuthenticatorMultifactorProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setCouchDb(CouchDbGoogleAuthenticatorMultifactorProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setRedis(RedisGoogleAuthenticatorMultifactorProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties setCleaner(ScheduledJobProperties cleaner) {
        this.cleaner = cleaner;
        return this;
    }
}

