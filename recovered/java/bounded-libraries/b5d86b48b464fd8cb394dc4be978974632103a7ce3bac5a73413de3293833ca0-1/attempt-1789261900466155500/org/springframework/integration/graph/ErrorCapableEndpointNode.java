/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import org.springframework.integration.graph.EndpointNode;
import org.springframework.integration.graph.ErrorCapableNode;

public class ErrorCapableEndpointNode
extends EndpointNode
implements ErrorCapableNode {
    private final String errors;

    protected ErrorCapableEndpointNode(int nodeId, String name, Object nodeObject, String output, String errors) {
        super(nodeId, name, nodeObject, output);
        this.errors = errors;
    }

    @Override
    public String getErrors() {
        return this.errors;
    }
}

