/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.logout;

import org.apereo.cas.logout.LogoutExecutionPlan;

@FunctionalInterface
public interface LogoutExecutionPlanConfigurer {
    public void configureLogoutExecutionPlan(LogoutExecutionPlan var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

