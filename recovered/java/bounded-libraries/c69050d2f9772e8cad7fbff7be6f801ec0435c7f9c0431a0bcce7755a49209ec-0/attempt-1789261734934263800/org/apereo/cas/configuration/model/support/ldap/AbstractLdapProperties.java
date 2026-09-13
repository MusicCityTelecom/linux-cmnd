/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.LdapValidatorProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ldap-core")
@JsonFilter(value="AbstractLdapProperties")
public abstract class AbstractLdapProperties
implements Serializable {
    private static final long serialVersionUID = 2682743362616979324L;
    private String trustCertificates;
    @ExpressionLanguageCapable
    private String keystore;
    @ExpressionLanguageCapable
    private String keystorePassword;
    private String keystoreType;
    @ExpressionLanguageCapable
    private String trustStore;
    @ExpressionLanguageCapable
    private String trustStorePassword;
    private String trustStoreType;
    private boolean disablePooling;
    private int minPoolSize = 3;
    private int maxPoolSize = 10;
    private String poolPassivator = "BIND";
    private boolean validateOnCheckout = true;
    private boolean validatePeriodically = true;
    @DurationCapable
    private String validateTimeout = "PT5S";
    @DurationCapable
    private String validatePeriod = "PT5M";
    private boolean failFast = true;
    @DurationCapable
    private String idleTime = "PT10M";
    @DurationCapable
    private String prunePeriod = "PT2H";
    @DurationCapable
    private String blockWaitTime = "PT3S";
    private String connectionStrategy;
    @RequiredProperty
    private String ldapUrl;
    private boolean useStartTls;
    @DurationCapable
    private String connectTimeout = "PT5S";
    @DurationCapable
    private String responseTimeout = "PT5S";
    private boolean allowMultipleDns;
    @RequiredProperty
    private String bindDn;
    @RequiredProperty
    private String bindCredential;
    private String saslRealm;
    private String saslMechanism;
    private String saslAuthorizationId;
    private String saslSecurityStrength;
    private Boolean saslMutualAuth;
    private String saslQualityOfProtection;
    @NestedConfigurationProperty
    private LdapValidatorProperties validator = new LdapValidatorProperties();
    private LdapHostnameVerifierOptions hostnameVerifier = LdapHostnameVerifierOptions.DEFAULT;
    private String trustManager;
    private String name;
    private boolean allowMultipleEntries;
    private boolean followReferrals = true;
    private List<String> binaryAttributes = Stream.of("objectGUID", "objectSid").collect(Collectors.toList());

    @Generated
    public String getTrustCertificates() {
        return this.trustCertificates;
    }

    @Generated
    public String getKeystore() {
        return this.keystore;
    }

    @Generated
    public String getKeystorePassword() {
        return this.keystorePassword;
    }

    @Generated
    public String getKeystoreType() {
        return this.keystoreType;
    }

    @Generated
    public String getTrustStore() {
        return this.trustStore;
    }

    @Generated
    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    @Generated
    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    @Generated
    public boolean isDisablePooling() {
        return this.disablePooling;
    }

    @Generated
    public int getMinPoolSize() {
        return this.minPoolSize;
    }

    @Generated
    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    @Generated
    public String getPoolPassivator() {
        return this.poolPassivator;
    }

    @Generated
    public boolean isValidateOnCheckout() {
        return this.validateOnCheckout;
    }

    @Generated
    public boolean isValidatePeriodically() {
        return this.validatePeriodically;
    }

    @Generated
    public String getValidateTimeout() {
        return this.validateTimeout;
    }

    @Generated
    public String getValidatePeriod() {
        return this.validatePeriod;
    }

    @Generated
    public boolean isFailFast() {
        return this.failFast;
    }

    @Generated
    public String getIdleTime() {
        return this.idleTime;
    }

    @Generated
    public String getPrunePeriod() {
        return this.prunePeriod;
    }

    @Generated
    public String getBlockWaitTime() {
        return this.blockWaitTime;
    }

    @Generated
    public String getConnectionStrategy() {
        return this.connectionStrategy;
    }

    @Generated
    public String getLdapUrl() {
        return this.ldapUrl;
    }

    @Generated
    public boolean isUseStartTls() {
        return this.useStartTls;
    }

    @Generated
    public String getConnectTimeout() {
        return this.connectTimeout;
    }

    @Generated
    public String getResponseTimeout() {
        return this.responseTimeout;
    }

    @Generated
    public boolean isAllowMultipleDns() {
        return this.allowMultipleDns;
    }

    @Generated
    public String getBindDn() {
        return this.bindDn;
    }

    @Generated
    public String getBindCredential() {
        return this.bindCredential;
    }

    @Generated
    public String getSaslRealm() {
        return this.saslRealm;
    }

    @Generated
    public String getSaslMechanism() {
        return this.saslMechanism;
    }

    @Generated
    public String getSaslAuthorizationId() {
        return this.saslAuthorizationId;
    }

    @Generated
    public String getSaslSecurityStrength() {
        return this.saslSecurityStrength;
    }

    @Generated
    public Boolean getSaslMutualAuth() {
        return this.saslMutualAuth;
    }

    @Generated
    public String getSaslQualityOfProtection() {
        return this.saslQualityOfProtection;
    }

    @Generated
    public LdapValidatorProperties getValidator() {
        return this.validator;
    }

    @Generated
    public LdapHostnameVerifierOptions getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Generated
    public String getTrustManager() {
        return this.trustManager;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public boolean isAllowMultipleEntries() {
        return this.allowMultipleEntries;
    }

    @Generated
    public boolean isFollowReferrals() {
        return this.followReferrals;
    }

    @Generated
    public List<String> getBinaryAttributes() {
        return this.binaryAttributes;
    }

    @Generated
    public AbstractLdapProperties setTrustCertificates(String trustCertificates) {
        this.trustCertificates = trustCertificates;
        return this;
    }

    @Generated
    public AbstractLdapProperties setKeystore(String keystore) {
        this.keystore = keystore;
        return this;
    }

    @Generated
    public AbstractLdapProperties setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
        return this;
    }

    @Generated
    public AbstractLdapProperties setKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
        return this;
    }

    @Generated
    public AbstractLdapProperties setTrustStore(String trustStore) {
        this.trustStore = trustStore;
        return this;
    }

    @Generated
    public AbstractLdapProperties setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
        return this;
    }

    @Generated
    public AbstractLdapProperties setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
        return this;
    }

    @Generated
    public AbstractLdapProperties setDisablePooling(boolean disablePooling) {
        this.disablePooling = disablePooling;
        return this;
    }

    @Generated
    public AbstractLdapProperties setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
        return this;
    }

    @Generated
    public AbstractLdapProperties setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    @Generated
    public AbstractLdapProperties setPoolPassivator(String poolPassivator) {
        this.poolPassivator = poolPassivator;
        return this;
    }

    @Generated
    public AbstractLdapProperties setValidateOnCheckout(boolean validateOnCheckout) {
        this.validateOnCheckout = validateOnCheckout;
        return this;
    }

    @Generated
    public AbstractLdapProperties setValidatePeriodically(boolean validatePeriodically) {
        this.validatePeriodically = validatePeriodically;
        return this;
    }

    @Generated
    public AbstractLdapProperties setValidateTimeout(String validateTimeout) {
        this.validateTimeout = validateTimeout;
        return this;
    }

    @Generated
    public AbstractLdapProperties setValidatePeriod(String validatePeriod) {
        this.validatePeriod = validatePeriod;
        return this;
    }

    @Generated
    public AbstractLdapProperties setFailFast(boolean failFast) {
        this.failFast = failFast;
        return this;
    }

    @Generated
    public AbstractLdapProperties setIdleTime(String idleTime) {
        this.idleTime = idleTime;
        return this;
    }

    @Generated
    public AbstractLdapProperties setPrunePeriod(String prunePeriod) {
        this.prunePeriod = prunePeriod;
        return this;
    }

    @Generated
    public AbstractLdapProperties setBlockWaitTime(String blockWaitTime) {
        this.blockWaitTime = blockWaitTime;
        return this;
    }

    @Generated
    public AbstractLdapProperties setConnectionStrategy(String connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
        return this;
    }

    @Generated
    public AbstractLdapProperties setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
        return this;
    }

    @Generated
    public AbstractLdapProperties setUseStartTls(boolean useStartTls) {
        this.useStartTls = useStartTls;
        return this;
    }

    @Generated
    public AbstractLdapProperties setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Generated
    public AbstractLdapProperties setResponseTimeout(String responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    @Generated
    public AbstractLdapProperties setAllowMultipleDns(boolean allowMultipleDns) {
        this.allowMultipleDns = allowMultipleDns;
        return this;
    }

    @Generated
    public AbstractLdapProperties setBindDn(String bindDn) {
        this.bindDn = bindDn;
        return this;
    }

    @Generated
    public AbstractLdapProperties setBindCredential(String bindCredential) {
        this.bindCredential = bindCredential;
        return this;
    }

    @Generated
    public AbstractLdapProperties setSaslRealm(String saslRealm) {
        this.saslRealm = saslRealm;
        return this;
    }

    @Generated
    public AbstractLdapProperties setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
        return this;
    }

    @Generated
    public AbstractLdapProperties setSaslAuthorizationId(String saslAuthorizationId) {
        this.saslAuthorizationId = saslAuthorizationId;
        return this;
    }

    @Generated
    public AbstractLdapProperties setSaslSecurityStrength(String saslSecurityStrength) {
        this.saslSecurityStrength = saslSecurityStrength;
        return this;
    }

    @Generated
    public AbstractLdapProperties setSaslMutualAuth(Boolean saslMutualAuth) {
        this.saslMutualAuth = saslMutualAuth;
        return this;
    }

    @Generated
    public AbstractLdapProperties setSaslQualityOfProtection(String saslQualityOfProtection) {
        this.saslQualityOfProtection = saslQualityOfProtection;
        return this;
    }

    @Generated
    public AbstractLdapProperties setValidator(LdapValidatorProperties validator) {
        this.validator = validator;
        return this;
    }

    @Generated
    public AbstractLdapProperties setHostnameVerifier(LdapHostnameVerifierOptions hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    @Generated
    public AbstractLdapProperties setTrustManager(String trustManager) {
        this.trustManager = trustManager;
        return this;
    }

    @Generated
    public AbstractLdapProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public AbstractLdapProperties setAllowMultipleEntries(boolean allowMultipleEntries) {
        this.allowMultipleEntries = allowMultipleEntries;
        return this;
    }

    @Generated
    public AbstractLdapProperties setFollowReferrals(boolean followReferrals) {
        this.followReferrals = followReferrals;
        return this;
    }

    @Generated
    public AbstractLdapProperties setBinaryAttributes(List<String> binaryAttributes) {
        this.binaryAttributes = binaryAttributes;
        return this;
    }

    public static enum LdapTrustManagerOptions {
        DEFAULT,
        ANY;

    }

    public static enum LdapHostnameVerifierOptions {
        DEFAULT,
        ANY;

    }

    public static enum LdapConnectionStrategy {
        ACTIVE_PASSIVE,
        ROUND_ROBIN,
        RANDOM,
        DNS_SRV;

    }

    public static enum LdapConnectionPoolPassivator {
        NONE,
        BIND;

    }

    public static enum LdapType {
        GENERIC,
        AD,
        FreeIPA,
        EDirectory;

    }
}

