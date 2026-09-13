/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.availability;

import org.springframework.boot.availability.AvailabilityState;

public enum ReadinessState implements AvailabilityState
{
    ACCEPTING_TRAFFIC,
    REFUSING_TRAFFIC;

}

