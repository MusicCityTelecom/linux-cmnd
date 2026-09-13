/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlan
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.authentication.principal;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlan;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultPrincipalResolutionExecutionPlan
implements PrincipalResolutionExecutionPlan {
    private final List<PrincipalResolver> registeredPrincipalResolvers = new ArrayList<PrincipalResolver>(0);

    public void registerPrincipalResolver(PrincipalResolver principalResolver) {
        this.registeredPrincipalResolvers.add(principalResolver);
        AnnotationAwareOrderComparator.sortIfNecessary(this.registeredPrincipalResolvers);
    }

    @Generated
    public List<PrincipalResolver> getRegisteredPrincipalResolvers() {
        return this.registeredPrincipalResolvers;
    }
}

