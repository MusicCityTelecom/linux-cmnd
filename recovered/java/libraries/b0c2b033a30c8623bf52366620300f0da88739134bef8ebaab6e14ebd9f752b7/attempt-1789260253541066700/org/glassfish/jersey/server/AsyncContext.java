/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.util.Producer;

public interface AsyncContext
extends AsyncResponse {
    public boolean suspend();

    public void invokeManaged(Producer<Response> var1);

    public static enum State {
        RUNNING,
        SUSPENDED,
        RESUMED,
        COMPLETED;

    }
}

