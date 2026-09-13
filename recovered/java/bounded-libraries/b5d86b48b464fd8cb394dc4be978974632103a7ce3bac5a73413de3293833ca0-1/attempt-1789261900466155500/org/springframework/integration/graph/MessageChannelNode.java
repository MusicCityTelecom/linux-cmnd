/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.graph;

import java.util.function.Supplier;
import org.springframework.integration.graph.IntegrationNode;
import org.springframework.integration.graph.SendTimers;
import org.springframework.integration.graph.SendTimersAware;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;

public class MessageChannelNode
extends IntegrationNode
implements SendTimersAware {
    private Supplier<SendTimers> sendTimers;

    public MessageChannelNode(int nodeId, String name, MessageChannel channel) {
        super(nodeId, name, channel);
    }

    @Nullable
    public SendTimers getSendTimers() {
        return this.sendTimers != null ? this.sendTimers.get() : null;
    }

    @Override
    public void sendTimers(Supplier<SendTimers> timers) {
        this.sendTimers = timers;
    }
}

