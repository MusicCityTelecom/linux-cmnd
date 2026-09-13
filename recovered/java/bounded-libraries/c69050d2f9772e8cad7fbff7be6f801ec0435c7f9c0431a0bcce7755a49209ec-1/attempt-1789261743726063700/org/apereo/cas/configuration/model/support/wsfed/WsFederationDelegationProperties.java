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
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.delegation.DelegationAutoRedirectTypes;
import org.apereo.cas.configuration.model.support.wsfed.GroovyWsFederationDelegationProperties;
import org.apereo.cas.configuration.model.support.wsfed.WsFederationDelegatedCookieProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-wsfederation-webflow")
@JsonFilter(value="WsFederationDelegationProperties")
public class WsFederationDelegationProperties
implements Serializable {
    private static final long serialVersionUID = 5743971334977239938L;
    @RequiredProperty
    private String identityAttribute = "upn";
    @RequiredProperty
    @ExpressionLanguageCapable
    private String identityProviderIdentifier = "http://adfs.example.org/adfs/services/trust";
    @RequiredProperty
    @ExpressionLanguageCapable
    private String identityProviderUrl = "https://adfs.example.org/adfs/ls/";
    @RequiredProperty
    private String signingCertificateResources = "classpath:adfs-signing.crt";
    @RequiredProperty
    @ExpressionLanguageCapable
    private String relyingPartyIdentifier = "urn:cas:localhost";
    @DurationCapable
    private String tolerance = "PT10S";
    private String attributesType = "WSFED";
    private boolean attributeResolverEnabled = true;
    private DelegationAutoRedirectTypes autoRedirectType = DelegationAutoRedirectTypes.SERVER;
    private String encryptionPrivateKey = "classpath:private.key";
    private String encryptionCertificate = "classpath:certificate.crt";
    private String encryptionPrivateKeyPassword = "NONE";
    @RequiredProperty
    private String id;
    @NestedConfigurationProperty
    private PersonDirectoryPrincipalResolverProperties principal = new PersonDirectoryPrincipalResolverProperties();
    private String name;
    private int order = Integer.MAX_VALUE;
    @NestedConfigurationProperty
    private GroovyWsFederationDelegationProperties attributeMutatorScript = new GroovyWsFederationDelegationProperties();
    @NestedConfigurationProperty
    private WsFederationDelegatedCookieProperties cookie = new WsFederationDelegatedCookieProperties();

    @Generated
    public String getIdentityAttribute() {
        return this.identityAttribute;
    }

    @Generated
    public String getIdentityProviderIdentifier() {
        return this.identityProviderIdentifier;
    }

    @Generated
    public String getIdentityProviderUrl() {
        return this.identityProviderUrl;
    }

    @Generated
    public String getSigningCertificateResources() {
        return this.signingCertificateResources;
    }

    @Generated
    public String getRelyingPartyIdentifier() {
        return this.relyingPartyIdentifier;
    }

    @Generated
    public String getTolerance() {
        return this.tolerance;
    }

    @Generated
    public String getAttributesType() {
        return this.attributesType;
    }

    @Generated
    public boolean isAttributeResolverEnabled() {
        return this.attributeResolverEnabled;
    }

    @Generated
    public DelegationAutoRedirectTypes getAutoRedirectType() {
        return this.autoRedirectType;
    }

    @Generated
    public String getEncryptionPrivateKey() {
        return this.encryptionPrivateKey;
    }

    @Generated
    public String getEncryptionCertificate() {
        return this.encryptionCertificate;
    }

    @Generated
    public String getEncryptionPrivateKeyPassword() {
        return this.encryptionPrivateKeyPassword;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties getPrincipal() {
        return this.principal;
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
    public GroovyWsFederationDelegationProperties getAttributeMutatorScript() {
        return this.attributeMutatorScript;
    }

    @Generated
    public WsFederationDelegatedCookieProperties getCookie() {
        return this.cookie;
    }

    @Generated
    public WsFederationDelegationProperties setIdentityAttribute(String identityAttribute) {
        this.identityAttribute = identityAttribute;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setIdentityProviderIdentifier(String identityProviderIdentifier) {
        this.identityProviderIdentifier = identityProviderIdentifier;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setIdentityProviderUrl(String identityProviderUrl) {
        this.identityProviderUrl = identityProviderUrl;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setSigningCertificateResources(String signingCertificateResources) {
        this.signingCertificateResources = signingCertificateResources;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setRelyingPartyIdentifier(String relyingPartyIdentifier) {
        this.relyingPartyIdentifier = relyingPartyIdentifier;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setTolerance(String tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setAttributesType(String attributesType) {
        this.attributesType = attributesType;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setAttributeResolverEnabled(boolean attributeResolverEnabled) {
        this.attributeResolverEnabled = attributeResolverEnabled;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setAutoRedirectType(DelegationAutoRedirectTypes autoRedirectType) {
        this.autoRedirectType = autoRedirectType;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setEncryptionPrivateKey(String encryptionPrivateKey) {
        this.encryptionPrivateKey = encryptionPrivateKey;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setEncryptionCertificate(String encryptionCertificate) {
        this.encryptionCertificate = encryptionCertificate;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setEncryptionPrivateKeyPassword(String encryptionPrivateKeyPassword) {
        this.encryptionPrivateKeyPassword = encryptionPrivateKeyPassword;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setPrincipal(PersonDirectoryPrincipalResolverProperties principal) {
        this.principal = principal;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setAttributeMutatorScript(GroovyWsFederationDelegationProperties attributeMutatorScript) {
        this.attributeMutatorScript = attributeMutatorScript;
        return this;
    }

    @Generated
    public WsFederationDelegationProperties setCookie(WsFederationDelegatedCookieProperties cookie) {
        this.cookie = cookie;
        return this;
    }
}

