/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.context;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.lifecycle")
public class LifecycleProperties {
    private Duration timeoutPerShutdownPhase = Duration.ofSeconds(30L);

    public Duration getTimeoutPerShutdownPhase() {
        return this.timeoutPerShutdownPhase;
    }

    public void setTimeoutPerShutdownPhase(Duration timeoutPerShutdownPhase) {
        this.timeoutPerShutdownPhase = timeoutPerShutdownPhase;
    }
}

