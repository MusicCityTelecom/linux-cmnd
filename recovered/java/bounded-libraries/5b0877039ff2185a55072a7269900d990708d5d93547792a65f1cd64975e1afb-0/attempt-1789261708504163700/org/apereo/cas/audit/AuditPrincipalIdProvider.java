/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.aspectj.lang.JoinPoint
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.audit;

import org.apereo.cas.authentication.Authentication;
import org.aspectj.lang.JoinPoint;
import org.springframework.core.Ordered;

public interface AuditPrincipalIdProvider
extends Ordered {
    public String getPrincipalIdFrom(JoinPoint var1, Authentication var2, Object var3, Exception var4);

    public boolean supports(JoinPoint var1, Authentication var2, Object var3, Exception var4);
}

