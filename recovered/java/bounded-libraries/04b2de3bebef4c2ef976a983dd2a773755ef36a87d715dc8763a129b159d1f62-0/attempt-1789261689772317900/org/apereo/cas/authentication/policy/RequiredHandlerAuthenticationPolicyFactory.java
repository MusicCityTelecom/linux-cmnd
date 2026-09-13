/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.ContextualAuthenticationPolicy
 *  org.apereo.cas.authentication.ContextualAuthenticationPolicyFactory
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.policy;

import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.ContextualAuthenticationPolicy;
import org.apereo.cas.authentication.ContextualAuthenticationPolicyFactory;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequiredHandlerAuthenticationPolicyFactory
implements ContextualAuthenticationPolicyFactory<ServiceContext> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredHandlerAuthenticationPolicyFactory.class);

    public ContextualAuthenticationPolicy<ServiceContext> createPolicy(final ServiceContext context) {
        return new ContextualAuthenticationPolicy<ServiceContext>(){

            public ServiceContext getContext() {
                return context;
            }

            public boolean isSatisfiedBy(Authentication authentication) {
                RegisteredService registeredService = context.getRegisteredService();
                Set requiredHandlers = registeredService.getAuthenticationPolicy().getRequiredAuthenticationHandlers();
                LOGGER.debug("Required authentication handlers for this service [{}] are [{}]", (Object)registeredService.getName(), (Object)requiredHandlers);
                return requiredHandlers.stream().allMatch(required -> authentication.getSuccesses().containsKey(required));
            }
        };
    }
}

