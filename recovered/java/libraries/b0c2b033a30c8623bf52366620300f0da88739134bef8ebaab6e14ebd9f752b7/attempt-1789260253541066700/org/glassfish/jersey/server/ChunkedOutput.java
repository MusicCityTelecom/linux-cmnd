/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Provider;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.process.internal.RequestContext;
import org.glassfish.jersey.process.internal.RequestScope;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.process.MappableException;

public class ChunkedOutput<T>
extends GenericType<T>
implements Closeable {
    private static final byte[] ZERO_LENGTH_DELIMITER = new byte[0];
    private final BlockingDeque<T> queue = new LinkedBlockingDeque<T>();
    private final byte[] chunkDelimiter;
    private final AtomicBoolean resumed = new AtomicBoolean(false);
    private boolean flushing = false;
    private volatile boolean closed = false;
    private volatile AsyncContext asyncContext;
    private volatile RequestScope requestScope;
    private volatile RequestContext requestScopeContext;
    private volatile ContainerRequest requestContext;
    private volatile ContainerResponse responseContext;
    private volatile ConnectionCallback connectionCallback;

    protected ChunkedOutput() {
        this.chunkDelimiter = ZERO_LENGTH_DELIMITER;
    }

    public ChunkedOutput(Type chunkType) {
        super(chunkType);
        this.chunkDelimiter = ZERO_LENGTH_DELIMITER;
    }

    protected ChunkedOutput(byte[] chunkDelimiter) {
        if (chunkDelimiter.length > 0) {
            this.chunkDelimiter = new byte[chunkDelimiter.length];
            System.arraycopy(chunkDelimiter, 0, this.chunkDelimiter, 0, chunkDelimiter.length);
        } else {
            this.chunkDelimiter = ZERO_LENGTH_DELIMITER;
        }
    }

    protected ChunkedOutput(byte[] chunkDelimiter, Provider<AsyncContext> asyncContextProvider) {
        if (chunkDelimiter.length > 0) {
            this.chunkDelimiter = new byte[chunkDelimiter.length];
            System.arraycopy(chunkDelimiter, 0, this.chunkDelimiter, 0, chunkDelimiter.length);
        } else {
            this.chunkDelimiter = ZERO_LENGTH_DELIMITER;
        }
        this.asyncContext = asyncContextProvider == null ? null : asyncContextProvider.get();
    }

    public ChunkedOutput(Type chunkType, byte[] chunkDelimiter) {
        super(chunkType);
        if (chunkDelimiter.length > 0) {
            this.chunkDelimiter = new byte[chunkDelimiter.length];
            System.arraycopy(chunkDelimiter, 0, this.chunkDelimiter, 0, chunkDelimiter.length);
        } else {
            this.chunkDelimiter = ZERO_LENGTH_DELIMITER;
        }
    }

    protected ChunkedOutput(String chunkDelimiter) {
        this.chunkDelimiter = chunkDelimiter.isEmpty() ? ZERO_LENGTH_DELIMITER : chunkDelimiter.getBytes();
    }

    public ChunkedOutput(Type chunkType, String chunkDelimiter) {
        super(chunkType);
        this.chunkDelimiter = chunkDelimiter.isEmpty() ? ZERO_LENGTH_DELIMITER : chunkDelimiter.getBytes();
    }

    public void write(T chunk) throws IOException {
        if (this.closed) {
            throw new IOException(LocalizationMessages.CHUNKED_OUTPUT_CLOSED());
        }
        if (chunk != null) {
            this.queue.add(chunk);
        }
        this.flushQueue();
    }

    /*
     * Loose catch block
     */
    protected void flushQueue() throws IOException {
        block19: {
            if (this.resumed.compareAndSet(false, true) && this.asyncContext != null) {
                this.asyncContext.resume(this);
            }
            if (this.requestScopeContext == null || this.requestContext == null || this.responseContext == null) {
                return;
            }
            Exception ex = null;
            this.requestScope.runInScope(this.requestScopeContext, new Callable<Void>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public Void call() throws IOException {
                    Object t;
                    boolean shouldClose;
                    ChunkedOutput chunkedOutput = ChunkedOutput.this;
                    synchronized (chunkedOutput) {
                        if (ChunkedOutput.this.flushing) {
                            return null;
                        }
                        shouldClose = ChunkedOutput.this.closed;
                        t = ChunkedOutput.this.queue.poll();
                        if (t != null || shouldClose) {
                            ChunkedOutput.this.flushing = true;
                        }
                    }
                    while (t != null) {
                        try {
                            OutputStream origStream = ChunkedOutput.this.responseContext.getEntityStream();
                            OutputStream writtenStream = ChunkedOutput.this.requestContext.getWorkers().writeTo(t, t.getClass(), ChunkedOutput.this.getType(), ChunkedOutput.this.responseContext.getEntityAnnotations(), ChunkedOutput.this.responseContext.getMediaType(), ChunkedOutput.this.responseContext.getHeaders(), ChunkedOutput.this.requestContext.getPropertiesDelegate(), origStream, Collections.emptyList());
                            if (ChunkedOutput.this.chunkDelimiter != ZERO_LENGTH_DELIMITER) {
                                writtenStream.write(ChunkedOutput.this.chunkDelimiter);
                            }
                            writtenStream.flush();
                            if (origStream != writtenStream) {
                                ChunkedOutput.this.responseContext.setEntityStream(writtenStream);
                            }
                        }
                        catch (IOException ioe) {
                            ChunkedOutput.this.connectionCallback.onDisconnect(ChunkedOutput.this.asyncContext);
                            throw ioe;
                        }
                        catch (MappableException mpe) {
                            if (mpe.getCause() instanceof IOException) {
                                ChunkedOutput.this.connectionCallback.onDisconnect(ChunkedOutput.this.asyncContext);
                            }
                            throw mpe;
                        }
                        t = ChunkedOutput.this.queue.poll();
                        if (t != null) continue;
                        chunkedOutput = ChunkedOutput.this;
                        synchronized (chunkedOutput) {
                            shouldClose = ChunkedOutput.this.closed;
                            t = ChunkedOutput.this.queue.poll();
                            if (t == null) {
                                ChunkedOutput.this.responseContext.commitStream();
                                ChunkedOutput.this.flushing = shouldClose;
                                break;
                            }
                        }
                    }
                    return null;
                }
            });
            if (!this.closed) break block19;
            try {
                this.responseContext.close();
            }
            catch (Exception e) {
                ex = ex == null ? e : ex;
            }
            this.requestScopeContext.release();
            if (ex instanceof IOException) {
                throw (IOException)ex;
            }
            if (ex instanceof RuntimeException) {
                throw (RuntimeException)ex;
            }
            break block19;
            catch (Exception e) {
                try {
                    this.closed = true;
                    ex = e;
                    if (!this.closed) break block19;
                }
                catch (Throwable throwable) {
                    if (this.closed) {
                        try {
                            this.responseContext.close();
                        }
                        catch (Exception e2) {
                            ex = ex == null ? e2 : ex;
                        }
                        this.requestScopeContext.release();
                        if (ex instanceof IOException) {
                            throw (IOException)ex;
                        }
                        if (ex instanceof RuntimeException) {
                            throw (RuntimeException)ex;
                        }
                    }
                    throw throwable;
                }
                try {
                    this.responseContext.close();
                }
                catch (Exception e3) {
                    ex = ex == null ? e3 : ex;
                }
                this.requestScopeContext.release();
                if (ex instanceof IOException) {
                    throw (IOException)ex;
                }
                if (ex instanceof RuntimeException) {
                    throw (RuntimeException)ex;
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        this.flushQueue();
    }

    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.queue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ChunkedOutput<" + this.getType() + ">";
    }

    void setContext(RequestScope requestScope, RequestContext requestScopeContext, ContainerRequest requestContext, ContainerResponse responseContext, ConnectionCallback connectionCallbackRunner) throws IOException {
        this.requestScope = requestScope;
        this.requestScopeContext = requestScopeContext;
        this.requestContext = requestContext;
        this.responseContext = responseContext;
        this.connectionCallback = connectionCallbackRunner;
        this.flushQueue();
    }
}

