/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.availability.ApplicationAvailability
 *  org.springframework.boot.availability.AvailabilityState
 *  org.springframework.boot.availability.LivenessState
 */
package org.springframework.boot.actuate.availability;

import org.springframework.boot.actuate.availability.AvailabilityStateHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;

public class LivenessStateHealthIndicator
extends AvailabilityStateHealthIndicator {
    public LivenessStateHealthIndicator(ApplicationAvailability availability) {
        super(availability, LivenessState.class, statusMappings -> {
            statusMappings.add(LivenessState.CORRECT, Status.UP);
            statusMappings.add(LivenessState.BROKEN, Status.DOWN);
        });
    }

    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        return applicationAvailability.getLivenessState();
    }
}

