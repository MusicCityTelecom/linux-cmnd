/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.rsocket.RSocketStrategies$Builder
 */
package org.springframework.boot.rsocket.messaging;

import org.springframework.messaging.rsocket.RSocketStrategies;

@FunctionalInterface
public interface RSocketStrategiesCustomizer {
    public void customize(RSocketStrategies.Builder var1);
}

