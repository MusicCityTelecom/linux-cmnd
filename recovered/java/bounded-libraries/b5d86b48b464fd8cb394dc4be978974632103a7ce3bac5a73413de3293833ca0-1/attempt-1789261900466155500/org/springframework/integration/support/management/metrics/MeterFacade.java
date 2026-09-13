/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.support.management.metrics;

import org.springframework.lang.Nullable;

public interface MeterFacade {
    @Nullable
    default public <T extends MeterFacade> T remove() {
        return null;
    }
}

