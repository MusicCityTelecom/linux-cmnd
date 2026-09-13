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
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.Ordered
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderAbsentException;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

public class GlobalMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final ApplicationContext applicationContext;
    private final MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (authentication == null) {
            LOGGER.debug("No authentication is available to determine event for principal");
            return Optional.empty();
        }
        String globalProviderId = this.casProperties.getAuthn().getMfa().getTriggers().getGlobal().getGlobalProviderId();
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)globalProviderId)) {
            LOGGER.trace("No value could be found for for the global provider id");
            return Optional.empty();
        }
        LOGGER.debug("Attempting to globally activate [{}]", (Object)globalProviderId);
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context to handle [{}]", (Object)globalProviderId);
            throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationProviderAbsentException()));
        }
        Set providers = StringUtils.commaDelimitedListToSet((String)globalProviderId);
        List<MultifactorAuthenticationProvider> resolvedProviders = providers.stream().map(provider -> MultifactorAuthenticationUtils.resolveProvider(providerMap, provider)).filter(Optional::isPresent).map(Optional::get).sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
        if (resolvedProviders.size() != providers.size()) {
            this.handleAbsentMultifactorProvider(globalProviderId, resolvedProviders);
        }
        if (resolvedProviders.size() == 1) {
            return this.resolveSingleMultifactorProvider(resolvedProviders.get(0));
        }
        return this.resolveMultifactorProvider(authentication, registeredService, resolvedProviders);
    }

    protected void handleAbsentMultifactorProvider(String globalProviderId, List<MultifactorAuthenticationProvider> resolvedProviders) {
        String providerIds = resolvedProviders.stream().map(MultifactorAuthenticationProvider::getId).collect(Collectors.joining(","));
        String message = String.format("Not all requested multifactor providers could be found. Requested providers are [%s] and resolved providers are [%s]", globalProviderId, providerIds);
        LOGGER.warn(message, (Object)globalProviderId);
        throw new MultifactorAuthenticationProviderAbsentException(message);
    }

    protected Optional<MultifactorAuthenticationProvider> resolveSingleMultifactorProvider(MultifactorAuthenticationProvider resolvedProvider) {
        LOGGER.debug("Resolved single multifactor provider [{}]", (Object)resolvedProvider);
        return Optional.of(resolvedProvider);
    }

    protected Optional<MultifactorAuthenticationProvider> resolveMultifactorProvider(Authentication authentication, RegisteredService registeredService, List<MultifactorAuthenticationProvider> resolvedProviders) {
        Principal principal = authentication.getPrincipal();
        MultifactorAuthenticationProvider provider = this.multifactorAuthenticationProviderSelector.resolve(resolvedProviders, registeredService, principal);
        LOGGER.debug("Selected multifactor authentication provider for this transaction is [{}]", (Object)provider);
        return Optional.ofNullable(provider);
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
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
    public GlobalMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, ApplicationContext applicationContext, MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector) {
        this.casProperties = casProperties;
        this.applicationContext = applicationContext;
        this.multifactorAuthenticationProviderSelector = multifactorAuthenticationProviderSelector;
    }
}

