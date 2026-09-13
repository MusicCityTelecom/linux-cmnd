/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.channel;

import java.util.List;
import org.springframework.integration.core.MessageSelector;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public interface QueueChannelOperations {
    public List<Message<?>> clear();

    public List<Message<?>> purge(@Nullable MessageSelector var1);

    @ManagedAttribute(description="Queue size")
    public int getQueueSize();

    @ManagedAttribute(description="Queue remaining capacity")
    public int getRemainingCapacity();
}

