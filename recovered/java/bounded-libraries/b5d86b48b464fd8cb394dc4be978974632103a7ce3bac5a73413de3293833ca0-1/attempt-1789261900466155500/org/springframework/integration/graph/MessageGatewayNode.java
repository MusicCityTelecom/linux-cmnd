/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.graph.ErrorCapableEndpointNode;

public class MessageGatewayNode
extends ErrorCapableEndpointNode {
    public MessageGatewayNode(int nodeId, String name, MessagingGatewaySupport gateway, String output, String errors) {
        super(nodeId, name, gateway, output, errors);
    }
}

