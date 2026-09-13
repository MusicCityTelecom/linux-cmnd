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
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.util.StringUtils
 *  org.springframework.webflow.execution.Event
 *  org.springframework.webflow.execution.RequestContext
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
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationRequiredException;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class PrincipalAttributeMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalAttributeMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (authentication == null) {
            LOGGER.debug("No authentication is available to determine event for principal");
            return Optional.empty();
        }
        Principal principal = this.getPrincipalForMultifactorAuthentication(authentication);
        Set<Event> result = this.resolveMultifactorAuthenticationProvider(Optional.empty(), registeredService, principal);
        if (result != null && !result.isEmpty()) {
            Optional id = CollectionUtils.firstElement(result);
            if (id.isEmpty()) {
                return Optional.empty();
            }
            return MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderById(id.get().toString(), this.applicationContext);
        }
        return Optional.empty();
    }

    protected Principal getPrincipalForMultifactorAuthentication(Authentication authentication) {
        return authentication.getPrincipal();
    }

    protected Set<Event> resolveMultifactorAuthenticationProvider(Optional<RequestContext> context, RegisteredService service, Principal principal) {
        Set<Event> events = this.determineMultifactorAuthenticationEvent(context, service, principal);
        boolean deny = this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().isDenyIfUnmatched();
        if (deny && (events == null || events.isEmpty())) {
            throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationRequiredException(service, principal)));
        }
        return events;
    }

    protected Set<Event> determineMultifactorAuthenticationEvent(Optional<RequestContext> context, RegisteredService service, Principal principal) {
        String globalPrincipalAttributeValueRegex = this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().getGlobalPrincipalAttributeValueRegex();
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        Collection<MultifactorAuthenticationProvider> providers = providerMap.values();
        if (providers.size() == 1 && org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)globalPrincipalAttributeValueRegex)) {
            return this.resolveSingleMultifactorProvider(context, service, principal, providers);
        }
        return this.resolveMultifactorProviderViaPredicate(context, service, principal, providers);
    }

    protected Set<Event> resolveMultifactorProviderViaPredicate(Optional<RequestContext> context, RegisteredService service, Principal principal, Collection<MultifactorAuthenticationProvider> providers) {
        Set attributeNames = StringUtils.commaDelimitedListToSet((String)this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().getGlobalPrincipalAttributeNameTriggers());
        return this.multifactorAuthenticationProviderResolver.resolveEventViaPrincipalAttribute(principal, (Collection)attributeNames, service, context, providers, (attributeValue, provider) -> attributeValue != null && provider.matches(attributeValue));
    }

    protected Set<Event> resolveSingleMultifactorProvider(Optional<RequestContext> context, RegisteredService service, Principal principal, Collection<MultifactorAuthenticationProvider> providers) {
        String globalPrincipalAttributeValueRegex = this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().getGlobalPrincipalAttributeValueRegex();
        MultifactorAuthenticationProvider provider = providers.iterator().next();
        LOGGER.trace("Found a single multifactor provider [{}] in the application context", (Object)provider);
        Set attributeNames = StringUtils.commaDelimitedListToSet((String)this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().getGlobalPrincipalAttributeNameTriggers());
        return this.multifactorAuthenticationProviderResolver.resolveEventViaPrincipalAttribute(principal, (Collection)attributeNames, service, context, providers, (attributeValue, mfaProvider) -> attributeValue != null && RegexUtils.find((String)globalPrincipalAttributeValueRegex, (String)attributeValue));
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
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public PrincipalAttributeMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.multifactorAuthenticationProviderResolver = multifactorAuthenticationProviderResolver;
        this.applicationContext = applicationContext;
    }
}

