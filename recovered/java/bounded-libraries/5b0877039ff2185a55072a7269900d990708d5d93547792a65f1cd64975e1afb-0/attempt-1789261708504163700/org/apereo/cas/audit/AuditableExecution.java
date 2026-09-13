/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.audit;

import org.apereo.cas.audit.AuditableContext;
import org.apereo.cas.audit.AuditableExecutionResult;

@FunctionalInterface
public interface AuditableExecution {
    public static final String AUDITABLE_EXECUTION_REGISTERED_SERVICE_ACCESS = "registeredServiceAccessStrategyEnforcer";
    public static final String AUDITABLE_EXECUTION_DELEGATED_AUTHENTICATION_ACCESS = "registeredServiceDelegatedAuthenticationPolicyAuditableEnforcer";

    public AuditableExecutionResult execute(AuditableContext var1);
}

