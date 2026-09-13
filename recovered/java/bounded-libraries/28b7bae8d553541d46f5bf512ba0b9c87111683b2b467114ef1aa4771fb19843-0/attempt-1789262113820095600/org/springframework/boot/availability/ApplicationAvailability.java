/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.availability;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;

public interface ApplicationAvailability {
    default public LivenessState getLivenessState() {
        return this.getState(LivenessState.class, LivenessState.BROKEN);
    }

    default public ReadinessState getReadinessState() {
        return this.getState(ReadinessState.class, ReadinessState.REFUSING_TRAFFIC);
    }

    public <S extends AvailabilityState> S getState(Class<S> var1, S var2);

    public <S extends AvailabilityState> S getState(Class<S> var1);

    public <S extends AvailabilityState> AvailabilityChangeEvent<S> getLastChangeEvent(Class<S> var1);
}

