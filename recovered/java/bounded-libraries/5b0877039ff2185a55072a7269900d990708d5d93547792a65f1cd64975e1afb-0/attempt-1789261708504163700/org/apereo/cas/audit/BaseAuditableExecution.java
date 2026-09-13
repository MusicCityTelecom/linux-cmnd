/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.audit;

import org.apereo.cas.audit.AuditableContext;
import org.apereo.cas.audit.AuditableExecution;
import org.apereo.cas.audit.AuditableExecutionResult;

public abstract class BaseAuditableExecution
implements AuditableExecution {
    @Override
    public AuditableExecutionResult execute(AuditableContext context) {
        return AuditableExecutionResult.of(context);
    }
}

