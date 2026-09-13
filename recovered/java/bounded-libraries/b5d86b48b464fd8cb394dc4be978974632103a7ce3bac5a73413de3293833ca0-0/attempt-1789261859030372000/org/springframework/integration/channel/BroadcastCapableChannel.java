/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.SubscribableChannel
 */
package org.springframework.integration.channel;

import org.springframework.messaging.SubscribableChannel;

public interface BroadcastCapableChannel
extends SubscribableChannel {
    default public boolean isBroadcast() {
        return true;
    }
}

