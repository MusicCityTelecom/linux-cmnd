/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi;

import org.apereo.inspektr.audit.annotation.Audit;
import org.aspectj.lang.JoinPoint;

public interface AuditActionResolver {
    public String resolveFrom(JoinPoint var1, Object var2, Audit var3);

    public String resolveFrom(JoinPoint var1, Exception var2, Audit var3);
}

