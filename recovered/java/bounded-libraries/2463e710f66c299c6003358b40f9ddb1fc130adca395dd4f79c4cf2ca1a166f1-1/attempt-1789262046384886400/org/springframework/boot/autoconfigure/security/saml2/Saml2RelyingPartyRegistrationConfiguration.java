/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.Resource
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.converter.RsaKeyConverters
 *  org.springframework.security.saml2.core.Saml2X509Credential
 *  org.springframework.security.saml2.core.Saml2X509Credential$Saml2X509CredentialType
 *  org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository
 *  org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration
 *  org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration$AssertingPartyDetails$Builder
 *  org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration$Builder
 *  org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository
 *  org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations
 *  org.springframework.security.saml2.provider.service.registration.Saml2MessageBinding
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.security.saml2;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.saml2.RegistrationConfiguredCondition;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.log.LogMessage;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.security.saml2.provider.service.registration.Saml2MessageBinding;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@Conditional(value={RegistrationConfiguredCondition.class})
@ConditionalOnMissingBean(value={RelyingPartyRegistrationRepository.class})
class Saml2RelyingPartyRegistrationConfiguration {
    private static final Log logger = LogFactory.getLog(Saml2RelyingPartyRegistrationConfiguration.class);

    Saml2RelyingPartyRegistrationConfiguration() {
    }

    @Bean
    RelyingPartyRegistrationRepository relyingPartyRegistrationRepository(Saml2RelyingPartyProperties properties) {
        List registrations = properties.getRegistration().entrySet().stream().map(this::asRegistration).collect(Collectors.toList());
        return new InMemoryRelyingPartyRegistrationRepository(registrations);
    }

    private RelyingPartyRegistration asRegistration(Map.Entry<String, Saml2RelyingPartyProperties.Registration> entry) {
        return this.asRegistration(entry.getKey(), entry.getValue());
    }

    private RelyingPartyRegistration asRegistration(String id, Saml2RelyingPartyProperties.Registration properties) {
        AssertingPartyProperties assertingParty = new AssertingPartyProperties(properties, id);
        boolean usingMetadata = StringUtils.hasText((String)assertingParty.getMetadataUri());
        RelyingPartyRegistration.Builder builder = usingMetadata ? RelyingPartyRegistrations.fromMetadataLocation((String)assertingParty.getMetadataUri()).registrationId(id) : RelyingPartyRegistration.withRegistrationId((String)id);
        builder.assertionConsumerServiceLocation(properties.getAcs().getLocation());
        builder.assertionConsumerServiceBinding(properties.getAcs().getBinding());
        builder.assertingPartyDetails(this.mapAssertingParty(properties, id, usingMetadata));
        builder.signingX509Credentials(credentials -> properties.getSigning().getCredentials().stream().map(this::asSigningCredential).forEach(credentials::add));
        builder.decryptionX509Credentials(credentials -> properties.getDecryption().getCredentials().stream().map(this::asDecryptionCredential).forEach(credentials::add));
        builder.assertingPartyDetails(details -> details.verificationX509Credentials(credentials -> assertingParty.getVerification().getCredentials().stream().map(this::asVerificationCredential).forEach(credentials::add)));
        builder.singleLogoutServiceLocation(properties.getSinglelogout().getUrl());
        builder.singleLogoutServiceResponseLocation(properties.getSinglelogout().getResponseUrl());
        builder.singleLogoutServiceBinding(properties.getSinglelogout().getBinding());
        builder.entityId(properties.getEntityId());
        RelyingPartyRegistration registration = builder.build();
        boolean signRequest = registration.getAssertingPartyDetails().getWantAuthnRequestsSigned();
        this.validateSigningCredentials(properties, signRequest);
        return registration;
    }

