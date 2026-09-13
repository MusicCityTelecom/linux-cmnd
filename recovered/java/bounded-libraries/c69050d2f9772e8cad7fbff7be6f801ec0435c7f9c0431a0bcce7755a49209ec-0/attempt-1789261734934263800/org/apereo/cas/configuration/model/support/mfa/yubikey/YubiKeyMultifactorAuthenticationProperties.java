/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  javax.annotation.Nonnull
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.mfa.yubikey;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyCouchDbMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyDynamoDbMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyJpaMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyMongoDbMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyRedisMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyRestfulMultifactorProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-yubikey")
@JsonFilter(value="YubiKeyMultifactorProperties")
public class YubiKeyMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-yubikey";
    private static final long serialVersionUID = 9138057706201201089L;
    @RequiredProperty
    private Integer clientId = 0;
    @RequiredProperty
    @Nonnull
    private String secretKey = "";
    private boolean multipleDeviceRegistrationEnabled;
    private transient Resource jsonFile;
    private Map<String, String> allowedDevices = new LinkedHashMap<String, String>(1);
    private List<String> apiUrls = new ArrayList<String>(0);
    private boolean trustedDeviceEnabled;
    @NestedConfigurationProperty
    private YubiKeyCouchDbMultifactorProperties couchDb = new YubiKeyCouchDbMultifactorProperties();
    @NestedConfigurationProperty
    private YubiKeyJpaMultifactorProperties jpa = new YubiKeyJpaMultifactorProperties();
    @NestedConfigurationProperty
    private YubiKeyMongoDbMultifactorProperties mongo = new YubiKeyMongoDbMultifactorProperties();
    @NestedConfigurationProperty
    private YubiKeyRedisMultifactorProperties redis = new YubiKeyRedisMultifactorProperties();
    @NestedConfigurationProperty
    private YubiKeyDynamoDbMultifactorProperties dynamoDb = new YubiKeyDynamoDbMultifactorProperties();
    @NestedConfigurationProperty
    private YubiKeyRestfulMultifactorProperties rest = new YubiKeyRestfulMultifactorProperties();
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public YubiKeyMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public Integer getClientId() {
        return this.clientId;
    }

    @Nonnull
    @Generated
    public String getSecretKey() {
        return this.secretKey;
    }

    @Generated
    public boolean isMultipleDeviceRegistrationEnabled() {
        return this.multipleDeviceRegistrationEnabled;
    }

    @Generated
    public Resource getJsonFile() {
        return this.jsonFile;
    }

    @Generated
    public Map<String, String> getAllowedDevices() {
        return this.allowedDevices;
    }

    @Generated
    public List<String> getApiUrls() {
        return this.apiUrls;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public YubiKeyCouchDbMultifactorProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public YubiKeyJpaMultifactorProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public YubiKeyMongoDbMultifactorProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public YubiKeyRedisMultifactorProperties getRedis() {
        return this.redis;
    }

    @Generated
    public YubiKeyDynamoDbMultifactorProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public YubiKeyRestfulMultifactorProperties getRest() {
        return this.rest;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setSecretKey(@Nonnull String secretKey) {
        if (secretKey == null) {
            throw new NullPointerException("secretKey is marked non-null but is null");
        }
        this.secretKey = secretKey;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setMultipleDeviceRegistrationEnabled(boolean multipleDeviceRegistrationEnabled) {
        this.multipleDeviceRegistrationEnabled = multipleDeviceRegistrationEnabled;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setJsonFile(Resource jsonFile) {
        this.jsonFile = jsonFile;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setAllowedDevices(Map<String, String> allowedDevices) {
        this.allowedDevices = allowedDevices;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setApiUrls(List<String> apiUrls) {
        this.apiUrls = apiUrls;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setCouchDb(YubiKeyCouchDbMultifactorProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setJpa(YubiKeyJpaMultifactorProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setMongo(YubiKeyMongoDbMultifactorProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setRedis(YubiKeyRedisMultifactorProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setDynamoDb(YubiKeyDynamoDbMultifactorProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setRest(YubiKeyRestfulMultifactorProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

