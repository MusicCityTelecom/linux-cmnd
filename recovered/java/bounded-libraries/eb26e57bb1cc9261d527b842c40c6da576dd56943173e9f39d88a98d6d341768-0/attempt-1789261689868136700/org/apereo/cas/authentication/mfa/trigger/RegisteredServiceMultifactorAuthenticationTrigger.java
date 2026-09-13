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
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class RegisteredServiceMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector;
    private final ConfigurableApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (registeredService == null || authentication == null) {
            LOGGER.debug("No service or authentication is available to determine event for principal");
            return Optional.empty();
        }
        RegisteredServiceMultifactorPolicy policy = registeredService.getMultifactorAuthenticationPolicy();
        if (policy == null || policy.getMultifactorAuthenticationProviders().isEmpty()) {
            LOGGER.trace("Authentication policy does not contain any multifactor authentication providers");
            return Optional.empty();
        }
        if (StringUtils.isNotBlank((CharSequence)policy.getPrincipalAttributeNameTrigger()) || StringUtils.isNotBlank((CharSequence)policy.getPrincipalAttributeValueToMatch())) {
            LOGGER.debug("Authentication policy for [{}] has defined principal attribute triggers. Skipping...", (Object)registeredService.getServiceId());
            return Optional.empty();
        }
        Principal principal = authentication.getPrincipal();
        Collection<MultifactorAuthenticationProvider> providers = MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderForService(registeredService, (ApplicationContext)this.applicationContext);
        if (providers != null && !providers.isEmpty()) {
            MultifactorAuthenticationProvider provider = this.multifactorAuthenticationProviderSelector.resolve(providers, registeredService, principal);
            LOGGER.debug("Selected multifactor authentication provider for this transaction is [{}]", (Object)provider);
            if (provider != null) {
                return Optional.of(provider);
            }
        }
        return Optional.empty();
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }

    @Generated
    public MultifactorAuthenticationProviderSelector getMultifactorAuthenticationProviderSelector() {
        return this.multifactorAuthenticationProviderSelector;
    }

    @Generated
    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
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
    public RegisteredServiceMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector, ConfigurableApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.multifactorAuthenticationProviderSelector = multifactorAuthenticationProviderSelector;
        this.applicationContext = applicationContext;
    }
}

