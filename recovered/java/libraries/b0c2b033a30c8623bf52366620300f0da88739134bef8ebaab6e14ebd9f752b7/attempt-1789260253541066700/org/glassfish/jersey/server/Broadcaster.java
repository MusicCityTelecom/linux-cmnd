/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.BroadcasterListener;
import org.glassfish.jersey.server.ChunkedOutput;
import org.glassfish.jersey.server.internal.LocalizationMessages;

public class Broadcaster<T>
implements BroadcasterListener<T> {
    private final CopyOnWriteArrayList<BroadcasterListener<T>> listeners = new CopyOnWriteArrayList();
    private final ConcurrentLinkedQueue<ChunkedOutput<T>> chunkedOutputs = new ConcurrentLinkedQueue();

    public Broadcaster() {
        this(Broadcaster.class);
    }

    protected Broadcaster(Class<? extends Broadcaster> subclass) {
        if (subclass != this.getClass()) {
            this.listeners.add(this);
        }
    }

    public <OUT extends ChunkedOutput<T>> boolean add(OUT chunkedOutput) {
        return this.chunkedOutputs.offer(chunkedOutput);
    }

    public <OUT extends ChunkedOutput<T>> boolean remove(OUT chunkedOutput) {
        return this.chunkedOutputs.remove(chunkedOutput);
    }

    public boolean add(BroadcasterListener<T> listener) {
        return this.listeners.add(listener);
    }

    public boolean remove(BroadcasterListener<T> listener) {
        return this.listeners.remove(listener);
    }

    public void broadcast(final T chunk) {
        this.forEachOutput(new Task<ChunkedOutput<T>>(){

            @Override
            public void run(ChunkedOutput<T> cr) throws IOException {
                cr.write(chunk);
            }
        });
    }

    public void closeAll() {
        this.forEachOutput(new Task<ChunkedOutput<T>>(){

            @Override
            public void run(ChunkedOutput<T> cr) throws IOException {
                cr.close();
            }
        });
    }

    @Override
    public void onException(ChunkedOutput<T> chunkedOutput, Exception exception) {
    }

    @Override
    public void onClose(ChunkedOutput<T> chunkedOutput) {
    }

    private void forEachOutput(Task<ChunkedOutput<T>> t) {
        Iterator<ChunkedOutput<T>> iterator = this.chunkedOutputs.iterator();
        while (iterator.hasNext()) {
            ChunkedOutput<T> chunkedOutput = iterator.next();
            if (!chunkedOutput.isClosed()) {
                try {
                    t.run(chunkedOutput);
                }
                catch (Exception e) {
                    this.fireOnException(chunkedOutput, e);
                }
            }
            if (!chunkedOutput.isClosed()) continue;
            iterator.remove();
            this.fireOnClose(chunkedOutput);
        }
    }

    private void forEachListener(Task<BroadcasterListener<T>> t) {
        for (BroadcasterListener<T> listener : this.listeners) {
            try {
                t.run(listener);
            }
            catch (Exception e) {
                Logger.getLogger(Broadcaster.class.getName()).log(Level.WARNING, LocalizationMessages.BROADCASTER_LISTENER_EXCEPTION(e.getClass().getSimpleName()), e);
            }
        }
    }

    private void fireOnException(final ChunkedOutput<T> chunkedOutput, final Exception exception) {
        this.forEachListener(new Task<BroadcasterListener<T>>(){

            @Override
            public void run(BroadcasterListener<T> parameter) throws IOException {
                parameter.onException(chunkedOutput, exception);
            }
        });
    }

    private void fireOnClose(final ChunkedOutput<T> chunkedOutput) {
        this.forEachListener(new Task<BroadcasterListener<T>>(){

            @Override
            public void run(BroadcasterListener<T> parameter) throws IOException {
                parameter.onClose(chunkedOutput);
            }
        });
    }

    private static interface Task<T> {
        public void run(T var1) throws IOException;
    }
}

