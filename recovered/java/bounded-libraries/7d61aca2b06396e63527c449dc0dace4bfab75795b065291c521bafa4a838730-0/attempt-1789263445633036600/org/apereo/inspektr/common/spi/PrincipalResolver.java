/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.common.spi;

import org.aspectj.lang.JoinPoint;

public interface PrincipalResolver {
    public static final String ANONYMOUS_USER = "audit:anonymous";
    public static final String UNKNOWN_USER = "audit:unknown";

    public String resolveFrom(JoinPoint var1, Object var2);

    public String resolveFrom(JoinPoint var1, Exception var2);

    default public String resolve() {
        return UNKNOWN_USER;
    }
}

