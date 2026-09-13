/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import java.util.Collection;
import java.util.Map;
import org.springframework.integration.graph.IntegrationNode;
import org.springframework.integration.graph.LinkNode;

public class Graph {
    private final Map<String, Object> contentDescriptor;
    private final Collection<IntegrationNode> nodes;
    private final Collection<LinkNode> links;

    public Graph(Map<String, Object> descriptor, Collection<IntegrationNode> nodes, Collection<LinkNode> links) {
        this.contentDescriptor = descriptor;
        this.nodes = nodes;
        this.links = links;
    }

    public Map<String, Object> getContentDescriptor() {
        return this.contentDescriptor;
    }

    public Collection<IntegrationNode> getNodes() {
        return this.nodes;
    }

    public Collection<LinkNode> getLinks() {
        return this.links;
    }
}

