/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.principal;

import java.util.Collection;
import org.apereo.cas.authentication.principal.PrincipalResolver;

public interface PrincipalResolutionExecutionPlan {
    public void registerPrincipalResolver(PrincipalResolver var1);

    public Collection<PrincipalResolver> getRegisteredPrincipalResolvers();
}

