/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.util.StringUtils
 *  org.springframework.webflow.execution.Event
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector;
import org.apereo.cas.authentication.MultifactorAuthenticationRequiredException;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.Event;

public class RegisteredServicePrincipalAttributeMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServicePrincipalAttributeMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver;
    private final ApplicationContext applicationContext;
    private final MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (authentication == null || registeredService == null) {
            LOGGER.debug("No authentication or service is available to determine event for principal");
            return Optional.empty();
        }
        LOGGER.trace("Evaluating multifactor authentication policy for registered service [{}]", (Object)registeredService);
        RegisteredServiceMultifactorPolicy policy = registeredService.getMultifactorAuthenticationPolicy();
        if (policy == null || registeredService.getMultifactorAuthenticationPolicy().getMultifactorAuthenticationProviders().isEmpty()) {
            LOGGER.trace("Authentication policy is absent or does not contain any multifactor authentication providers");
            return Optional.empty();
        }
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)policy.getPrincipalAttributeNameTrigger()) || org.apache.commons.lang3.StringUtils.isBlank((CharSequence)policy.getPrincipalAttributeValueToMatch())) {
            LOGGER.debug("Authentication policy does not define a principal attribute and/or value to trigger multifactor authentication");
            return Optional.empty();
        }
        Principal principal = this.multifactorAuthenticationProviderResolver.resolvePrincipal(authentication.getPrincipal());
        Collection<MultifactorAuthenticationProvider> providers = MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderForService(registeredService, this.applicationContext);
        if (providers.size() > 1) {
            MultifactorAuthenticationProvider resolvedProvider = this.multifactorAuthenticationProviderSelector.resolve(providers, registeredService, principal);
            providers.clear();
            providers.add(resolvedProvider);
        }
        LOGGER.debug("Resolved multifactor providers are [{}]", providers);
        Set result = this.multifactorAuthenticationProviderResolver.resolveEventViaPrincipalAttribute(principal, (Collection)StringUtils.commaDelimitedListToSet((String)policy.getPrincipalAttributeNameTrigger()), registeredService, Optional.empty(), providers, (attributeValue, mfaProvider) -> attributeValue != null && RegexUtils.find((String)policy.getPrincipalAttributeValueToMatch(), (String)attributeValue));
        if (result != null && !result.isEmpty()) {
            return CollectionUtils.firstElement((Object)result).map(Event.class::cast).map(event -> {
                MultifactorAuthenticationProvider provider = (MultifactorAuthenticationProvider)CollectionUtils.firstElement((Object)providers, MultifactorAuthenticationProvider.class).orElseThrow();
                if (provider instanceof ChainingMultifactorAuthenticationProvider && provider.getId().equals(event.getId())) {
                    ChainingMultifactorAuthenticationProvider chain = (ChainingMultifactorAuthenticationProvider)provider;
                    boolean matched = chain.getMultifactorAuthenticationProviders().stream().map(p -> MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderById(p.getId(), this.applicationContext)).allMatch(Optional::isPresent);
                    return matched ? Optional.of(provider) : this.unmatchedMultifactorAuthenticationTrigger(principal, registeredService);
                }
                return MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderById(event.getId(), this.applicationContext);
            }).orElseGet(() -> this.unmatchedMultifactorAuthenticationTrigger(principal, registeredService));
        }
        return this.unmatchedMultifactorAuthenticationTrigger(principal, registeredService);
    }

    private Optional<MultifactorAuthenticationProvider> unmatchedMultifactorAuthenticationTrigger(Principal principal, RegisteredService registeredService) {
        if (this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().isDenyIfUnmatched()) {
            throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationRequiredException(registeredService, principal)));
        }
        return Optional.empty();
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
    public MultifactorAuthenticationProviderSelector getMultifactorAuthenticationProviderSelector() {
        return this.multifactorAuthenticationProviderSelector;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public RegisteredServicePrincipalAttributeMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver, ApplicationContext applicationContext, MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector) {
        this.casProperties = casProperties;
        this.multifactorAuthenticationProviderResolver = multifactorAuthenticationProviderResolver;
        this.applicationContext = applicationContext;
        this.multifactorAuthenticationProviderSelector = multifactorAuthenticationProviderSelector;
    }
}

