/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.integration.graph.Graph
 *  org.springframework.integration.graph.IntegrationGraphServer
 */
package org.springframework.boot.actuate.integration;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.integration.graph.Graph;
import org.springframework.integration.graph.IntegrationGraphServer;

@Endpoint(id="integrationgraph")
public class IntegrationGraphEndpoint {
    private final IntegrationGraphServer graphServer;

    public IntegrationGraphEndpoint(IntegrationGraphServer graphServer) {
        this.graphServer = graphServer;
    }

    @ReadOperation
    public Graph graph() {
        return this.graphServer.getGraph();
    }

    @WriteOperation
    public void rebuild() {
        this.graphServer.rebuild();
    }
}

