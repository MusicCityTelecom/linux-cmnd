/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;

@FunctionalInterface
public interface AuthenticationServiceSelectionStrategyConfigurer {
    public void configureAuthenticationServiceSelectionStrategy(AuthenticationServiceSelectionPlan var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

