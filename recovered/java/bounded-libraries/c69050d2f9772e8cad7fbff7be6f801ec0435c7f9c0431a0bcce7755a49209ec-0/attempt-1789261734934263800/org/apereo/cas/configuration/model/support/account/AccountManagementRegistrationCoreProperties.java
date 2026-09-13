/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.account;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-account-mgmt")
@JsonFilter(value="AccountManagementRegistrationCoreProperties")
public class AccountManagementRegistrationCoreProperties
implements Serializable {
    private static final long serialVersionUID = -4679683905941523034L;
    @NestedConfigurationProperty
    private SpringResourceProperties registrationProperties = new SpringResourceProperties();
    private boolean includeServerIpAddress = true;
    private boolean includeClientIpAddress = true;
    @DurationCapable
    private String expiration = "PT1M";
    @RequiredProperty
    private String passwordPolicyPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,10}";
    private int securityQuestionsCount = 2;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public AccountManagementRegistrationCoreProperties() {
        ClassPathResource resource = new ClassPathResource("account-registration-properties/registration-properties.json");
        this.registrationProperties.setLocation((Resource)resource);
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public SpringResourceProperties getRegistrationProperties() {
        return this.registrationProperties;
    }

    @Generated
    public boolean isIncludeServerIpAddress() {
        return this.includeServerIpAddress;
    }

    @Generated
    public boolean isIncludeClientIpAddress() {
        return this.includeClientIpAddress;
    }

    @Generated
    public String getExpiration() {
        return this.expiration;
    }

    @Generated
    public String getPasswordPolicyPattern() {
        return this.passwordPolicyPattern;
    }

    @Generated
    public int getSecurityQuestionsCount() {
        return this.securityQuestionsCount;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setRegistrationProperties(SpringResourceProperties registrationProperties) {
        this.registrationProperties = registrationProperties;
        return this;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setIncludeServerIpAddress(boolean includeServerIpAddress) {
        this.includeServerIpAddress = includeServerIpAddress;
        return this;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setIncludeClientIpAddress(boolean includeClientIpAddress) {
        this.includeClientIpAddress = includeClientIpAddress;
        return this;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setPasswordPolicyPattern(String passwordPolicyPattern) {
        this.passwordPolicyPattern = passwordPolicyPattern;
        return this;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setSecurityQuestionsCount(int securityQuestionsCount) {
        this.securityQuestionsCount = securityQuestionsCount;
        return this;
    }

    @Generated
    public AccountManagementRegistrationCoreProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

