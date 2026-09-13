/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.function.Function;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.aspectj.lang.JoinPoint;

public abstract class AbstractAuditResourceResolver
implements AuditResourceResolver {
    protected AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;
    protected Function<String[], String[]> resourcePostProcessor = inputs -> inputs;

    public void setResourcePostProcessor(Function<String[], String[]> resourcePostProcessor) {
        this.resourcePostProcessor = resourcePostProcessor;
    }

    @Override
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }

    @Override
    public final String[] resolveFrom(JoinPoint joinPoint, Object retVal) {
        return this.createResource(joinPoint.getArgs());
    }

    @Override
    public final String[] resolveFrom(JoinPoint joinPoint, Exception e) {
        return this.resourcePostProcessor.apply(this.createResource(joinPoint.getArgs()));
    }

    protected abstract String[] createResource(Object[] var1);
}

