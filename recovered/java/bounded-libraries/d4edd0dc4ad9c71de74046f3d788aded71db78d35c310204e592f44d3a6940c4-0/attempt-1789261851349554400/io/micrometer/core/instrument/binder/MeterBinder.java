/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.binder;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.lang.NonNull;

public interface MeterBinder {
    public void bindTo(@NonNull MeterRegistry var1);
}

