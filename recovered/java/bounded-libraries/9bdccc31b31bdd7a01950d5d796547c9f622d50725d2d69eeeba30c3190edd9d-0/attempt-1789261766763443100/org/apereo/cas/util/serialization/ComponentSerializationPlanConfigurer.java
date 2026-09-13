/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.serialization;

import org.apereo.cas.util.serialization.ComponentSerializationPlan;

@FunctionalInterface
public interface ComponentSerializationPlanConfigurer {
    public void configureComponentSerializationPlan(ComponentSerializationPlan var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

