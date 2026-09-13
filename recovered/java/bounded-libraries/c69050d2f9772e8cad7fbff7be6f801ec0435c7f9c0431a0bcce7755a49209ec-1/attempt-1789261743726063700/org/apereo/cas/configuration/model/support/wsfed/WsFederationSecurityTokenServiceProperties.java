/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.wsfed;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.wsfed.WsFederationSecurityTokenServiceRealmProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@JsonFilter(value="WsFederationSecurityTokenServiceProperties")
@RequiresModule(name="cas-server-support-ws-sts")
public class WsFederationSecurityTokenServiceProperties
implements Serializable {
    private static final long serialVersionUID = -1155140161252595793L;
    private String subjectNameIdFormat = "unspecified";
    private String subjectNameQualifier = "http://cxf.apache.org/sts";
    private boolean signTokens = true;
    private boolean conditionsAcceptClientLifetime = true;
    private boolean conditionsFailLifetimeExceedance;
    @DurationCapable
    private String conditionsFutureTimeToLive = "PT60S";
    @DurationCapable
    private String conditionsLifetime = "PT30M";
    @DurationCapable
    private String conditionsMaxLifetime = "PT12H";
    private boolean encryptTokens = true;
    private String signingKeystoreFile;
    private String signingKeystorePassword;
    private String encryptionKeystoreFile;
    private String encryptionKeystorePassword;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private WsFederationSecurityTokenServiceRealmProperties realm = new WsFederationSecurityTokenServiceRealmProperties();
    private List<String> customClaims = new ArrayList<String>(0);

    public WsFederationSecurityTokenServiceProperties() {
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public String getSubjectNameIdFormat() {
        return this.subjectNameIdFormat;
    }

    @Generated
    public String getSubjectNameQualifier() {
        return this.subjectNameQualifier;
    }

    @Generated
    public boolean isSignTokens() {
        return this.signTokens;
    }

    @Generated
    public boolean isConditionsAcceptClientLifetime() {
        return this.conditionsAcceptClientLifetime;
    }

    @Generated
    public boolean isConditionsFailLifetimeExceedance() {
        return this.conditionsFailLifetimeExceedance;
    }

    @Generated
    public String getConditionsFutureTimeToLive() {
        return this.conditionsFutureTimeToLive;
    }

    @Generated
    public String getConditionsLifetime() {
        return this.conditionsLifetime;
    }

    @Generated
    public String getConditionsMaxLifetime() {
        return this.conditionsMaxLifetime;
    }

    @Generated
    public boolean isEncryptTokens() {
        return this.encryptTokens;
    }

    @Generated
    public String getSigningKeystoreFile() {
        return this.signingKeystoreFile;
    }

    @Generated
    public String getSigningKeystorePassword() {
        return this.signingKeystorePassword;
    }

    @Generated
    public String getEncryptionKeystoreFile() {
        return this.encryptionKeystoreFile;
    }

    @Generated
    public String getEncryptionKeystorePassword() {
        return this.encryptionKeystorePassword;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public WsFederationSecurityTokenServiceRealmProperties getRealm() {
        return this.realm;
    }

    @Generated
    public List<String> getCustomClaims() {
        return this.customClaims;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setSubjectNameIdFormat(String subjectNameIdFormat) {
        this.subjectNameIdFormat = subjectNameIdFormat;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setSubjectNameQualifier(String subjectNameQualifier) {
        this.subjectNameQualifier = subjectNameQualifier;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setSignTokens(boolean signTokens) {
        this.signTokens = signTokens;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setConditionsAcceptClientLifetime(boolean conditionsAcceptClientLifetime) {
        this.conditionsAcceptClientLifetime = conditionsAcceptClientLifetime;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setConditionsFailLifetimeExceedance(boolean conditionsFailLifetimeExceedance) {
        this.conditionsFailLifetimeExceedance = conditionsFailLifetimeExceedance;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setConditionsFutureTimeToLive(String conditionsFutureTimeToLive) {
        this.conditionsFutureTimeToLive = conditionsFutureTimeToLive;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setConditionsLifetime(String conditionsLifetime) {
        this.conditionsLifetime = conditionsLifetime;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setConditionsMaxLifetime(String conditionsMaxLifetime) {
        this.conditionsMaxLifetime = conditionsMaxLifetime;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setEncryptTokens(boolean encryptTokens) {
        this.encryptTokens = encryptTokens;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setSigningKeystoreFile(String signingKeystoreFile) {
        this.signingKeystoreFile = signingKeystoreFile;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setSigningKeystorePassword(String signingKeystorePassword) {
        this.signingKeystorePassword = signingKeystorePassword;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setEncryptionKeystoreFile(String encryptionKeystoreFile) {
        this.encryptionKeystoreFile = encryptionKeystoreFile;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setEncryptionKeystorePassword(String encryptionKeystorePassword) {
        this.encryptionKeystorePassword = encryptionKeystorePassword;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setRealm(WsFederationSecurityTokenServiceRealmProperties realm) {
        this.realm = realm;
        return this;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties setCustomClaims(List<String> customClaims) {
        this.customClaims = customClaims;
        return this;
    }
}

