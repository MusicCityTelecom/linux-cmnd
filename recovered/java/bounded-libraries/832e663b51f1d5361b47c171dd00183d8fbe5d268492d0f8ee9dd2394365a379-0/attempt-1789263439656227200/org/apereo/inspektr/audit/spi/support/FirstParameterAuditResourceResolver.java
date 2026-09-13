/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.Arrays;
import java.util.function.Function;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.apereo.inspektr.audit.util.AopUtils;
import org.aspectj.lang.JoinPoint;

public class FirstParameterAuditResourceResolver
implements AuditResourceResolver {
    protected Function<String[], String[]> resourcePostProcessor = inputs -> inputs;
    private String resourceString;
    private AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;

    public void setResourceString(String resourceString) {
        this.resourceString = resourceString;
    }

    public void setResourcePostProcessor(Function<String[], String[]> resourcePostProcessor) {
        this.resourcePostProcessor = resourcePostProcessor;
    }

    @Override
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }

    @Override
    public String[] resolveFrom(JoinPoint joinPoint, Object retval) {
        return this.toResources(this.getArguments(joinPoint));
    }

    @Override
    public String[] resolveFrom(JoinPoint joinPoint, Exception exception) {
        return this.toResources(this.getArguments(joinPoint));
    }

    public String toResourceString(Object arg) {
        if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
            return AuditTrailManager.toJson(arg);
        }
        return this.resourceString != null ? this.resourceString + Arrays.asList(arg) : arg.toString();
    }

    private Object[] getArguments(JoinPoint joinPoint) {
        return AopUtils.unWrapJoinPoint(joinPoint).getArgs();
    }

    private String[] toResources(Object[] args) {
        return this.resourcePostProcessor.apply(new String[]{this.toResourceString(args[0])});
    }
}

