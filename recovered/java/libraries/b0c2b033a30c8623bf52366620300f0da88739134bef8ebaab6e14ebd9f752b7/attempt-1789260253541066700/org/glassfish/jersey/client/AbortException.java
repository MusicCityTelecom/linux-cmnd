/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.client.ClientResponse;

class AbortException
extends ProcessingException {
    private final transient ClientResponse abortResponse;

    AbortException(ClientResponse abortResponse) {
        super("Request processing has been aborted");
        this.abortResponse = abortResponse;
    }

    public ClientResponse getAbortResponse() {
        return this.abortResponse;
    }
}

