/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.webflow.core.collection.AttributeMap
 *  org.springframework.webflow.core.collection.LocalAttributeMap
 *  org.springframework.webflow.definition.TransitionDefinition
 *  org.springframework.webflow.execution.Event
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public final class MultifactorAuthenticationUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(MultifactorAuthenticationUtils.class);

    public static Map<String, Object> buildEventAttributeMap(Principal principal, Optional<RegisteredService> service, MultifactorAuthenticationProvider provider) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(Principal.class.getName(), principal);
        service.ifPresent(svc -> map.put(RegisteredService.class.getName(), svc));
        map.put(MultifactorAuthenticationProvider.class.getName(), provider);
        return map;
    }

    public static Event validateEventIdForMatchingTransitionInContext(String eventId, Optional<RequestContext> context, Map<String, Object> attributes) {
        LocalAttributeMap attributesMap = new LocalAttributeMap(attributes);
        Event event = new Event((Object)eventId, eventId, (AttributeMap)attributesMap);
        LOGGER.trace("Attempting to find a matching transition for event id [{}]", (Object)event.getId());
        return context.map(ctx -> {
            LOGGER.trace("Reviewing current state [{}], event [{}] and transition [{}]", new Object[]{ctx.getCurrentState(), ctx.getCurrentEvent(), ctx.getCurrentTransition()});
            TransitionDefinition def = ctx.getMatchingTransition(event.getId());
            if (def == null) {
                String msg = String.format("State [%s:%s:%s] does not have a matching transition for %s", ctx.getCurrentState().getId(), ctx.getCurrentEvent() != null ? ctx.getCurrentEvent().getId() : "N/A", ctx.getCurrentTransition() != null ? ctx.getCurrentTransition().getId() : "N/A", event.getId());
                LoggingUtils.error((Logger)LOGGER, (String)msg);
                throw new AuthenticationException(msg);
            }
            return event;
        }).orElse(event);
    }

    public static Set<Event> resolveEventViaMultivaluedAttribute(Principal principal, Object attributeValue, RegisteredService service, Optional<RequestContext> context, MultifactorAuthenticationProvider provider, BiPredicate<String, MultifactorAuthenticationProvider> predicate) {
        if (attributeValue instanceof Collection) {
            LOGGER.debug("Attribute value [{}] is a multi-valued attribute", attributeValue);
            Collection values = (Collection)attributeValue;
            HashSet<Event> events = new HashSet<Event>();
            values.forEach(value -> {
                String id = provider.getId();
                try {
                    LOGGER.trace("Testing attribute value [{}] against multifactor provider [{}]", value, (Object)provider);
                    if (predicate.test((String)value, provider)) {
                        Map<String, Object> attributeMap = MultifactorAuthenticationUtils.buildEventAttributeMap(principal, Optional.ofNullable(service), provider);
                        LOGGER.trace("Event attribute map for provider [{}] transition is [{}]", (Object)provider, attributeMap);
                        Event event = MultifactorAuthenticationUtils.validateEventIdForMatchingTransitionInContext(id, context, attributeMap);
                        events.add(event);
                    }
                }
                catch (Exception e) {
                    LOGGER.debug("Ignoring [{}] since no matching transition could be found for provider [{}]", value, (Object)id);
                }
            });
            return events;
        }
        LOGGER.debug("Attribute value [{}] is not a multi-valued attribute", attributeValue);
        return null;
    }

    public static Optional<MultifactorAuthenticationProvider> resolveProvider(Map<String, MultifactorAuthenticationProvider> providers, Collection<String> requestMfaMethod) {
        return providers.values().stream().filter(p -> requestMfaMethod.stream().filter(Objects::nonNull).anyMatch(arg_0 -> ((MultifactorAuthenticationProvider)p).matches(arg_0))).findFirst();
    }

    public static Optional<MultifactorAuthenticationProvider> resolveProvider(Map<String, MultifactorAuthenticationProvider> providers, String requestMfaMethod) {
        return MultifactorAuthenticationUtils.resolveProvider(providers, Stream.of(requestMfaMethod).collect(Collectors.toList()));
    }

    public static Set<Event> resolveEventViaSingleAttribute(Principal principal, Object providedAttributeValue, RegisteredService service, Optional<RequestContext> context, MultifactorAuthenticationProvider provider, BiPredicate<String, MultifactorAuthenticationProvider> predicate) {
        return (Set)FunctionUtils.doUnchecked(() -> {
            boolean processSingleValue;
            boolean bl = processSingleValue = !(providedAttributeValue instanceof Collection) || CollectionUtils.toCollection((Object)providedAttributeValue).size() == 1;
            if (processSingleValue) {
                String attributeValue = CollectionUtils.firstElement((Object)providedAttributeValue).map(Object::toString).orElse("");
                LOGGER.debug("Attribute value [{}] is a single-valued attribute", (Object)attributeValue);
                if (predicate.test(attributeValue, provider)) {
                    LOGGER.debug("Attribute value predicate [{}] has matched the [{}]", (Object)predicate, (Object)attributeValue);
                    return MultifactorAuthenticationUtils.evaluateEventForProviderInContext(principal, service, context, provider);
                }
                LOGGER.debug("Attribute value predicate [{}] could not match the [{}]", (Object)predicate, (Object)attributeValue);
            }
            LOGGER.debug("Attribute value [{}] is not a single-valued attribute", providedAttributeValue);
            return null;
        });
    }

    public static Collection<MultifactorAuthenticationProvider> getMultifactorAuthenticationProviderForService(RegisteredService service, ApplicationContext applicationContext) {
        return Optional.ofNullable(service.getMultifactorAuthenticationPolicy()).map(policy -> policy.getMultifactorAuthenticationProviders().stream().map(provider -> MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderFromApplicationContext(provider, applicationContext)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet())).orElseGet(HashSet::new);
    }

    public static Optional<MultifactorAuthenticationProvider> getMultifactorAuthenticationProviderFromApplicationContext(String providerId) {
        return MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderFromApplicationContext(providerId, ApplicationContextProvider.getApplicationContext());
    }

    public static Optional<MultifactorAuthenticationProvider> getMultifactorAuthenticationProviderFromApplicationContext(String providerId, ApplicationContext applicationContext) {
        LOGGER.trace("Locating bean definition for [{}]", (Object)providerId);
        return MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(applicationContext).values().stream().filter(p -> p.matches(providerId)).findFirst();
    }

    public static Set<Event> evaluateEventForProviderInContext(Principal principal, RegisteredService service, Optional<RequestContext> context, MultifactorAuthenticationProvider provider) {
        LOGGER.debug("Attempting check for availability of multifactor authentication provider [{}] for [{}]", (Object)provider, (Object)service);
        if (provider != null) {
            LOGGER.debug("Provider [{}] is successfully verified", (Object)provider);
            String id = provider.getId();
            Map<String, Object> eventAttrMap = MultifactorAuthenticationUtils.buildEventAttributeMap(principal, Optional.ofNullable(service), provider);
            Event event = MultifactorAuthenticationUtils.validateEventIdForMatchingTransitionInContext(id, context, eventAttrMap);
            return CollectionUtils.wrapSet((Object)event);
        }
        LOGGER.debug("Provider could not be verified");
        return new HashSet<Event>(0);
    }

    public static Map<String, MultifactorAuthenticationProvider> getAvailableMultifactorAuthenticationProviders(ApplicationContext applicationContext) {
        try {
            return applicationContext.getBeansOfType(MultifactorAuthenticationProvider.class);
        }
        catch (Exception e) {
            LOGGER.trace("No beans of type [{}] are available in the application context. CAS may not be configured to handle multifactor authentication requests in absence of a provider", MultifactorAuthenticationProvider.class);
            return new HashMap<String, MultifactorAuthenticationProvider>(0);
        }
    }

    public static Optional<MultifactorAuthenticationProvider> getMultifactorAuthenticationProviderById(String providerId, ApplicationContext context) {
        return MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(context).values().stream().filter(p -> p.matches(providerId)).findFirst();
    }

    @Generated
    private MultifactorAuthenticationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

