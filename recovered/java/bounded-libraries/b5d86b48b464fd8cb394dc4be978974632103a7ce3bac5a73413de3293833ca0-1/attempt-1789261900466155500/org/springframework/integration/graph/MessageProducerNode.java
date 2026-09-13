/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.graph.ErrorCapableEndpointNode;

public class MessageProducerNode
extends ErrorCapableEndpointNode {
    public MessageProducerNode(int nodeId, String name, MessageProducerSupport producer, String output, String errors) {
        super(nodeId, name, producer, output, errors);
    }
}

