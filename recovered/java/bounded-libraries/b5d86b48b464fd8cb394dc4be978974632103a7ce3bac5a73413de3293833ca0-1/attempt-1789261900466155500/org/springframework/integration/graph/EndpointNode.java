/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import org.springframework.integration.graph.IntegrationNode;

public abstract class EndpointNode
extends IntegrationNode {
    private final String output;

    protected EndpointNode(int nodeId, String name, Object nodeObject, String output) {
        super(nodeId, name, nodeObject);
        this.output = output;
    }

    public String getOutput() {
        return this.output;
    }
}

