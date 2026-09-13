/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.convert.ApplicationConversionService
 *  org.springframework.core.convert.ConversionException
 *  org.springframework.security.config.oauth2.client.CommonOAuth2Provider
 *  org.springframework.security.oauth2.client.registration.ClientRegistration
 *  org.springframework.security.oauth2.client.registration.ClientRegistration$Builder
 *  org.springframework.security.oauth2.client.registration.ClientRegistrations
 *  org.springframework.security.oauth2.core.AuthenticationMethod
 *  org.springframework.security.oauth2.core.AuthorizationGrantType
 *  org.springframework.security.oauth2.core.ClientAuthenticationMethod
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.security.oauth2.client;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionException;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.StringUtils;

public final class OAuth2ClientPropertiesRegistrationAdapter {
    private OAuth2ClientPropertiesRegistrationAdapter() {
    }

    public static Map<String, ClientRegistration> getClientRegistrations(OAuth2ClientProperties properties) {
        HashMap<String, ClientRegistration> clientRegistrations = new HashMap<String, ClientRegistration>();
        properties.getRegistration().forEach((key, value) -> clientRegistrations.put((String)key, OAuth2ClientPropertiesRegistrationAdapter.getClientRegistration(key, value, properties.getProvider())));
        return clientRegistrations;
    }

    private static ClientRegistration getClientRegistration(String registrationId, OAuth2ClientProperties.Registration properties, Map<String, OAuth2ClientProperties.Provider> providers) {
        ClientRegistration.Builder builder = OAuth2ClientPropertiesRegistrationAdapter.getBuilderFromIssuerIfPossible(registrationId, properties.getProvider(), providers);
        if (builder == null) {
            builder = OAuth2ClientPropertiesRegistrationAdapter.getBuilder(registrationId, properties.getProvider(), providers);
        }
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties::getClientId).to(arg_0 -> ((ClientRegistration.Builder)builder).clientId(arg_0));
        map.from(properties::getClientSecret).to(arg_0 -> ((ClientRegistration.Builder)builder).clientSecret(arg_0));
        map.from(properties::getClientAuthenticationMethod).as(ClientAuthenticationMethod::new).to(arg_0 -> ((ClientRegistration.Builder)builder).clientAuthenticationMethod(arg_0));
        map.from(properties::getAuthorizationGrantType).as(AuthorizationGrantType::new).to(arg_0 -> ((ClientRegistration.Builder)builder).authorizationGrantType(arg_0));
        map.from(properties::getRedirectUri).to(arg_0 -> ((ClientRegistration.Builder)builder).redirectUri(arg_0));
        map.from(properties::getScope).as(StringUtils::toStringArray).to(arg_0 -> ((ClientRegistration.Builder)builder).scope(arg_0));
        map.from(properties::getClientName).to(arg_0 -> ((ClientRegistration.Builder)builder).clientName(arg_0));
        return builder.build();
    }

    private static ClientRegistration.Builder getBuilderFromIssuerIfPossible(String registrationId, String configuredProviderId, Map<String, OAuth2ClientProperties.Provider> providers) {
        OAuth2ClientProperties.Provider provider;
        String issuer;
        String providerId;
        String string = providerId = configuredProviderId != null ? configuredProviderId : registrationId;
        if (providers.containsKey(providerId) && (issuer = (provider = providers.get(providerId)).getIssuerUri()) != null) {
            ClientRegistration.Builder builder = ClientRegistrations.fromIssuerLocation((String)issuer).registrationId(registrationId);
            return OAuth2ClientPropertiesRegistrationAdapter.getBuilder(builder, provider);
        }
        return null;
    }

    private static ClientRegistration.Builder getBuilder(String registrationId, String configuredProviderId, Map<String, OAuth2ClientProperties.Provider> providers) {
        ClientRegistration.Builder builder;
        String providerId = configuredProviderId != null ? configuredProviderId : registrationId;
        CommonOAuth2Provider provider = OAuth2ClientPropertiesRegistrationAdapter.getCommonProvider(providerId);
        if (provider == null && !providers.containsKey(providerId)) {
            throw new IllegalStateException(OAuth2ClientPropertiesRegistrationAdapter.getErrorMessage(configuredProviderId, registrationId));
        }
        ClientRegistration.Builder builder2 = builder = provider != null ? provider.getBuilder(registrationId) : ClientRegistration.withRegistrationId((String)registrationId);
        if (providers.containsKey(providerId)) {
            return OAuth2ClientPropertiesRegistrationAdapter.getBuilder(builder, providers.get(providerId));
        }
        return builder;
    }

    private static String getErrorMessage(String configuredProviderId, String registrationId) {
        return configuredProviderId != null ? "Unknown provider ID '" + configuredProviderId + "'" : "Provider ID must be specified for client registration '" + registrationId + "'";
    }

    private static ClientRegistration.Builder getBuilder(ClientRegistration.Builder builder, OAuth2ClientProperties.Provider provider) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(provider::getAuthorizationUri).to(arg_0 -> ((ClientRegistration.Builder)builder).authorizationUri(arg_0));
        map.from(provider::getTokenUri).to(arg_0 -> ((ClientRegistration.Builder)builder).tokenUri(arg_0));
        map.from(provider::getUserInfoUri).to(arg_0 -> ((ClientRegistration.Builder)builder).userInfoUri(arg_0));
        map.from(provider::getUserInfoAuthenticationMethod).as(AuthenticationMethod::new).to(arg_0 -> ((ClientRegistration.Builder)builder).userInfoAuthenticationMethod(arg_0));
        map.from(provider::getJwkSetUri).to(arg_0 -> ((ClientRegistration.Builder)builder).jwkSetUri(arg_0));
        map.from(provider::getUserNameAttribute).to(arg_0 -> ((ClientRegistration.Builder)builder).userNameAttributeName(arg_0));
        return builder;
    }

    private static CommonOAuth2Provider getCommonProvider(String providerId) {
        try {
            return (CommonOAuth2Provider)ApplicationConversionService.getSharedInstance().convert((Object)providerId, CommonOAuth2Provider.class);
        }
        catch (ConversionException ex) {
            return null;
        }
    }
}

