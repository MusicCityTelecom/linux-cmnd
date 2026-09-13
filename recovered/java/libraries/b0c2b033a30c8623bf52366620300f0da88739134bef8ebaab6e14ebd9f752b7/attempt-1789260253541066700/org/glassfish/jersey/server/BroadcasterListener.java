/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import org.glassfish.jersey.server.ChunkedOutput;

public interface BroadcasterListener<T> {
    public void onException(ChunkedOutput<T> var1, Exception var2);

    public void onClose(ChunkedOutput<T> var1);
}