    private Consumer<RelyingPartyRegistration.AssertingPartyDetails.Builder> mapAssertingParty(Saml2RelyingPartyProperties.Registration registration, String id, boolean usingMetadata) {
        return details -> {
            AssertingPartyProperties assertingParty = new AssertingPartyProperties(registration, id);
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(assertingParty::getEntityId).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).entityId(arg_0));
            map.from(assertingParty::getSingleSignonBinding).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).singleSignOnServiceBinding(arg_0));
            map.from(assertingParty::getSingleSignonUrl).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).singleSignOnServiceLocation(arg_0));
            map.from(assertingParty::getSingleSignonSignRequest).when(ignored -> !usingMetadata).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).wantAuthnRequestsSigned(arg_0));
            map.from((Object)assertingParty.getSinglelogoutUrl()).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).singleLogoutServiceLocation(arg_0));
            map.from((Object)assertingParty.getSinglelogoutResponseUrl()).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).singleLogoutServiceResponseLocation(arg_0));
            map.from((Object)assertingParty.getSinglelogoutBinding()).to(arg_0 -> ((RelyingPartyRegistration.AssertingPartyDetails.Builder)details).singleLogoutServiceBinding(arg_0));
        };
    }

    private void validateSigningCredentials(Saml2RelyingPartyProperties.Registration properties, boolean signRequest) {
        if (signRequest) {
            Assert.state((!properties.getSigning().getCredentials().isEmpty() ? 1 : 0) != 0, (String)"Signing credentials must not be empty when authentication requests require signing.");
        }
    }

    private Saml2X509Credential asSigningCredential(Saml2RelyingPartyProperties.Registration.Signing.Credential properties) {
        RSAPrivateKey privateKey = this.readPrivateKey(properties.getPrivateKeyLocation());
        X509Certificate certificate = this.readCertificate(properties.getCertificateLocation());
        return new Saml2X509Credential((PrivateKey)privateKey, certificate, new Saml2X509Credential.Saml2X509CredentialType[]{Saml2X509Credential.Saml2X509CredentialType.SIGNING});
    }

    private Saml2X509Credential asDecryptionCredential(Saml2RelyingPartyProperties.Decryption.Credential properties) {
        RSAPrivateKey privateKey = this.readPrivateKey(properties.getPrivateKeyLocation());
        X509Certificate certificate = this.readCertificate(properties.getCertificateLocation());
        return new Saml2X509Credential((PrivateKey)privateKey, certificate, new Saml2X509Credential.Saml2X509CredentialType[]{Saml2X509Credential.Saml2X509CredentialType.DECRYPTION});
    }

    private Saml2X509Credential asVerificationCredential(Saml2RelyingPartyProperties.AssertingParty.Verification.Credential properties) {
        X509Certificate certificate = this.readCertificate(properties.getCertificateLocation());
        return new Saml2X509Credential(certificate, new Saml2X509Credential.Saml2X509CredentialType[]{Saml2X509Credential.Saml2X509CredentialType.ENCRYPTION, Saml2X509Credential.Saml2X509CredentialType.VERIFICATION});
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RSAPrivateKey readPrivateKey(Resource location) {
        Assert.state((location != null ? 1 : 0) != 0, (String)"No private key location specified");
        Assert.state((boolean)location.exists(), () -> "Private key location '" + location + "' does not exist");
        try (InputStream inputStream = location.getInputStream();){
            RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)RsaKeyConverters.pkcs8().convert((Object)inputStream);
            return rSAPrivateKey;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private X509Certificate readCertificate(Resource location) {
        Assert.state((location != null ? 1 : 0) != 0, (String)"No certificate location specified");
        Assert.state((boolean)location.exists(), () -> "Certificate  location '" + location + "' does not exist");
        try (InputStream inputStream = location.getInputStream();){
            X509Certificate x509Certificate = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
            return x509Certificate;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private static class AssertingPartyProperties {
        private final Saml2RelyingPartyProperties.Registration registration;
        private final String id;

        AssertingPartyProperties(Saml2RelyingPartyProperties.Registration registration, String id) {
            this.registration = registration;
            this.id = id;
        }

        String getMetadataUri() {
            return this.get("metadata-uri", Saml2RelyingPartyProperties.AssertingParty::getMetadataUri);
        }

        Saml2RelyingPartyProperties.AssertingParty.Verification getVerification() {
            return this.get("verification", Saml2RelyingPartyProperties.AssertingParty::getVerification);
        }

        String getEntityId() {
            return this.get("entity-id", Saml2RelyingPartyProperties.AssertingParty::getEntityId);
        }

        Saml2MessageBinding getSingleSignonBinding() {
            return this.get("singlesignon.binding", property -> property.getSinglesignon().getBinding());
        }

        String getSingleSignonUrl() {
            return this.get("singlesignon.url", property -> property.getSinglesignon().getUrl());
        }

        Boolean getSingleSignonSignRequest() {
            return this.get("singlesignon.sign-request", property -> property.getSinglesignon().getSignRequest());
        }

        String getSinglelogoutUrl() {
            return this.registration.getAssertingparty().getSinglelogout().getUrl();
        }

        String getSinglelogoutResponseUrl() {
            return this.registration.getAssertingparty().getSinglelogout().getResponseUrl();
        }

        Saml2MessageBinding getSinglelogoutBinding() {
            return this.registration.getAssertingparty().getSinglelogout().getBinding();
        }

        private <T> T get(String name, Function<Saml2RelyingPartyProperties.AssertingParty, T> getter) {
            T newValue = getter.apply(this.registration.getAssertingparty());
            if (newValue != null) {
                return newValue;
            }
            T deprecatedValue = getter.apply(this.registration.getIdentityprovider());
            if (deprecatedValue != null) {
                logger.warn((Object)LogMessage.format((String)"Property 'spring.security.saml2.relyingparty.registration.identityprovider.%1$s.%2$s' is deprecated, please use 'spring.security.saml2.relyingparty.registration.assertingparty.%1$s.%2$s' instead", (Object)this.id, (Object)name));
                return deprecatedValue;
            }
            return newValue;
        }
    }
}

