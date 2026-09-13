/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FCoreMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FCouchDbMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FDynamoDbMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FGroovyMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FJpaMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FJsonMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FMongoDbMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FRedisMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FRestfulMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.quartz.ScheduledJobProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-u2f")
@JsonFilter(value="U2FMultifactorAuthenticationProperties")
public class U2FMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-u2f";
    private static final long serialVersionUID = 6151350313777066398L;
    @NestedConfigurationProperty
    private U2FCoreMultifactorAuthenticationProperties core = new U2FCoreMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FJpaMultifactorAuthenticationProperties jpa = new U2FJpaMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FMongoDbMultifactorAuthenticationProperties mongo = new U2FMongoDbMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FRedisMultifactorAuthenticationProperties redis = new U2FRedisMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FDynamoDbMultifactorAuthenticationProperties dynamoDb = new U2FDynamoDbMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FJsonMultifactorAuthenticationProperties json = new U2FJsonMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FGroovyMultifactorAuthenticationProperties groovy = new U2FGroovyMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FRestfulMultifactorAuthenticationProperties rest = new U2FRestfulMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private U2FCouchDbMultifactorAuthenticationProperties couchDb = new U2FCouchDbMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private ScheduledJobProperties cleaner = new ScheduledJobProperties("PT10S", "PT1M");
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public U2FMultifactorAuthenticationProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public U2FCoreMultifactorAuthenticationProperties getCore() {
        return this.core;
    }

    @Generated
    public U2FJpaMultifactorAuthenticationProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public U2FMongoDbMultifactorAuthenticationProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public U2FRedisMultifactorAuthenticationProperties getRedis() {
        return this.redis;
    }

    @Generated
    public U2FDynamoDbMultifactorAuthenticationProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public U2FJsonMultifactorAuthenticationProperties getJson() {
        return this.json;
    }

    @Generated
    public U2FGroovyMultifactorAuthenticationProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public U2FRestfulMultifactorAuthenticationProperties getRest() {
        return this.rest;
    }

    @Generated
    public U2FCouchDbMultifactorAuthenticationProperties getCouchDb() {
        return this.couchDb;
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
    public U2FMultifactorAuthenticationProperties setCore(U2FCoreMultifactorAuthenticationProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setJpa(U2FJpaMultifactorAuthenticationProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setMongo(U2FMongoDbMultifactorAuthenticationProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setRedis(U2FRedisMultifactorAuthenticationProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setDynamoDb(U2FDynamoDbMultifactorAuthenticationProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setJson(U2FJsonMultifactorAuthenticationProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setGroovy(U2FGroovyMultifactorAuthenticationProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setRest(U2FRestfulMultifactorAuthenticationProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setCouchDb(U2FCouchDbMultifactorAuthenticationProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setCleaner(ScheduledJobProperties cleaner) {
        this.cleaner = cleaner;
        return this;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

