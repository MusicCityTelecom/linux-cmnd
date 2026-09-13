/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.spi.support;

import org.apereo.inspektr.audit.spi.AuditActionResolver;

public abstract class AbstractSuffixAwareAuditActionResolver
implements AuditActionResolver {
    private final String successSuffix;
    private final String failureSuffix;

    protected AbstractSuffixAwareAuditActionResolver(String successSuffix, String failureSuffix) {
        if (successSuffix == null) {
            throw new IllegalArgumentException("successSuffix cannot be null.");
        }
        if (failureSuffix == null) {
            throw new IllegalArgumentException("failureSuffix cannot be null.");
        }
        this.successSuffix = successSuffix;
        this.failureSuffix = failureSuffix;
    }

    protected final String getSuccessSuffix() {
        return this.successSuffix;
    }

    protected final String getFailureSuffix() {
        return this.failureSuffix;
    }
}

