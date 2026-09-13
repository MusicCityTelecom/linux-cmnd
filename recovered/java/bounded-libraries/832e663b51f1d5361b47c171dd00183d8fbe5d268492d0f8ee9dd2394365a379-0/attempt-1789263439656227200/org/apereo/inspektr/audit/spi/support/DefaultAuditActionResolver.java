/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi.support;

import org.apereo.inspektr.audit.annotation.Audit;
import org.apereo.inspektr.audit.spi.support.AbstractSuffixAwareAuditActionResolver;
import org.aspectj.lang.JoinPoint;

public class DefaultAuditActionResolver
extends AbstractSuffixAwareAuditActionResolver {
    public DefaultAuditActionResolver() {
        this("", "");
    }

    public DefaultAuditActionResolver(String successSuffix) {
        super(successSuffix, "");
    }

    public DefaultAuditActionResolver(String successSuffix, String failureSuffix) {
        super(successSuffix, failureSuffix);
    }

    @Override
    public String resolveFrom(JoinPoint auditableTarget, Object retval, Audit audit) {
        return audit.action() + this.getSuccessSuffix();
    }

    @Override
    public String resolveFrom(JoinPoint auditableTarget, Exception exception, Audit audit) {
        return audit.action() + this.getFailureSuffix();
    }
}

