/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.config;

import io.micrometer.core.instrument.config.validate.Validated;
import io.micrometer.core.instrument.config.validate.ValidationException;
import io.micrometer.core.lang.Nullable;

public interface MeterRegistryConfig {
    public String prefix();

    @Nullable
    public String get(String var1);

    default public Validated<?> validate() {
        return Validated.none();
    }

    default public void requireValid() throws ValidationException {
        this.validate().orThrow();
    }
}

