/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.support.mfa.AuthenticationAttributeMultifactorAuthenticationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.mfa.AuthenticationAttributeMultifactorAuthenticationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

public class AuthenticationAttributeMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationAttributeMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        Set result;
        AuthenticationAttributeMultifactorAuthenticationProperties mfa = this.casProperties.getAuthn().getMfa().getTriggers().getAuthentication();
        String globalAuthenticationAttributeValueRegex = mfa.getGlobalAuthenticationAttributeValueRegex();
        Set attributeNames = StringUtils.commaDelimitedListToSet((String)mfa.getGlobalAuthenticationAttributeNameTriggers());
        if (authentication == null) {
            LOGGER.debug("No authentication is available to determine event for principal");
            return Optional.empty();
        }
        if (attributeNames.isEmpty()) {
            LOGGER.trace("Authentication attribute name to determine event is not configured");
            return Optional.empty();
        }
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            return Optional.empty();
        }
        Collection<MultifactorAuthenticationProvider> providers = providerMap.values();
        if (providers.size() == 1 && org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)globalAuthenticationAttributeValueRegex)) {
            MultifactorAuthenticationProvider provider = providers.iterator().next();
            LOGGER.debug("Found a single multifactor provider [{}] in the application context", (Object)provider);
            Set result2 = this.multifactorAuthenticationProviderResolver.resolveEventViaAuthenticationAttribute(authentication, (Collection)attributeNames, registeredService, Optional.empty(), providers, (attributeValue, mfaProvider) -> attributeValue != null && attributeValue.matches(globalAuthenticationAttributeValueRegex));
            if (result2 != null && !result2.isEmpty()) {
                return Optional.of(provider);
            }
        }
        if ((result = this.multifactorAuthenticationProviderResolver.resolveEventViaAuthenticationAttribute(authentication, (Collection)attributeNames, registeredService, Optional.empty(), providers, (attributeValue, mfaProvider) -> attributeValue != null && mfaProvider.matches(attributeValue))) != null && !result.isEmpty()) {
            Optional id = CollectionUtils.firstElement((Object)result);
            return id.flatMap(value -> MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderById(value.toString(), this.applicationContext));
        }
        return Optional.empty();
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }

    @Generated
    public MultifactorAuthenticationProviderResolver getMultifactorAuthenticationProviderResolver() {
        return this.multifactorAuthenticationProviderResolver;
    }

    @Generated
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public AuthenticationAttributeMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.multifactorAuthenticationProviderResolver = multifactorAuthenticationProviderResolver;
        this.applicationContext = applicationContext;
    }
}

