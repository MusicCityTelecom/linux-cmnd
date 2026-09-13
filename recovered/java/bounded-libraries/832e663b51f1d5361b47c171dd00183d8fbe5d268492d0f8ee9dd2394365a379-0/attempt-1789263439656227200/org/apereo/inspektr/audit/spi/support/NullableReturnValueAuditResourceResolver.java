/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.aspectj.lang.JoinPoint
 *  org.springframework.webflow.execution.Event
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.aspectj.lang.JoinPoint;
import org.springframework.webflow.execution.Event;

public class NullableReturnValueAuditResourceResolver
implements AuditResourceResolver {
    private final AuditResourceResolver delegate;
    private AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;
    protected Function<String[], String[]> resourcePostProcessor = inputs -> inputs;

    public NullableReturnValueAuditResourceResolver(AuditResourceResolver delegate) {
        this.delegate = delegate;
    }

    public void setResourcePostProcessor(Function<String[], String[]> resourcePostProcessor) {
        this.resourcePostProcessor = resourcePostProcessor;
    }

    @Override
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }

    @Override
    public String[] resolveFrom(JoinPoint joinPoint, Object o) {
        if (o == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (o instanceof Event) {
            Event event = (Event)Event.class.cast(o);
            String sourceName = event.getSource().getClass().getSimpleName();
            HashMap<String, Object> values = new HashMap<String, Object>();
            values.put("event", event.getId());
            values.put("timestamp", new Date(event.getTimestamp()));
            values.put("source", sourceName);
            if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
                return this.resourcePostProcessor.apply(new String[]{AuditTrailManager.toJson(values)});
            }
            return this.resourcePostProcessor.apply(new String[]{((Object)values).toString()});
        }
        return this.resourcePostProcessor.apply(this.delegate.resolveFrom(joinPoint, o));
    }

    @Override
    public String[] resolveFrom(JoinPoint joinPoint, Exception e) {
        return this.resourcePostProcessor.apply(this.delegate.resolveFrom(joinPoint, e));
    }
}

