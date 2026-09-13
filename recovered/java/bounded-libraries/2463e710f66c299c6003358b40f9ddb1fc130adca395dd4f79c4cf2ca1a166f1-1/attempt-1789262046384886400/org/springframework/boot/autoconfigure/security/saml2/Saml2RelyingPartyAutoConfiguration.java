/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository
 */
package org.springframework.boot.autoconfigure.security.saml2;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.saml2.Saml2LoginConfiguration;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyRegistrationConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;

@AutoConfiguration(before={SecurityAutoConfiguration.class})
@ConditionalOnClass(value={RelyingPartyRegistrationRepository.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@Import(value={Saml2RelyingPartyRegistrationConfiguration.class, Saml2LoginConfiguration.class})
@EnableConfigurationProperties(value={Saml2RelyingPartyProperties.class})
public class Saml2RelyingPartyAutoConfiguration {
}

