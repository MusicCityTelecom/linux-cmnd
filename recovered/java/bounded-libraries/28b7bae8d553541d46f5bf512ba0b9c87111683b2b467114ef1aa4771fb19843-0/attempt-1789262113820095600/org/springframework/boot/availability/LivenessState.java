/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.availability;

import org.springframework.boot.availability.AvailabilityState;

public enum LivenessState implements AvailabilityState
{
    CORRECT,
    BROKEN;

}

