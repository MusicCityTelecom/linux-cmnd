/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.x509;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.x509.CnEdipiPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.x509.Rfc822EmailPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.x509.SerialNoDnPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.x509.SerialNoPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.x509.SubjectAltNamePrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.x509.SubjectDnPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.x509.X509LdapProperties;
import org.apereo.cas.configuration.model.support.x509.X509WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-x509-webflow")
public class X509Properties
implements Serializable {
    private static final long serialVersionUID = -9032744084671270366L;
    private static final int DEFAULT_MAXPATHLENGTH = 1;
    private static final boolean DEFAULT_MAX_PATHLENGTH_ALLOW_UNSPECIFIED = false;
    private static final boolean DEFAULT_CHECK_KEYUSAGE = false;
    private static final boolean DEFAULT_REQUIRE_KEYUSAGE = false;
    private static final String DEFAULT_CERT_HEADER_NAME = "ssl_client_cert";
    private int revocationPolicyThreshold = 172800;
    private boolean checkAll;
    private int refreshIntervalSeconds = 3600;
    private String principalDescriptor;
    private boolean throwOnFetchFailure;
    @RequiredProperty
    private PrincipalTypes principalType = PrincipalTypes.SUBJECT_DN;
    private String revocationChecker = "NONE";
    private String crlFetcher = "RESOURCE";
    private List<String> crlResources = new ArrayList<String>(0);
    private int cacheMaxElementsInMemory = 1000;
    private boolean cacheDiskOverflow;
    private String cacheDiskSize = "100MB";
    private boolean cacheEternal;
    private boolean mixedMode = true;
    private long cacheTimeToLiveSeconds = TimeUnit.HOURS.toSeconds(4L);
    private String crlResourceUnavailablePolicy = "DENY";
    private String crlResourceExpiredPolicy = "DENY";
    private String crlUnavailablePolicy = "DENY";
    private String crlExpiredPolicy = "DENY";
    @NestedConfigurationProperty
    private PersonDirectoryPrincipalResolverProperties principal = new PersonDirectoryPrincipalResolverProperties();
    @NestedConfigurationProperty
    private X509LdapProperties ldap = new X509LdapProperties();
    private String regExTrustedIssuerDnPattern;
    private int maxPathLength = 1;
    private boolean maxPathLengthAllowUnspecified = false;
    private boolean checkKeyUsage = false;
    private boolean requireKeyUsage = false;
    private String regExSubjectDnPattern = ".+";
    private String name;
    private int order = Integer.MAX_VALUE;
    private boolean extractCert;
    private String sslHeaderName = "ssl_client_cert";
    @NestedConfigurationProperty
    private SubjectDnPrincipalResolverProperties subjectDn = new SubjectDnPrincipalResolverProperties();
    @NestedConfigurationProperty
    private CnEdipiPrincipalResolverProperties cnEdipi = new CnEdipiPrincipalResolverProperties();
    @NestedConfigurationProperty
    private SubjectAltNamePrincipalResolverProperties subjectAltName = new SubjectAltNamePrincipalResolverProperties();
    @NestedConfigurationProperty
    private Rfc822EmailPrincipalResolverProperties rfc822Email = new Rfc822EmailPrincipalResolverProperties();
    @NestedConfigurationProperty
    private SerialNoDnPrincipalResolverProperties serialNoDn = new SerialNoDnPrincipalResolverProperties();
    @NestedConfigurationProperty
    private SerialNoPrincipalResolverProperties serialNo = new SerialNoPrincipalResolverProperties();
    @NestedConfigurationProperty
    private X509WebflowAutoConfigurationProperties webflow = new X509WebflowAutoConfigurationProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();

    @Generated
    public int getRevocationPolicyThreshold() {
        return this.revocationPolicyThreshold;
    }

    @Generated
    public boolean isCheckAll() {
        return this.checkAll;
    }

    @Generated
    public int getRefreshIntervalSeconds() {
        return this.refreshIntervalSeconds;
    }

    @Generated
    public String getPrincipalDescriptor() {
        return this.principalDescriptor;
    }

    @Generated
    public boolean isThrowOnFetchFailure() {
        return this.throwOnFetchFailure;
    }

    @Generated
    public PrincipalTypes getPrincipalType() {
        return this.principalType;
    }

    @Generated
    public String getRevocationChecker() {
        return this.revocationChecker;
    }

    @Generated
    public String getCrlFetcher() {
        return this.crlFetcher;
    }

    @Generated
    public List<String> getCrlResources() {
        return this.crlResources;
    }

    @Generated
    public int getCacheMaxElementsInMemory() {
        return this.cacheMaxElementsInMemory;
    }

    @Generated
    public boolean isCacheDiskOverflow() {
        return this.cacheDiskOverflow;
    }

    @Generated
    public String getCacheDiskSize() {
        return this.cacheDiskSize;
    }

    @Generated
    public boolean isCacheEternal() {
        return this.cacheEternal;
    }

    @Generated
    public boolean isMixedMode() {
        return this.mixedMode;
    }

    @Generated
    public long getCacheTimeToLiveSeconds() {
        return this.cacheTimeToLiveSeconds;
    }

    @Generated
    public String getCrlResourceUnavailablePolicy() {
        return this.crlResourceUnavailablePolicy;
    }

    @Generated
    public String getCrlResourceExpiredPolicy() {
        return this.crlResourceExpiredPolicy;
    }

    @Generated
    public String getCrlUnavailablePolicy() {
        return this.crlUnavailablePolicy;
    }

    @Generated
    public String getCrlExpiredPolicy() {
        return this.crlExpiredPolicy;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties getPrincipal() {
        return this.principal;
    }

    @Generated
    public X509LdapProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public String getRegExTrustedIssuerDnPattern() {
        return this.regExTrustedIssuerDnPattern;
    }

    @Generated
    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    @Generated
    public boolean isMaxPathLengthAllowUnspecified() {
        return this.maxPathLengthAllowUnspecified;
    }

    @Generated
    public boolean isCheckKeyUsage() {
        return this.checkKeyUsage;
    }

    @Generated
    public boolean isRequireKeyUsage() {
        return this.requireKeyUsage;
    }

    @Generated
    public String getRegExSubjectDnPattern() {
        return this.regExSubjectDnPattern;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean isExtractCert() {
        return this.extractCert;
    }

    @Generated
    public String getSslHeaderName() {
        return this.sslHeaderName;
    }

    @Generated
    public SubjectDnPrincipalResolverProperties getSubjectDn() {
        return this.subjectDn;
    }

    @Generated
    public CnEdipiPrincipalResolverProperties getCnEdipi() {
        return this.cnEdipi;
    }

    @Generated
    public SubjectAltNamePrincipalResolverProperties getSubjectAltName() {
        return this.subjectAltName;
    }

    @Generated
    public Rfc822EmailPrincipalResolverProperties getRfc822Email() {
        return this.rfc822Email;
    }

    @Generated
    public SerialNoDnPrincipalResolverProperties getSerialNoDn() {
        return this.serialNoDn;
    }

    @Generated
    public SerialNoPrincipalResolverProperties getSerialNo() {
        return this.serialNo;
    }

    @Generated
    public X509WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public X509Properties setRevocationPolicyThreshold(int revocationPolicyThreshold) {
        this.revocationPolicyThreshold = revocationPolicyThreshold;
        return this;
    }

    @Generated
    public X509Properties setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
        return this;
    }

    @Generated
    public X509Properties setRefreshIntervalSeconds(int refreshIntervalSeconds) {
        this.refreshIntervalSeconds = refreshIntervalSeconds;
        return this;
    }

    @Generated
    public X509Properties setPrincipalDescriptor(String principalDescriptor) {
        this.principalDescriptor = principalDescriptor;
        return this;
    }

    @Generated
    public X509Properties setThrowOnFetchFailure(boolean throwOnFetchFailure) {
        this.throwOnFetchFailure = throwOnFetchFailure;
        return this;
    }

    @Generated
    public X509Properties setPrincipalType(PrincipalTypes principalType) {
        this.principalType = principalType;
        return this;
    }

    @Generated
    public X509Properties setRevocationChecker(String revocationChecker) {
        this.revocationChecker = revocationChecker;
        return this;
    }

    @Generated
    public X509Properties setCrlFetcher(String crlFetcher) {
        this.crlFetcher = crlFetcher;
        return this;
    }

    @Generated
    public X509Properties setCrlResources(List<String> crlResources) {
        this.crlResources = crlResources;
        return this;
    }

    @Generated
    public X509Properties setCacheMaxElementsInMemory(int cacheMaxElementsInMemory) {
        this.cacheMaxElementsInMemory = cacheMaxElementsInMemory;
        return this;
    }

    @Generated
    public X509Properties setCacheDiskOverflow(boolean cacheDiskOverflow) {
        this.cacheDiskOverflow = cacheDiskOverflow;
        return this;
    }

    @Generated
    public X509Properties setCacheDiskSize(String cacheDiskSize) {
        this.cacheDiskSize = cacheDiskSize;
        return this;
    }

    @Generated
    public X509Properties setCacheEternal(boolean cacheEternal) {
        this.cacheEternal = cacheEternal;
        return this;
    }

    @Generated
    public X509Properties setMixedMode(boolean mixedMode) {
        this.mixedMode = mixedMode;
        return this;
    }

    @Generated
    public X509Properties setCacheTimeToLiveSeconds(long cacheTimeToLiveSeconds) {
        this.cacheTimeToLiveSeconds = cacheTimeToLiveSeconds;
        return this;
    }

    @Generated
    public X509Properties setCrlResourceUnavailablePolicy(String crlResourceUnavailablePolicy) {
        this.crlResourceUnavailablePolicy = crlResourceUnavailablePolicy;
        return this;
    }

    @Generated
    public X509Properties setCrlResourceExpiredPolicy(String crlResourceExpiredPolicy) {
        this.crlResourceExpiredPolicy = crlResourceExpiredPolicy;
        return this;
    }

    @Generated
    public X509Properties setCrlUnavailablePolicy(String crlUnavailablePolicy) {
        this.crlUnavailablePolicy = crlUnavailablePolicy;
        return this;
    }

    @Generated
    public X509Properties setCrlExpiredPolicy(String crlExpiredPolicy) {
        this.crlExpiredPolicy = crlExpiredPolicy;
        return this;
    }

    @Generated
    public X509Properties setPrincipal(PersonDirectoryPrincipalResolverProperties principal) {
        this.principal = principal;
        return this;
    }

    @Generated
    public X509Properties setLdap(X509LdapProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public X509Properties setRegExTrustedIssuerDnPattern(String regExTrustedIssuerDnPattern) {
        this.regExTrustedIssuerDnPattern = regExTrustedIssuerDnPattern;
        return this;
    }

    @Generated
    public X509Properties setMaxPathLength(int maxPathLength) {
        this.maxPathLength = maxPathLength;
        return this;
    }

    @Generated
    public X509Properties setMaxPathLengthAllowUnspecified(boolean maxPathLengthAllowUnspecified) {
        this.maxPathLengthAllowUnspecified = maxPathLengthAllowUnspecified;
        return this;
    }

    @Generated
    public X509Properties setCheckKeyUsage(boolean checkKeyUsage) {
        this.checkKeyUsage = checkKeyUsage;
        return this;
    }

    @Generated
    public X509Properties setRequireKeyUsage(boolean requireKeyUsage) {
        this.requireKeyUsage = requireKeyUsage;
        return this;
    }

    @Generated
    public X509Properties setRegExSubjectDnPattern(String regExSubjectDnPattern) {
        this.regExSubjectDnPattern = regExSubjectDnPattern;
        return this;
    }

    @Generated
    public X509Properties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public X509Properties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public X509Properties setExtractCert(boolean extractCert) {
        this.extractCert = extractCert;
        return this;
    }

    @Generated
    public X509Properties setSslHeaderName(String sslHeaderName) {
        this.sslHeaderName = sslHeaderName;
        return this;
    }

    @Generated
    public X509Properties setSubjectDn(SubjectDnPrincipalResolverProperties subjectDn) {
        this.subjectDn = subjectDn;
        return this;
    }

    @Generated
    public X509Properties setCnEdipi(CnEdipiPrincipalResolverProperties cnEdipi) {
        this.cnEdipi = cnEdipi;
        return this;
    }

    @Generated
    public X509Properties setSubjectAltName(SubjectAltNamePrincipalResolverProperties subjectAltName) {
        this.subjectAltName = subjectAltName;
        return this;
    }

    @Generated
    public X509Properties setRfc822Email(Rfc822EmailPrincipalResolverProperties rfc822Email) {
        this.rfc822Email = rfc822Email;
        return this;
    }

    @Generated
    public X509Properties setSerialNoDn(SerialNoDnPrincipalResolverProperties serialNoDn) {
        this.serialNoDn = serialNoDn;
        return this;
    }

    @Generated
    public X509Properties setSerialNo(SerialNoPrincipalResolverProperties serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    @Generated
    public X509Properties setWebflow(X509WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }

    @Generated
    public X509Properties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    public static enum PrincipalTypes {
        CN_EDIPI,
        RFC822_EMAIL,
        SERIAL_NO,
        SERIAL_NO_DN,
        SUBJECT,
        SUBJECT_ALT_NAME,
        SUBJECT_DN;

    }
}

