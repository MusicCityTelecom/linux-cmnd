/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.MultifactorAuthenticationPrincipalResolver
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.webflow.execution.Event
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import lombok.Generated;
import org.apereo.cas.authentication.MultifactorAuthenticationPrincipalResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class DefaultMultifactorAuthenticationProviderResolver
implements MultifactorAuthenticationProviderResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMultifactorAuthenticationProviderResolver.class);
    private final List<MultifactorAuthenticationPrincipalResolver> multifactorAuthenticationPrincipalResolver;

    public DefaultMultifactorAuthenticationProviderResolver(MultifactorAuthenticationPrincipalResolver resolver) {
        this.multifactorAuthenticationPrincipalResolver = List.of(resolver);
    }

    public Set<Event> resolveEventViaAttribute(Principal principal, Map<String, List<Object>> attributesToExamine, Collection<String> attributeNames, RegisteredService service, Optional<RequestContext> context, Collection<MultifactorAuthenticationProvider> providers, BiPredicate<String, MultifactorAuthenticationProvider> predicate) {
        LOGGER.debug("Attributes to examine are [{}]", attributesToExamine);
        if (providers == null || providers.isEmpty()) {
            LOGGER.debug("No authentication provider is associated with this service");
            return null;
        }
        LOGGER.debug("Locating attribute value for attribute(s): [{}].", attributeNames);
        for (String attributeName : attributeNames) {
            List<Object> attributeValue = attributesToExamine.get(attributeName);
            if (attributeValue == null) {
                LOGGER.debug("Attribute value for [{}] to determine event is not configured for [{}]", (Object)attributeName, (Object)principal.getId());
                continue;
            }
            LOGGER.debug("Located attribute value [{}] for [{}]", attributeValue, attributeNames);
            for (MultifactorAuthenticationProvider provider : providers) {
                Set<Event> results = MultifactorAuthenticationUtils.resolveEventViaSingleAttribute(principal, attributeValue, service, context, provider, predicate);
                if (results == null || results.isEmpty()) {
                    results = MultifactorAuthenticationUtils.resolveEventViaMultivaluedAttribute(principal, attributeValue, service, context, provider, predicate);
                }
                if (results == null || results.isEmpty()) continue;
                LOGGER.debug("Resolved set of events based on the attribute [{}] are [{}]", (Object)attributeName, results);
                return results;
            }
        }
        LOGGER.debug("No set of events based on the attribute(s) [{}] could be matched", attributeNames);
        return null;
    }

    public Principal resolvePrincipal(Principal principal) {
        return this.multifactorAuthenticationPrincipalResolver.stream().filter(resolver -> resolver.supports(principal)).findFirst().map(r -> r.resolve(principal)).orElseThrow(() -> new IllegalStateException("Unable to resolve principal for multifactor authentication"));
    }

    @Generated
    public List<MultifactorAuthenticationPrincipalResolver> getMultifactorAuthenticationPrincipalResolver() {
        return this.multifactorAuthenticationPrincipalResolver;
    }

    @Generated
    public DefaultMultifactorAuthenticationProviderResolver(List<MultifactorAuthenticationPrincipalResolver> multifactorAuthenticationPrincipalResolver) {
        this.multifactorAuthenticationPrincipalResolver = multifactorAuthenticationPrincipalResolver;
    }
}

