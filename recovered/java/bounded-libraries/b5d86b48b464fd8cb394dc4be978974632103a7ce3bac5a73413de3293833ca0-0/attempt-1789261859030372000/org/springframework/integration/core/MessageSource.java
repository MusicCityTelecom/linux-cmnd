/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.core;

import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageSource<T>
extends IntegrationPattern {
    @Nullable
    public Message<T> receive();

    @Override
    default public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.inbound_channel_adapter;
    }
}

