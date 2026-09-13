/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi;

import org.apereo.inspektr.audit.AuditTrailManager;
import org.aspectj.lang.JoinPoint;

public interface AuditResourceResolver {
    public String[] resolveFrom(JoinPoint var1, Object var2);

    public String[] resolveFrom(JoinPoint var1, Exception var2);

    default public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
    }
}

