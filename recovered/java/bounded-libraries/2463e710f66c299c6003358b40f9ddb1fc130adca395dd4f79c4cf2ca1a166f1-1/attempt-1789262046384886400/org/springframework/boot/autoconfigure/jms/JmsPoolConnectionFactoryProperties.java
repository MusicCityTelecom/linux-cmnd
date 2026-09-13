/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.jms;

import java.time.Duration;

public class JmsPoolConnectionFactoryProperties {
    private boolean enabled;
    private boolean blockIfFull = true;
    private Duration blockIfFullTimeout = Duration.ofMillis(-1L);
    private Duration idleTimeout = Duration.ofSeconds(30L);
    private int maxConnections = 1;
    private int maxSessionsPerConnection = 500;
    private Duration timeBetweenExpirationCheck = Duration.ofMillis(-1L);
    private boolean useAnonymousProducers = true;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBlockIfFull() {
        return this.blockIfFull;
    }

    public void setBlockIfFull(boolean blockIfFull) {
        this.blockIfFull = blockIfFull;
    }

    public Duration getBlockIfFullTimeout() {
        return this.blockIfFullTimeout;
    }

    public void setBlockIfFullTimeout(Duration blockIfFullTimeout) {
        this.blockIfFullTimeout = blockIfFullTimeout;
    }

    public Duration getIdleTimeout() {
        return this.idleTimeout;
    }

    public void setIdleTimeout(Duration idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxSessionsPerConnection() {
        return this.maxSessionsPerConnection;
    }

    public void setMaxSessionsPerConnection(int maxSessionsPerConnection) {
        this.maxSessionsPerConnection = maxSessionsPerConnection;
    }

    public Duration getTimeBetweenExpirationCheck() {
        return this.timeBetweenExpirationCheck;
    }

    public void setTimeBetweenExpirationCheck(Duration timeBetweenExpirationCheck) {
        this.timeBetweenExpirationCheck = timeBetweenExpirationCheck;
    }

    public boolean isUseAnonymousProducers() {
        return this.useAnonymousProducers;
    }

    public void setUseAnonymousProducers(boolean useAnonymousProducers) {
        this.useAnonymousProducers = useAnonymousProducers;
    }
}

