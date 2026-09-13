/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.aspectj.lang.JoinPoint;

public class ReturnValueAsStringResourceResolver
implements AuditResourceResolver {
    protected AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;
    protected Function<String[], String[]> resourcePostProcessor = inputs -> inputs;

    @Override
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }

    public void setResourcePostProcessor(Function<String[], String[]> resourcePostProcessor) {
        this.resourcePostProcessor = resourcePostProcessor;
    }

    @Override
    public String[] resolveFrom(JoinPoint auditableTarget, Object retval) {
        if (retval instanceof Collection) {
            Collection c = (Collection)retval;
            String[] retvals = new String[c.size()];
            Iterator iter = c.iterator();
            for (int i = 0; iter.hasNext() && i < c.size(); ++i) {
                Object o = iter.next();
                if (o == null) continue;
                retvals[i] = this.toResourceString(o);
            }
            return retvals;
        }
        if (retval instanceof Object[]) {
            Object[] vals = (Object[])retval;
            String[] retvals = new String[vals.length];
            for (int i = 0; i < vals.length; ++i) {
                retvals[i] = this.toResourceString(vals[i]);
            }
            return retvals;
        }
        return new String[]{this.toResourceString(retval)};
    }

    @Override
    public String[] resolveFrom(JoinPoint auditableTarget, Exception exception) {
        String message = exception.getMessage();
        if (message != null) {
            return new String[]{this.toResourceString(message)};
        }
        return new String[]{this.toResourceString(exception)};
    }

    public String toResourceString(Object arg) {
        if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
            return this.postProcess(AuditTrailManager.toJson(arg));
        }
        return this.postProcess(arg.toString());
    }

    protected String postProcess(String value) {
        return this.resourcePostProcessor.apply(new String[]{value})[0];
    }
}

