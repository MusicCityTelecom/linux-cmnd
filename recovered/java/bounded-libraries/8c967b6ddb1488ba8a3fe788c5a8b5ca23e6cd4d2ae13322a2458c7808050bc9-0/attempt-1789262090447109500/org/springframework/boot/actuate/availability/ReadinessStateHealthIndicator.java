/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.availability.ApplicationAvailability
 *  org.springframework.boot.availability.AvailabilityState
 *  org.springframework.boot.availability.ReadinessState
 */
package org.springframework.boot.actuate.availability;

import org.springframework.boot.actuate.availability.AvailabilityStateHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.ReadinessState;

public class ReadinessStateHealthIndicator
extends AvailabilityStateHealthIndicator {
    public ReadinessStateHealthIndicator(ApplicationAvailability availability) {
        super(availability, ReadinessState.class, statusMappings -> {
            statusMappings.add(ReadinessState.ACCEPTING_TRAFFIC, Status.UP);
            statusMappings.add(ReadinessState.REFUSING_TRAFFIC, Status.OUT_OF_SERVICE);
        });
    }

    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        return applicationAvailability.getReadinessState();
    }
}

