/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.graph;

import java.util.function.Supplier;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.graph.ErrorCapableEndpointNode;
import org.springframework.integration.graph.ReceiveCounters;
import org.springframework.integration.graph.ReceiveCountersAware;
import org.springframework.lang.Nullable;

public class MessageSourceNode
extends ErrorCapableEndpointNode
implements ReceiveCountersAware {
    private Supplier<ReceiveCounters> receiveCounters;

    public MessageSourceNode(int nodeId, String name, MessageSource<?> messageSource, String output, String errors) {
        super(nodeId, name, messageSource, output, errors);
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

