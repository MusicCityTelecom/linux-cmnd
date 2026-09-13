/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.MultifactorAuthenticationHandler
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicy
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.UnauthorizedSsoServiceException
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.handler;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.MultifactorAuthenticationHandler;
import org.apereo.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.UnauthorizedSsoServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisteredServiceAuthenticationHandlerResolver
implements AuthenticationHandlerResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceAuthenticationHandlerResolver.class);
    protected final ServicesManager servicesManager;
    protected final AuthenticationServiceSelectionPlan authenticationServiceSelectionPlan;
    private int order;

    private static Set<AuthenticationHandler> filterExcludedAuthenticationHandlers(Set<AuthenticationHandler> candidateHandlers, Service service, RegisteredService registeredService) {
        RegisteredServiceAuthenticationPolicy authenticationPolicy = registeredService.getAuthenticationPolicy();
        Set excludedHandlers = authenticationPolicy.getExcludedAuthenticationHandlers();
        LOGGER.debug("Authentication transaction excludes [{}] for service [{}]", (Object)excludedHandlers, (Object)service);
        LinkedHashSet<AuthenticationHandler> handlerSet = new LinkedHashSet<AuthenticationHandler>(candidateHandlers);
        LOGGER.debug("Candidate authentication handlers examined for this transaction are [{}]", handlerSet);
        if (!excludedHandlers.isEmpty()) {
            Iterator it = handlerSet.iterator();
            while (it.hasNext()) {
                AuthenticationHandler handler = (AuthenticationHandler)it.next();
                String handlerName = handler.getName();
                if (!excludedHandlers.contains(handlerName)) continue;
                LOGGER.debug("Authentication handler [{}] is excluded for this transaction and is removed", (Object)handlerName);
                it.remove();
            }
        }
        LOGGER.info("Final authentication handlers after exclusion rules are [{}]", handlerSet);
        return handlerSet;
    }

    private static Set<AuthenticationHandler> filterRequiredAuthenticationHandlers(Set<AuthenticationHandler> candidateHandlers, Service service, RegisteredService registeredService) {
        RegisteredServiceAuthenticationPolicy authenticationPolicy = registeredService.getAuthenticationPolicy();
        Set requiredHandlers = authenticationPolicy.getRequiredAuthenticationHandlers();
        LOGGER.debug("Authentication transaction requires [{}] for service [{}]", (Object)requiredHandlers, (Object)service);
        LinkedHashSet<AuthenticationHandler> handlerSet = new LinkedHashSet<AuthenticationHandler>(candidateHandlers);
        LOGGER.debug("Candidate authentication handlers examined for this transaction are [{}]", handlerSet);
        if (!requiredHandlers.isEmpty()) {
            Iterator it = handlerSet.iterator();
            while (it.hasNext()) {
                AuthenticationHandler handler = (AuthenticationHandler)it.next();
                String handlerName = handler.getName();
                boolean removeHandler = !(handler instanceof MultifactorAuthenticationHandler) && !(handler instanceof HttpBasedServiceCredentialsAuthenticationHandler) && !requiredHandlers.contains(handlerName);
                if (!removeHandler) continue;
                it.remove();
                LOGGER.debug("Authentication handler [{}] is removed", (Object)handlerName);
            }
        }
        LOGGER.info("Final authentication handlers after inclusion rules are [{}]", handlerSet);
        return handlerSet;
    }

    public Set<AuthenticationHandler> resolve(Set<AuthenticationHandler> candidateHandlers, AuthenticationTransaction transaction) {
        Service service = this.authenticationServiceSelectionPlan.resolveService(transaction.getService());
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        Set<AuthenticationHandler> requiredHandlers = RegisteredServiceAuthenticationHandlerResolver.filterRequiredAuthenticationHandlers(candidateHandlers, service, registeredService);
        return RegisteredServiceAuthenticationHandlerResolver.filterExcludedAuthenticationHandlers(requiredHandlers, service, registeredService);
    }

    public boolean supports(Set<AuthenticationHandler> handlers, AuthenticationTransaction transaction) {
        Service service = this.authenticationServiceSelectionPlan.resolveService(transaction.getService());
        if (service != null) {
            RegisteredService registeredService = this.servicesManager.findServiceBy(service);
            LOGGER.trace("Located registered service definition [{}] for this authentication transaction", (Object)registeredService);
            if (registeredService == null || !registeredService.getAccessStrategy().isServiceAccessAllowed()) {
                LOGGER.warn("Service [{}] is not allowed to use SSO.", (Object)service);
                throw new UnauthorizedSsoServiceException();
            }
            RegisteredServiceAuthenticationPolicy authenticationPolicy = registeredService.getAuthenticationPolicy();
            return !authenticationPolicy.getRequiredAuthenticationHandlers().isEmpty() || !authenticationPolicy.getExcludedAuthenticationHandlers().isEmpty();
        }
        return false;
    }

    @Generated
    public RegisteredServiceAuthenticationHandlerResolver(ServicesManager servicesManager, AuthenticationServiceSelectionPlan authenticationServiceSelectionPlan) {
        this.servicesManager = servicesManager;
        this.authenticationServiceSelectionPlan = authenticationServiceSelectionPlan;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public AuthenticationServiceSelectionPlan getAuthenticationServiceSelectionPlan() {
        return this.authenticationServiceSelectionPlan;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

