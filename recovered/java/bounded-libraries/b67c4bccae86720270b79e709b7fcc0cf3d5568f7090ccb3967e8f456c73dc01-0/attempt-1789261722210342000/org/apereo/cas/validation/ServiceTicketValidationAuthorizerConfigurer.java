/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.validation;

import org.apereo.cas.validation.ServiceTicketValidationAuthorizersExecutionPlan;

@FunctionalInterface
public interface ServiceTicketValidationAuthorizerConfigurer {
    public void configureAuthorizersExecutionPlan(ServiceTicketValidationAuthorizersExecutionPlan var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

