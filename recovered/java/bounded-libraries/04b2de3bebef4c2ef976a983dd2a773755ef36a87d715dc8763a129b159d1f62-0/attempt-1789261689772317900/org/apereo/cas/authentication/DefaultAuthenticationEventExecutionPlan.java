/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationEventExecutionPlan
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 *  org.apereo.cas.authentication.AuthenticationMetaDataPopulator
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.AuthenticationPolicyResolver
 *  org.apereo.cas.authentication.AuthenticationPostProcessor
 *  org.apereo.cas.authentication.AuthenticationPreProcessor
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationMetaDataPopulator;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.AuthenticationPolicyResolver;
import org.apereo.cas.authentication.AuthenticationPostProcessor;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.handler.DefaultAuthenticationHandlerResolver;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultAuthenticationEventExecutionPlan
implements AuthenticationEventExecutionPlan {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationEventExecutionPlan.class);
    private final List<AuthenticationMetaDataPopulator> authenticationMetaDataPopulatorList = new ArrayList<AuthenticationMetaDataPopulator>(0);
    private final List<AuthenticationPostProcessor> authenticationPostProcessors = new ArrayList<AuthenticationPostProcessor>(0);
    private final List<AuthenticationPreProcessor> authenticationPreProcessors = new ArrayList<AuthenticationPreProcessor>(0);
    private final List<AuthenticationPolicy> authenticationPolicies = new ArrayList<AuthenticationPolicy>(0);
    private final List<AuthenticationHandlerResolver> authenticationHandlerResolvers = new ArrayList<AuthenticationHandlerResolver>(0);
    private final List<AuthenticationPolicyResolver> authenticationPolicyResolvers = new ArrayList<AuthenticationPolicyResolver>(0);
    private final Map<AuthenticationHandler, PrincipalResolver> authenticationHandlerPrincipalResolverMap = new LinkedHashMap<AuthenticationHandler, PrincipalResolver>();

    public boolean registerAuthenticationHandler(AuthenticationHandler handler) {
        return this.registerAuthenticationHandlerWithPrincipalResolver(handler, null);
    }

    public void registerAuthenticationMetadataPopulator(AuthenticationMetaDataPopulator populator) {
        if (BeanSupplier.isNotProxy((Object)populator)) {
            LOGGER.trace("Registering metadata populator [{}] into the execution plan", (Object)populator);
            this.authenticationMetaDataPopulatorList.add(populator);
        }
    }

    public void registerAuthenticationPostProcessor(AuthenticationPostProcessor processor) {
        if (BeanSupplier.isNotProxy((Object)processor)) {
            LOGGER.debug("Registering authentication post processor [{}] into the execution plan", (Object)processor);
            this.authenticationPostProcessors.add(processor);
        }
    }

    public void registerAuthenticationPreProcessor(AuthenticationPreProcessor processor) {
        if (BeanSupplier.isNotProxy((Object)processor)) {
            LOGGER.debug("Registering authentication pre processor [{}] into the execution plan", (Object)processor);
            this.authenticationPreProcessors.add(processor);
        }
    }

    public void registerAuthenticationMetadataPopulators(Collection<AuthenticationMetaDataPopulator> populators) {
        populators.stream().filter(BeanSupplier::isNotProxy).forEach(this::registerAuthenticationMetadataPopulator);
    }

    public void registerAuthenticationPolicy(AuthenticationPolicy authenticationPolicy) {
        if (BeanSupplier.isNotProxy((Object)authenticationPolicy)) {
            this.authenticationPolicies.add(authenticationPolicy);
        }
    }

    public void registerAuthenticationPolicies(Collection<AuthenticationPolicy> authenticationPolicy) {
        this.authenticationPolicies.addAll(authenticationPolicy.stream().filter(BeanSupplier::isNotProxy).collect(Collectors.toList()));
    }

    public void registerAuthenticationHandlerResolver(AuthenticationHandlerResolver handlerResolver) {
        if (BeanSupplier.isNotProxy((Object)handlerResolver)) {
            this.authenticationHandlerResolvers.add(handlerResolver);
        }
    }

    public void registerAuthenticationPolicyResolver(AuthenticationPolicyResolver policyResolver) {
        if (BeanSupplier.isNotProxy((Object)policyResolver)) {
            this.authenticationPolicyResolvers.add(policyResolver);
        }
    }

    public void registerAuthenticationHandlerWithPrincipalResolver(Map<AuthenticationHandler, PrincipalResolver> plan) {
        plan.forEach(this::registerAuthenticationHandlerWithPrincipalResolver);
    }

    public boolean registerAuthenticationHandlerWithPrincipalResolver(AuthenticationHandler handler, PrincipalResolver principalResolver) {
        return (Boolean)FunctionUtils.doIf((boolean)BeanSupplier.isNotProxy((Object)handler), () -> {
            LOGGER.trace("Registering handler [{}] with [{}] principal resolver into the execution plan", (Object)handler.getName(), (Object)Optional.ofNullable(principalResolver).map(PrincipalResolver::getName).orElse("no"));
            if (this.authenticationHandlerPrincipalResolverMap.containsKey(handler)) {
                PrincipalResolver result = this.authenticationHandlerPrincipalResolverMap.get(handler);
                LOGGER.error("Authentication execution plan has found an existing handler [{}]. Attempts to register a new authentication handler [{}] may lead to unpredictable results. Please make sure all authentication handlers are uniquely defined in the CAS configuration.", (Object)result, (Object)handler);
                return false;
            }
            this.authenticationHandlerPrincipalResolverMap.put(handler, principalResolver);
            return true;
        }, () -> false).get();
    }

    public void registerAuthenticationHandlerWithPrincipalResolvers(Collection<AuthenticationHandler> handlers, PrincipalResolver principalResolver) {
        handlers.stream().filter(BeanSupplier::isNotProxy).forEach(h -> this.registerAuthenticationHandlerWithPrincipalResolver((AuthenticationHandler)h, principalResolver));
    }

    public void registerAuthenticationHandlerWithPrincipalResolvers(List<AuthenticationHandler> handlers, List<PrincipalResolver> principalResolver) {
        if (handlers.size() != principalResolver.size()) {
            LOGGER.error("Total number of authentication handlers must match the number of provided principal resolvers");
            return;
        }
        IntStream.range(0, handlers.size()).forEach(i -> this.registerAuthenticationHandlerWithPrincipalResolver((AuthenticationHandler)handlers.get(i), (PrincipalResolver)principalResolver.get(i)));
    }

    @NonNull
    public Set<AuthenticationHandler> getAuthenticationHandlers(AuthenticationTransaction transaction) {
        Set<AuthenticationHandler> handlers = this.getAuthenticationHandlers();
        LOGGER.debug("Candidate/Registered authentication handlers for this transaction are [{}]", handlers);
        Collection<AuthenticationHandlerResolver> handlerResolvers = this.getAuthenticationHandlerResolvers(transaction);
        LOGGER.debug("Authentication handler resolvers for this transaction are [{}]", handlerResolvers);
        LinkedHashSet resolvedHandlers = handlerResolvers.stream().filter(BeanSupplier::isNotProxy).filter(r -> r.supports(handlers, transaction)).map(r -> r.resolve(handlers, transaction)).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
        if (resolvedHandlers.isEmpty()) {
            LOGGER.debug("Authentication handler resolvers produced no candidate authentication handler. Using the default handler resolver instead...");
            DefaultAuthenticationHandlerResolver defaultHandlerResolver = new DefaultAuthenticationHandlerResolver();
            if (defaultHandlerResolver.supports(handlers, transaction)) {
                resolvedHandlers.addAll(defaultHandlerResolver.resolve(handlers, transaction));
            }
        }
        if (resolvedHandlers.isEmpty()) {
            throw new AuthenticationException("No authentication handlers could be resolved to support the authentication transaction");
        }
        LOGGER.debug("Resolved and finalized authentication handlers to carry out this authentication transaction are [{}]", handlerResolvers);
        return resolvedHandlers;
    }

    public Set<AuthenticationHandler> getAuthenticationHandlers() {
        Object[] handlers = (AuthenticationHandler[])this.authenticationHandlerPrincipalResolverMap.keySet().toArray(AuthenticationHandler[]::new);
        AnnotationAwareOrderComparator.sortIfNecessary((Object)handlers);
        return new LinkedHashSet<AuthenticationHandler>(CollectionUtils.wrapList((Object[])handlers));
    }

    public Collection<AuthenticationMetaDataPopulator> getAuthenticationMetadataPopulators(AuthenticationTransaction transaction) {
        ArrayList<AuthenticationMetaDataPopulator> list = new ArrayList<AuthenticationMetaDataPopulator>(this.authenticationMetaDataPopulatorList);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.debug("Sorted and registered metadata populators for this transaction are [{}]", list);
        return list;
    }

    public Collection<AuthenticationPostProcessor> getAuthenticationPostProcessors(AuthenticationTransaction transaction) {
        ArrayList<AuthenticationPostProcessor> list = new ArrayList<AuthenticationPostProcessor>(this.authenticationPostProcessors);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Sorted and registered authentication post processors for this transaction are [{}]", list);
        return list;
    }

    public Collection<AuthenticationPreProcessor> getAuthenticationPreProcessors(AuthenticationTransaction transaction) {
        ArrayList<AuthenticationPreProcessor> list = new ArrayList<AuthenticationPreProcessor>(this.authenticationPreProcessors);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Sorted and registered authentication pre processors for this transaction are [{}]", list);
        return list;
    }

    public PrincipalResolver getPrincipalResolver(AuthenticationHandler handler, AuthenticationTransaction transaction) {
        return this.authenticationHandlerPrincipalResolverMap.get(handler);
    }

    public Collection<AuthenticationPolicy> getAuthenticationPolicies() {
        ArrayList<AuthenticationPolicy> list = new ArrayList<AuthenticationPolicy>(this.authenticationPolicies);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Candidate authentication policies for this transaction are [{}]", list);
        return list;
    }

    public Collection<AuthenticationPolicy> getAuthenticationPolicies(AuthenticationTransaction transaction) {
        Collection<AuthenticationPolicyResolver> handlerResolvers = this.getAuthenticationPolicyResolvers(transaction);
        LOGGER.debug("Authentication policy resolvers for this transaction are [{}]", handlerResolvers);
        Collection<AuthenticationPolicy> list = this.getAuthenticationPolicies();
        LinkedHashSet resolvedPolicies = handlerResolvers.stream().filter(r -> r.supports(transaction)).map(r -> r.resolve(transaction)).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
        if (resolvedPolicies.isEmpty()) {
            LOGGER.debug("Authentication policy resolvers produced no candidate authentication policy. Using default policies");
            return list;
        }
        LOGGER.debug("Resolved authentication policies are [{}]", (Object)resolvedPolicies);
        return resolvedPolicies;
    }

    public Collection<AuthenticationPolicy> getAuthenticationPolicies(Authentication authentication) {
        ArrayList<AuthenticationPolicy> list = new ArrayList<AuthenticationPolicy>(this.authenticationPolicies);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Sorted and registered authentication policies for this assertion are [{}]", list);
        return list;
    }

    public Collection<AuthenticationHandlerResolver> getAuthenticationHandlerResolvers(AuthenticationTransaction transaction) {
        ArrayList<AuthenticationHandlerResolver> list = new ArrayList<AuthenticationHandlerResolver>(this.authenticationHandlerResolvers);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Sorted and registered authentication handler resolvers for this transaction are [{}]", list);
        return list;
    }

    public Collection<AuthenticationPolicyResolver> getAuthenticationPolicyResolvers(AuthenticationTransaction transaction) {
        ArrayList<AuthenticationPolicyResolver> list = new ArrayList<AuthenticationPolicyResolver>(this.authenticationPolicyResolvers);
        AnnotationAwareOrderComparator.sort(list);
        LOGGER.trace("Sorted and registered authentication policy resolvers for this transaction are [{}]", list);
        return list;
    }

    @Generated
    public DefaultAuthenticationEventExecutionPlan() {
    }
}

