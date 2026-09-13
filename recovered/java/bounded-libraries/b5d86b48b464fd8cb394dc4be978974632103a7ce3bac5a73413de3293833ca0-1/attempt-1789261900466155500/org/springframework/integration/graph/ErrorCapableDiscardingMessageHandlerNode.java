/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import org.springframework.integration.graph.DiscardingMessageHandlerNode;
import org.springframework.integration.graph.ErrorCapableNode;
import org.springframework.messaging.MessageHandler;

public class ErrorCapableDiscardingMessageHandlerNode
extends DiscardingMessageHandlerNode
implements ErrorCapableNode {
    private final String errors;

    public ErrorCapableDiscardingMessageHandlerNode(int nodeId, String name, MessageHandler handler, String input, String output, String discards, String errors) {
        super(nodeId, name, handler, input, output, discards);
        this.errors = errors;
    }

    @Override
    public String getErrors() {
        return this.errors;
    }
}

