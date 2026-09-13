/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.dynamodb.DynamoDbTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.CouchDbTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.DeviceFingerprintProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.JpaTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.JsonTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.MongoDbTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.RedisTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.RestfulTrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.TrustedDevicesMultifactorCoreProperties;
import org.apereo.cas.configuration.model.support.quartz.ScheduledJobProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-trusted-mfa")
@JsonFilter(value="TrustedDevicesMultifactorProperties")
public class TrustedDevicesMultifactorProperties
implements Serializable {
    private static final long serialVersionUID = 1505013239016790473L;
    @NestedConfigurationProperty
    private TrustedDevicesMultifactorCoreProperties core = new TrustedDevicesMultifactorCoreProperties();
    @NestedConfigurationProperty
    private RestfulTrustedDevicesMultifactorProperties rest = new RestfulTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private JpaTrustedDevicesMultifactorProperties jpa = new JpaTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private JsonTrustedDevicesMultifactorProperties json = new JsonTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private DeviceFingerprintProperties deviceFingerprint = new DeviceFingerprintProperties();
    @NestedConfigurationProperty
    private ScheduledJobProperties cleaner = new ScheduledJobProperties("PT15S", "PT2M");
    @NestedConfigurationProperty
    private MongoDbTrustedDevicesMultifactorProperties mongo = new MongoDbTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private CouchDbTrustedDevicesMultifactorProperties couchDb = new CouchDbTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private DynamoDbTrustedDevicesMultifactorProperties dynamoDb = new DynamoDbTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private RedisTrustedDevicesMultifactorProperties redis = new RedisTrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public TrustedDevicesMultifactorProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public TrustedDevicesMultifactorCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public RestfulTrustedDevicesMultifactorProperties getRest() {
        return this.rest;
    }

    @Generated
    public JpaTrustedDevicesMultifactorProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public JsonTrustedDevicesMultifactorProperties getJson() {
        return this.json;
    }

    @Generated
    public DeviceFingerprintProperties getDeviceFingerprint() {
        return this.deviceFingerprint;
    }

    @Generated
    public ScheduledJobProperties getCleaner() {
        return this.cleaner;
    }

    @Generated
    public MongoDbTrustedDevicesMultifactorProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public CouchDbTrustedDevicesMultifactorProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public DynamoDbTrustedDevicesMultifactorProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public RedisTrustedDevicesMultifactorProperties getRedis() {
        return this.redis;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setCore(TrustedDevicesMultifactorCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setRest(RestfulTrustedDevicesMultifactorProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setJpa(JpaTrustedDevicesMultifactorProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setJson(JsonTrustedDevicesMultifactorProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setDeviceFingerprint(DeviceFingerprintProperties deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setCleaner(ScheduledJobProperties cleaner) {
        this.cleaner = cleaner;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setMongo(MongoDbTrustedDevicesMultifactorProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setCouchDb(CouchDbTrustedDevicesMultifactorProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setDynamoDb(DynamoDbTrustedDevicesMultifactorProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setRedis(RedisTrustedDevicesMultifactorProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public TrustedDevicesMultifactorProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

