/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.client.spi.Connector;

public interface ConnectorProvider {
    public Connector getConnector(Client var1, Configuration var2);
}

