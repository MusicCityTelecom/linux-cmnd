/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import org.springframework.integration.graph.MessageHandlerNode;
import org.springframework.messaging.MessageHandler;

public class DiscardingMessageHandlerNode
extends MessageHandlerNode {
    private final String discards;

    public DiscardingMessageHandlerNode(int nodeId, String name, MessageHandler handler, String input, String output, String discards) {
        super(nodeId, name, handler, input, output);
        this.discards = discards;
    }

    public String getDiscards() {
        return this.discards;
    }
}

