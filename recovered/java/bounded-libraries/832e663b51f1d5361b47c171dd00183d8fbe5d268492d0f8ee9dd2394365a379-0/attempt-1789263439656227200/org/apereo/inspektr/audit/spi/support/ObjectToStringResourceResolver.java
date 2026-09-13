/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.HashMap;
import java.util.function.Function;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.aspectj.lang.JoinPoint;

public class ObjectToStringResourceResolver
implements AuditResourceResolver {
    protected Function<String[], String[]> resourcePostProcessor = inputs -> inputs;
    private AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;

    public void setResourcePostProcessor(Function<String[], String[]> resourcePostProcessor) {
        this.resourcePostProcessor = resourcePostProcessor;
    }

    @Override
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }

    @Override
    public String[] resolveFrom(JoinPoint target, Object returnValue) {
        return this.resourcePostProcessor.apply(new String[]{this.toResourceString(target.getTarget())});
    }

    @Override
    public String[] resolveFrom(JoinPoint target, Exception exception) {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("target", this.toResourceString(target.getTarget()));
        values.put("exception", this.toResourceString(exception.getMessage()));
        return this.resourcePostProcessor.apply(new String[]{this.toResourceString(values)});
    }

    public String toResourceString(Object arg) {
        if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
            return AuditTrailManager.toJson(arg);
        }
        return arg.toString();
    }
}

