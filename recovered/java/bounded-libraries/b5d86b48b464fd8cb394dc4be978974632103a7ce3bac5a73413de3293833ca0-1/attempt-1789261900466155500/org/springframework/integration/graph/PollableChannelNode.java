/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.graph;

import java.util.function.Supplier;
import org.springframework.integration.graph.MessageChannelNode;
import org.springframework.integration.graph.ReceiveCounters;
import org.springframework.integration.graph.ReceiveCountersAware;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;

public class PollableChannelNode
extends MessageChannelNode
implements ReceiveCountersAware {
    private Supplier<ReceiveCounters> receiveCounters;

    public PollableChannelNode(int nodeId, String name, MessageChannel channel) {
        super(nodeId, name, channel);
    }

    @Nullable
    public ReceiveCounters getReceiveCounters() {
        return this.receiveCounters != null ? this.receiveCounters.get() : null;
    }

    @Override
    public void receiveCounters(Supplier<ReceiveCounters> counters) {
        this.receiveCounters = counters;
    }
}

