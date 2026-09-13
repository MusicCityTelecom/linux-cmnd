/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import java.util.List;
import org.springframework.integration.graph.CompositeMessageHandlerNode;
import org.springframework.integration.graph.ErrorCapableNode;
import org.springframework.integration.handler.CompositeMessageHandler;

public class ErrorCapableCompositeMessageHandlerNode
extends CompositeMessageHandlerNode
implements ErrorCapableNode {
    private final String errors;

    public ErrorCapableCompositeMessageHandlerNode(int nodeId, String name, CompositeMessageHandler handler, String input, String output, String errors, List<CompositeMessageHandlerNode.InnerHandler> handlers) {
        super(nodeId, name, handler, input, output, handlers);
        this.errors = errors;
    }

    @Override
    public String getErrors() {
        return this.errors;
    }
}

