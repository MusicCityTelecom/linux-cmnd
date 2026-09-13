/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.management.metrics;

import org.springframework.integration.support.management.metrics.MeterFacade;

public interface CounterFacade
extends MeterFacade {
    public void increment();
}

