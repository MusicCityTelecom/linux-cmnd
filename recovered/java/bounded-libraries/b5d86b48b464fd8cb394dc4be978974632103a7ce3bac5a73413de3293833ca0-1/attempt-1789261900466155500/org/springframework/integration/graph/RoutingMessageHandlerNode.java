/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import java.util.Collection;
import org.springframework.integration.graph.MessageHandlerNode;
import org.springframework.messaging.MessageHandler;

public class RoutingMessageHandlerNode
extends MessageHandlerNode {
    private final Collection<String> routes;

    public RoutingMessageHandlerNode(int nodeId, String name, MessageHandler handler, String input, String output, Collection<String> routes) {
        super(nodeId, name, handler, input, output);
        this.routes = routes;
    }

    public Collection<String> getRoutes() {
        return this.routes;
    }
}

