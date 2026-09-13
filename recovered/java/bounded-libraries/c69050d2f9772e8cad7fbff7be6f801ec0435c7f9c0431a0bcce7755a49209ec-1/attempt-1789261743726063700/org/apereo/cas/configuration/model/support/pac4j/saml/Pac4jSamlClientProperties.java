/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.configuration.model.support.pac4j.saml;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jBaseClientProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.apereo.cas.util.model.TriStateBoolean;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jSamlClientProperties")
public class Pac4jSamlClientProperties
extends Pac4jBaseClientProperties
implements CasFeatureModule {
    private static final long serialVersionUID = -862819796533384951L;
    private String destinationBinding = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
    private String logoutRequestBinding = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
    @RequiredProperty
    private String keystorePassword;
    @RequiredProperty
    private String privateKeyPassword;
    @RequiredProperty
    private String keystorePath = Beans.getTempFilePath("samlSpKeystore", ".jks");
    @RequiredProperty
    private String identityProviderMetadataPath;
    @DurationCapable
    private String maximumAuthenticationLifetime = "PT3600S";
    @DurationCapable
    private String acceptedSkew = "PT300S";
    private List<String> mappedAttributes = new ArrayList<String>(0);
    @RequiredProperty
    private String serviceProviderEntityId = "https://apereo.org/cas/samlsp";
    @RequiredProperty
    private String serviceProviderMetadataPath = Beans.getTempFilePath("samlSpMetadata", ".xml");
    private boolean forceAuth;
    private boolean passive;
    private List<String> authnContextClassRef = new ArrayList<String>(0);
    private String authnContextComparisonType = "exact";
    private boolean forceKeystoreGeneration;
    private int certificateExpirationDays = 7300;
    private String certificateSignatureAlg = "SHA1WithRSA";
    private String keystoreAlias;
    private String certificateNameToAppend;
    private String nameIdPolicyFormat;
    private TriStateBoolean nameIdPolicyAllowCreate = TriStateBoolean.TRUE;
    private boolean wantsAssertionsSigned;
    private boolean wantsResponsesSigned;
    private boolean allSignatureValidationDisabled;
    private int attributeConsumingServiceIndex;
    private int assertionConsumerServiceIndex = -1;
    private boolean useNameQualifier = true;
    private String principalIdAttribute;
    private boolean signServiceProviderMetadata;
    private boolean signAuthnRequest;
    private boolean signServiceProviderLogoutRequest;
    private List<ServiceProviderRequestedAttribute> requestedAttributes = new ArrayList<ServiceProviderRequestedAttribute>(0);
    private List<String> blockedSignatureSigningAlgorithms = new ArrayList<String>(0);
    private List<String> signatureAlgorithms = new ArrayList<String>(0);
    private List<String> signatureReferenceDigestMethods = new ArrayList<String>(0);
    private String signatureCanonicalizationAlgorithm;
    private String providerName;
    private String messageStoreFactory = "org.pac4j.saml.store.EmptyStoreFactory";
    private String saml2AttributeConverter;
    private String metadataSignerStrategy = "default";

    @Generated
    public String getDestinationBinding() {
        return this.destinationBinding;
    }

    @Generated
    public String getLogoutRequestBinding() {
        return this.logoutRequestBinding;
    }

    @Generated
    public String getKeystorePassword() {
        return this.keystorePassword;
    }

    @Generated
    public String getPrivateKeyPassword() {
        return this.privateKeyPassword;
    }

    @Generated
    public String getKeystorePath() {
        return this.keystorePath;
    }

    @Generated
    public String getIdentityProviderMetadataPath() {
        return this.identityProviderMetadataPath;
    }

    @Generated
    public String getMaximumAuthenticationLifetime() {
        return this.maximumAuthenticationLifetime;
    }

    @Generated
    public String getAcceptedSkew() {
        return this.acceptedSkew;
    }

    @Generated
    public List<String> getMappedAttributes() {
        return this.mappedAttributes;
    }

    @Generated
    public String getServiceProviderEntityId() {
        return this.serviceProviderEntityId;
    }

    @Generated
    public String getServiceProviderMetadataPath() {
        return this.serviceProviderMetadataPath;
    }

    @Generated
    public boolean isForceAuth() {
        return this.forceAuth;
    }

    @Generated
    public boolean isPassive() {
        return this.passive;
    }

    @Generated
    public List<String> getAuthnContextClassRef() {
        return this.authnContextClassRef;
    }

    @Generated
    public String getAuthnContextComparisonType() {
        return this.authnContextComparisonType;
    }

    @Generated
    public boolean isForceKeystoreGeneration() {
        return this.forceKeystoreGeneration;
    }

    @Generated
    public int getCertificateExpirationDays() {
        return this.certificateExpirationDays;
    }

    @Generated
    public String getCertificateSignatureAlg() {
        return this.certificateSignatureAlg;
    }

    @Generated
    public String getKeystoreAlias() {
        return this.keystoreAlias;
    }

    @Generated
    public String getCertificateNameToAppend() {
        return this.certificateNameToAppend;
    }

    @Generated
    public String getNameIdPolicyFormat() {
        return this.nameIdPolicyFormat;
    }

    @Generated
    public TriStateBoolean getNameIdPolicyAllowCreate() {
        return this.nameIdPolicyAllowCreate;
    }

    @Generated
    public boolean isWantsAssertionsSigned() {
        return this.wantsAssertionsSigned;
    }

    @Generated
    public boolean isWantsResponsesSigned() {
        return this.wantsResponsesSigned;
    }

    @Generated
    public boolean isAllSignatureValidationDisabled() {
        return this.allSignatureValidationDisabled;
    }

    @Generated
    public int getAttributeConsumingServiceIndex() {
        return this.attributeConsumingServiceIndex;
    }

    @Generated
    public int getAssertionConsumerServiceIndex() {
        return this.assertionConsumerServiceIndex;
    }

    @Generated
    public boolean isUseNameQualifier() {
        return this.useNameQualifier;
    }

    @Generated
    public String getPrincipalIdAttribute() {
        return this.principalIdAttribute;
    }

    @Generated
    public boolean isSignServiceProviderMetadata() {
        return this.signServiceProviderMetadata;
    }

    @Generated
    public boolean isSignAuthnRequest() {
        return this.signAuthnRequest;
    }

    @Generated
    public boolean isSignServiceProviderLogoutRequest() {
        return this.signServiceProviderLogoutRequest;
    }

    @Generated
    public List<ServiceProviderRequestedAttribute> getRequestedAttributes() {
        return this.requestedAttributes;
    }

    @Generated
    public List<String> getBlockedSignatureSigningAlgorithms() {
        return this.blockedSignatureSigningAlgorithms;
    }

    @Generated
    public List<String> getSignatureAlgorithms() {
        return this.signatureAlgorithms;
    }

    @Generated
    public List<String> getSignatureReferenceDigestMethods() {
        return this.signatureReferenceDigestMethods;
    }

    @Generated
    public String getSignatureCanonicalizationAlgorithm() {
        return this.signatureCanonicalizationAlgorithm;
    }

    @Generated
    public String getProviderName() {
        return this.providerName;
    }

    @Generated
    public String getMessageStoreFactory() {
        return this.messageStoreFactory;
    }

    @Generated
    public String getSaml2AttributeConverter() {
        return this.saml2AttributeConverter;
    }

    @Generated
    public String getMetadataSignerStrategy() {
        return this.metadataSignerStrategy;
    }

    @Generated
    public Pac4jSamlClientProperties setDestinationBinding(String destinationBinding) {
        this.destinationBinding = destinationBinding;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setLogoutRequestBinding(String logoutRequestBinding) {
        this.logoutRequestBinding = logoutRequestBinding;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setPrivateKeyPassword(String privateKeyPassword) {
        this.privateKeyPassword = privateKeyPassword;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setIdentityProviderMetadataPath(String identityProviderMetadataPath) {
        this.identityProviderMetadataPath = identityProviderMetadataPath;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setMaximumAuthenticationLifetime(String maximumAuthenticationLifetime) {
        this.maximumAuthenticationLifetime = maximumAuthenticationLifetime;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setAcceptedSkew(String acceptedSkew) {
        this.acceptedSkew = acceptedSkew;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setMappedAttributes(List<String> mappedAttributes) {
        this.mappedAttributes = mappedAttributes;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setServiceProviderEntityId(String serviceProviderEntityId) {
        this.serviceProviderEntityId = serviceProviderEntityId;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setServiceProviderMetadataPath(String serviceProviderMetadataPath) {
        this.serviceProviderMetadataPath = serviceProviderMetadataPath;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setForceAuth(boolean forceAuth) {
        this.forceAuth = forceAuth;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setPassive(boolean passive) {
        this.passive = passive;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setAuthnContextClassRef(List<String> authnContextClassRef) {
        this.authnContextClassRef = authnContextClassRef;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setAuthnContextComparisonType(String authnContextComparisonType) {
        this.authnContextComparisonType = authnContextComparisonType;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setForceKeystoreGeneration(boolean forceKeystoreGeneration) {
        this.forceKeystoreGeneration = forceKeystoreGeneration;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setCertificateExpirationDays(int certificateExpirationDays) {
        this.certificateExpirationDays = certificateExpirationDays;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setCertificateSignatureAlg(String certificateSignatureAlg) {
        this.certificateSignatureAlg = certificateSignatureAlg;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setKeystoreAlias(String keystoreAlias) {
        this.keystoreAlias = keystoreAlias;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setCertificateNameToAppend(String certificateNameToAppend) {
        this.certificateNameToAppend = certificateNameToAppend;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setNameIdPolicyFormat(String nameIdPolicyFormat) {
        this.nameIdPolicyFormat = nameIdPolicyFormat;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setNameIdPolicyAllowCreate(TriStateBoolean nameIdPolicyAllowCreate) {
        this.nameIdPolicyAllowCreate = nameIdPolicyAllowCreate;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setWantsAssertionsSigned(boolean wantsAssertionsSigned) {
        this.wantsAssertionsSigned = wantsAssertionsSigned;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setWantsResponsesSigned(boolean wantsResponsesSigned) {
        this.wantsResponsesSigned = wantsResponsesSigned;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setAllSignatureValidationDisabled(boolean allSignatureValidationDisabled) {
        this.allSignatureValidationDisabled = allSignatureValidationDisabled;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setAttributeConsumingServiceIndex(int attributeConsumingServiceIndex) {
        this.attributeConsumingServiceIndex = attributeConsumingServiceIndex;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setAssertionConsumerServiceIndex(int assertionConsumerServiceIndex) {
        this.assertionConsumerServiceIndex = assertionConsumerServiceIndex;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setUseNameQualifier(boolean useNameQualifier) {
        this.useNameQualifier = useNameQualifier;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setPrincipalIdAttribute(String principalIdAttribute) {
        this.principalIdAttribute = principalIdAttribute;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSignServiceProviderMetadata(boolean signServiceProviderMetadata) {
        this.signServiceProviderMetadata = signServiceProviderMetadata;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSignAuthnRequest(boolean signAuthnRequest) {
        this.signAuthnRequest = signAuthnRequest;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSignServiceProviderLogoutRequest(boolean signServiceProviderLogoutRequest) {
        this.signServiceProviderLogoutRequest = signServiceProviderLogoutRequest;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setRequestedAttributes(List<ServiceProviderRequestedAttribute> requestedAttributes) {
        this.requestedAttributes = requestedAttributes;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setBlockedSignatureSigningAlgorithms(List<String> blockedSignatureSigningAlgorithms) {
        this.blockedSignatureSigningAlgorithms = blockedSignatureSigningAlgorithms;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSignatureAlgorithms(List<String> signatureAlgorithms) {
        this.signatureAlgorithms = signatureAlgorithms;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSignatureReferenceDigestMethods(List<String> signatureReferenceDigestMethods) {
        this.signatureReferenceDigestMethods = signatureReferenceDigestMethods;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSignatureCanonicalizationAlgorithm(String signatureCanonicalizationAlgorithm) {
        this.signatureCanonicalizationAlgorithm = signatureCanonicalizationAlgorithm;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setMessageStoreFactory(String messageStoreFactory) {
        this.messageStoreFactory = messageStoreFactory;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setSaml2AttributeConverter(String saml2AttributeConverter) {
        this.saml2AttributeConverter = saml2AttributeConverter;
        return this;
    }

    @Generated
    public Pac4jSamlClientProperties setMetadataSignerStrategy(String metadataSignerStrategy) {
        this.metadataSignerStrategy = metadataSignerStrategy;
        return this;
    }

    @RequiresModule(name="cas-server-support-pac4j-webflow")
    public static class ServiceProviderRequestedAttribute
    implements Serializable {
        private static final long serialVersionUID = -862819796533384951L;
        private String name;
        private String friendlyName;
        private String nameFormat = "urn:oasis:names:tc:SAML:2.0:attrname-format:uri";
        private boolean required;

        @Generated
        public String getName() {
            return this.name;
        }

        @Generated
        public String getFriendlyName() {
            return this.friendlyName;
        }

        @Generated
        public String getNameFormat() {
            return this.nameFormat;
        }

        @Generated
        public boolean isRequired() {
            return this.required;
        }

        @Generated
        public ServiceProviderRequestedAttribute setName(String name) {
            this.name = name;
            return this;
        }

        @Generated
        public ServiceProviderRequestedAttribute setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
            return this;
        }

        @Generated
        public ServiceProviderRequestedAttribute setNameFormat(String nameFormat) {
            this.nameFormat = nameFormat;
            return this;
        }

        @Generated
        public ServiceProviderRequestedAttribute setRequired(boolean required) {
            this.required = required;
            return this;
        }
    }
}

