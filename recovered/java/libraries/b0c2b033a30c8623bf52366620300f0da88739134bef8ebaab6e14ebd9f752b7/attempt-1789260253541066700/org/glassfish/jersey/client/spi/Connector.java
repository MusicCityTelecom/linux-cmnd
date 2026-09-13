/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import java.util.concurrent.Future;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.process.Inflector;

public interface Connector
extends Inflector<ClientRequest, ClientResponse> {
    @Override
    public ClientResponse apply(ClientRequest var1);

    public Future<?> apply(ClientRequest var1, AsyncConnectorCallback var2);

    public String getName();

    public void close();
}

