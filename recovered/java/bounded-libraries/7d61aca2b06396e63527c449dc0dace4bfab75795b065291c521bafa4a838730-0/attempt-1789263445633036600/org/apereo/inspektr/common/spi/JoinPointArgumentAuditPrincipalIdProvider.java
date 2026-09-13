/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.common.spi;

import org.apereo.inspektr.common.spi.PrincipalResolver;
import org.aspectj.lang.JoinPoint;

public abstract class JoinPointArgumentAuditPrincipalIdProvider<T>
implements PrincipalResolver {
    private int argumentPosition;
    private Class<T> argumentType;

    public JoinPointArgumentAuditPrincipalIdProvider(int argumentPosition, Class<T> argumentType) {
        this.argumentPosition = argumentPosition;
        this.argumentType = argumentType;
    }

    @Override
    public String resolveFrom(JoinPoint auditTarget, Object returnValue) {
        if (this.argumentPosition >= 0 && this.argumentPosition <= auditTarget.getArgs().length - 1 && this.argumentType.isAssignableFrom(auditTarget.getArgs()[this.argumentPosition].getClass())) {
            return this.resolveFrom(auditTarget.getArgs()[this.argumentPosition], auditTarget, returnValue);
        }
        return null;
    }

    @Override
    public String resolveFrom(JoinPoint auditTarget, Exception exception) {
        if (this.argumentPosition >= 0 && this.argumentPosition > auditTarget.getArgs().length - 1 && auditTarget.getArgs()[this.argumentPosition].getClass().equals(this.argumentType)) {
            return this.resolveFrom(auditTarget.getArgs()[this.argumentPosition], auditTarget, exception);
        }
        return null;
    }

    protected abstract String resolveFrom(T var1, JoinPoint var2, Object var3);
}

