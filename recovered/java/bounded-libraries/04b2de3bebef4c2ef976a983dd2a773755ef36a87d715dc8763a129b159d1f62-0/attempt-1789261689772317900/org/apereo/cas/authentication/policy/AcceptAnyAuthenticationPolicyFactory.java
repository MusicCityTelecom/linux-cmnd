/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.ContextualAuthenticationPolicy
 *  org.apereo.cas.authentication.ContextualAuthenticationPolicyFactory
 */
package org.apereo.cas.authentication.policy;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.ContextualAuthenticationPolicy;
import org.apereo.cas.authentication.ContextualAuthenticationPolicyFactory;
import org.apereo.cas.services.ServiceContext;

public class AcceptAnyAuthenticationPolicyFactory
implements ContextualAuthenticationPolicyFactory<ServiceContext> {
    public ContextualAuthenticationPolicy<ServiceContext> createPolicy(final ServiceContext context) {
        return new ContextualAuthenticationPolicy<ServiceContext>(){

            public ServiceContext getContext() {
                return context;
            }

            public boolean isSatisfiedBy(Authentication authentication) {
                return true;
            }
        };
    }
}

