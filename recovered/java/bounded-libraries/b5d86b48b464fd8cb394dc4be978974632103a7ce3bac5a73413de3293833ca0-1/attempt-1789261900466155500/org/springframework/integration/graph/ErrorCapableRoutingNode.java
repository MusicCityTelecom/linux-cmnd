/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import java.util.Collection;
import org.springframework.integration.graph.ErrorCapableNode;
import org.springframework.integration.graph.RoutingMessageHandlerNode;
import org.springframework.messaging.MessageHandler;

public class ErrorCapableRoutingNode
extends RoutingMessageHandlerNode
implements ErrorCapableNode {
    private final String errors;

    public ErrorCapableRoutingNode(int nodeId, String name, MessageHandler handler, String input, String output, String errors, Collection<String> routes) {
        super(nodeId, name, handler, input, output, routes);
        this.errors = errors;
    }

    @Override
    public String getErrors() {
        return this.errors;
    }
}

