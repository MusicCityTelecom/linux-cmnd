/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.support.channel;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.MessageChannel;

public interface HeaderChannelRegistry {
    public Object channelToChannelName(Object var1);

    public Object channelToChannelName(Object var1, long var2);

    public MessageChannel channelNameToChannel(String var1);

    @ManagedAttribute
    public int size();

    @ManagedOperation(description="Cancel the scheduled reap task and run immediately; then reschedule.")
    public void runReaper();
}

