/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import org.springframework.integration.graph.ErrorCapableNode;
import org.springframework.integration.graph.MessageHandlerNode;
import org.springframework.messaging.MessageHandler;

public class ErrorCapableMessageHandlerNode
extends MessageHandlerNode
implements ErrorCapableNode {
    private final String errors;

    public ErrorCapableMessageHandlerNode(int nodeId, String name, MessageHandler handler, String input, String output, String errors) {
        super(nodeId, name, handler, input, output);
        this.errors = errors;
    }

    @Override
    public String getErrors() {
        return this.errors;
    }
}

