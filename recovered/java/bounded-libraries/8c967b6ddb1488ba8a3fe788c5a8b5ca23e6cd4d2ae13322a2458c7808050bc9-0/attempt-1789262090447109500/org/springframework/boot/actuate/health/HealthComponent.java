/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonUnwrapped
 */
package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.boot.actuate.health.Status;

public abstract class HealthComponent {
    HealthComponent() {
    }

    @JsonUnwrapped
    public abstract Status getStatus();
}

