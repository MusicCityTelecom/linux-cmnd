/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.client.spi.ConnectorProvider;

public class CachingConnectorProvider
implements ConnectorProvider {
    private final ConnectorProvider delegate;
    private Connector connector;

    public CachingConnectorProvider(ConnectorProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public synchronized Connector getConnector(Client client, Configuration runtimeConfig) {
        if (this.connector == null) {
            this.connector = this.delegate.getConnector(client, runtimeConfig);
        }
        return this.connector;
    }
}

