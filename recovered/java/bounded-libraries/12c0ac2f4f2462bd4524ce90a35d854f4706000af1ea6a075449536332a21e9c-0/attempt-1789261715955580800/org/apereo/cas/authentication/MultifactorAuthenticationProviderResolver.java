/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
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
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public interface MultifactorAuthenticationProviderResolver {
    public static final String BEAN_NAME = "multifactorAuthenticationProviderResolver";
    public static final Logger LOGGER = LoggerFactory.getLogger(MultifactorAuthenticationProviderResolver.class);

    default public String getName() {
        return this.getClass().getName();
    }

    default public Set<Event> resolveEventViaAuthenticationAttribute(Authentication authentication, Collection<String> attributeNames, RegisteredService service, Optional<RequestContext> context, Collection<MultifactorAuthenticationProvider> providers, BiPredicate<String, MultifactorAuthenticationProvider> predicate) {
        return this.resolveEventViaAttribute(authentication.getPrincipal(), authentication.getAttributes(), attributeNames, service, context, providers, predicate);
    }

    public Set<Event> resolveEventViaAttribute(Principal var1, Map<String, List<Object>> var2, Collection<String> var3, RegisteredService var4, Optional<RequestContext> var5, Collection<MultifactorAuthenticationProvider> var6, BiPredicate<String, MultifactorAuthenticationProvider> var7);

    default public Set<Event> resolveEventViaPrincipalAttribute(Principal principal, Collection<String> attributeNames, RegisteredService service, Optional<RequestContext> context, Collection<MultifactorAuthenticationProvider> providers, BiPredicate<String, MultifactorAuthenticationProvider> predicate) {
        if (attributeNames.isEmpty()) {
            LOGGER.trace("No attribute names are provided to trigger a multifactor authentication provider via [{}]", (Object)this.getName());
            return null;
        }
        if (providers == null || providers.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            return null;
        }
        Principal resolvedPrincipal = this.resolvePrincipal(principal);
        LOGGER.debug("Multifactor authentication principal [{}] to evaluate for [{}] using attributes [{}]", new Object[]{resolvedPrincipal, providers, attributeNames});
        return this.resolveEventViaAttribute(principal, resolvedPrincipal.getAttributes(), attributeNames, service, context, providers, predicate);
    }

    public Principal resolvePrincipal(Principal var1);
}

