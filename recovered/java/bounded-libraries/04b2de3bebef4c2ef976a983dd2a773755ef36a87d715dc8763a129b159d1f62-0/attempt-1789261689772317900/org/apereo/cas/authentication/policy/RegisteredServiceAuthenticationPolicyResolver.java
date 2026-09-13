/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.AuthenticationPolicyResolver
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.UnauthorizedSsoServiceException
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.policy;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.AuthenticationPolicyResolver;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicy;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.UnauthorizedSsoServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisteredServiceAuthenticationPolicyResolver
implements AuthenticationPolicyResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceAuthenticationPolicyResolver.class);
    protected final ServicesManager servicesManager;
    protected final AuthenticationServiceSelectionPlan authenticationServiceSelectionPlan;
    private int order;

    public Set<AuthenticationPolicy> resolve(AuthenticationTransaction transaction) {
        Service service = this.authenticationServiceSelectionPlan.resolveService(transaction.getService());
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        RegisteredServiceAuthenticationPolicyCriteria criteria = registeredService.getAuthenticationPolicy().getCriteria();
        LinkedHashSet<AuthenticationPolicy> policies = new LinkedHashSet<AuthenticationPolicy>(1);
        if (criteria != null) {
            policies.add(criteria.toAuthenticationPolicy(registeredService));
        }
        LOGGER.debug("Authentication policies for this transaction are [{}]", policies);
        return policies;
    }

    public boolean supports(AuthenticationTransaction transaction) {
        Service service = this.authenticationServiceSelectionPlan.resolveService(transaction.getService());
        if (service != null) {
            RegisteredService registeredService = this.servicesManager.findServiceBy(service);
            LOGGER.trace("Located registered service definition [{}] for this authentication transaction", (Object)registeredService);
            if (registeredService == null || !registeredService.getAccessStrategy().isServiceAccessAllowed()) {
                LOGGER.warn("Service [{}] is not allowed to use SSO.", (Object)service);
                throw new UnauthorizedSsoServiceException();
            }
            RegisteredServiceAuthenticationPolicy authenticationPolicy = registeredService.getAuthenticationPolicy();
            if (authenticationPolicy != null) {
                RegisteredServiceAuthenticationPolicyCriteria criteria = authenticationPolicy.getCriteria();
                return criteria != null;
            }
        }
        return false;
    }

    @Generated
    public RegisteredServiceAuthenticationPolicyResolver(ServicesManager servicesManager, AuthenticationServiceSelectionPlan authenticationServiceSelectionPlan) {
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

