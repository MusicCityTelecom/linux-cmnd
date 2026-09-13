/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.process.internal.RequestScope;

interface ResponseCallback {
    public void completed(ClientResponse var1, RequestScope var2);

    public void failed(ProcessingException var1);
}

