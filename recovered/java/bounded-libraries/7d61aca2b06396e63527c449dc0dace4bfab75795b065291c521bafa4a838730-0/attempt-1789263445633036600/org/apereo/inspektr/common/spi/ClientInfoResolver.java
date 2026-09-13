/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.common.spi;

import org.apereo.inspektr.common.web.ClientInfo;
import org.aspectj.lang.JoinPoint;

public interface ClientInfoResolver {
    public ClientInfo resolveFrom(JoinPoint var1, Object var2);
}

