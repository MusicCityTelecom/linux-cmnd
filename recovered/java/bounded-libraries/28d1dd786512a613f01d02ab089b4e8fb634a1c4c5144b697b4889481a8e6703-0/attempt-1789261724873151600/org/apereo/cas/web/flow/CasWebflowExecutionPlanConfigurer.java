/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.web.flow;

import org.apereo.cas.web.flow.CasWebflowExecutionPlan;

@FunctionalInterface
public interface CasWebflowExecutionPlanConfigurer {
    public void configureWebflowExecutionPlan(CasWebflowExecutionPlan var1);
}

