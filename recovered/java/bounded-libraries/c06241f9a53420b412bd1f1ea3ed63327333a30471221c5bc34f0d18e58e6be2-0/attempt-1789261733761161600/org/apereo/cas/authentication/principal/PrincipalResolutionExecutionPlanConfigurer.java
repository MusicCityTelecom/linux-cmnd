/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication.principal;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.PrincipalResolutionExecutionPlan;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface PrincipalResolutionExecutionPlanConfigurer
extends Ordered {
    public void configurePrincipalResolutionExecutionPlan(PrincipalResolutionExecutionPlan var1) throws Exception;

    default public String getName() {
        return (String)StringUtils.defaultIfBlank((CharSequence)this.getClass().getSimpleName(), (CharSequence)"Default");
    }

    default public int getOrder() {
        return 0;
    }
}

