/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.graph;

import java.util.ArrayList;
import java.util.List;
import org.springframework.integration.graph.MessageHandlerNode;
import org.springframework.messaging.MessageHandler;

public class CompositeMessageHandlerNode
extends MessageHandlerNode {
    private final List<InnerHandler> handlers = new ArrayList<InnerHandler>();

    public CompositeMessageHandlerNode(int nodeId, String name, MessageHandler handler, String input, String output, List<InnerHandler> handlers) {
        super(nodeId, name, handler, input, output);
        this.handlers.addAll(handlers);
    }

    public List<InnerHandler> getHandlers() {
        return this.handlers;
    }

    public static class InnerHandler {
        private final String name;
        private final String type;

        public InnerHandler(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public String getType() {
            return this.type;
        }
    }
}

