/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import java.util.function.Supplier;
import org.springframework.integration.graph.EndpointNode;
import org.springframework.integration.graph.SendTimers;
import org.springframework.integration.graph.SendTimersAware;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHandler;

public class MessageHandlerNode
extends EndpointNode
implements SendTimersAware {
    private final String input;
    private Supplier<SendTimers> sendTimers;

    public MessageHandlerNode(int nodeId, String name, MessageHandler handler, String input, String output) {
        super(nodeId, name, handler, output);
        this.input = input;
    }

    public String getInput() {
        return this.input;
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

