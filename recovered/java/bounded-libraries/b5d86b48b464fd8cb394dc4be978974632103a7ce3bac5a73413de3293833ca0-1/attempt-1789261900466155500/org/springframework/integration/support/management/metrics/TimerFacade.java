/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.management.metrics;

import java.util.concurrent.TimeUnit;
import org.springframework.integration.support.management.metrics.MeterFacade;

public interface TimerFacade
extends MeterFacade {
    public void record(long var1, TimeUnit var3);
}

