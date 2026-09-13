/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import org.glassfish.jersey.client.ClientResponse;

public interface AsyncConnectorCallback {
    public void response(ClientResponse var1);

    public void failure(Throwable var1);
}

