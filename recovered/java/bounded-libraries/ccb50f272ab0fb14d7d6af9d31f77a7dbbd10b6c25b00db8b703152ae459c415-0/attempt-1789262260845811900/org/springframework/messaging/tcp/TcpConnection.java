/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.concurrent.ListenableFuture
 */
package org.springframework.messaging.tcp;

import java.io.Closeable;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

public interface TcpConnection<P>
extends Closeable {
    public ListenableFuture<Void> send(Message<P> var1);

    public void onReadInactivity(Runnable var1, long var2);

    public void onWriteInactivity(Runnable var1, long var2);

    @Override
    public void close();
}

