/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.collection.NonBlockingInputStream;

public final class ByteBufferInputStream
extends NonBlockingInputStream {
    private static final ByteBuffer EOF = ByteBuffer.wrap(new byte[0]);
    private boolean eof = false;
    private ByteBuffer current = null;
    private final BlockingQueue<ByteBuffer> buffers;
    private final AtomicReference<Object> queueStatus = new AtomicReference<Object>(null);
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public ByteBufferInputStream() {
        this.buffers = new LinkedTransferQueue<ByteBuffer>();
    }

    private boolean fetchChunk(boolean block) throws InterruptedException {
        if (this.eof) {
            return false;
        }
        do {
            if (this.closed.get()) {
                this.current = EOF;
                break;
            }
            ByteBuffer byteBuffer = this.current = block ? this.buffers.take() : (ByteBuffer)this.buffers.poll();
        } while (this.current != null && this.current != EOF && !this.current.hasRemaining());
        this.eof = this.current == EOF;
        return !this.eof;
    }

    private void checkNotClosed() throws IOException {
        if (this.closed.get()) {
            throw new IOException(LocalizationMessages.INPUT_STREAM_CLOSED());
        }
    }

    private void checkThrowable() throws IOException {
        Object o = this.queueStatus.get();
        if (o != null && o != EOF && this.queueStatus.compareAndSet(o, EOF)) {
            try {
                throw new IOException((Throwable)o);
            }
            catch (Throwable throwable) {
                this.close();
                throw throwable;
            }
        }
    }

    @Override
    public int available() throws IOException {
        ByteBuffer buffer;
        if (this.eof || this.closed.get()) {
            this.checkThrowable();
            return 0;
        }
        int available = 0;
        if (this.current != null && this.current.hasRemaining()) {
            available = this.current.remaining();
        }
        Iterator iterator = this.buffers.iterator();
        while (iterator.hasNext() && (buffer = (ByteBuffer)iterator.next()) != EOF) {
            available += buffer.remaining();
        }
        this.checkThrowable();
        return this.closed.get() ? 0 : available;
    }

    @Override
    public int read() throws IOException {
        return this.tryRead(true);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.tryRead(b, off, len, true);
    }

    @Override
    public int tryRead() throws IOException {
        return this.tryRead(false);
    }

    @Override
    public int tryRead(byte[] b) throws IOException {
        return this.tryRead(b, 0, b.length);
    }

    @Override
    public int tryRead(byte[] b, int off, int len) throws IOException {
        return this.tryRead(b, off, len, false);
    }

    @Override
    public void close() throws IOException {
        if (this.closed.compareAndSet(false, true)) {
            this.closeQueue();
            this.buffers.clear();
        }
        this.checkThrowable();
    }

    public boolean put(ByteBuffer src) throws InterruptedException {
        if (this.queueStatus.get() == null) {
            this.buffers.put(src);
            return true;
        }
        return false;
    }

    public void closeQueue() {
        if (this.queueStatus.compareAndSet(null, EOF)) {
            try {
                this.buffers.put(EOF);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void closeQueue(Throwable throwable) {
        if (this.queueStatus.compareAndSet(null, throwable)) {
            try {
                this.buffers.put(EOF);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private int tryRead(byte[] b, int off, int len, boolean block) throws IOException {
        this.checkThrowable();
        this.checkNotClosed();
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return 0;
        }
        if (this.eof) {
            return -1;
        }
        int i = 0;
        while (i < len) {
            if (this.current != null && this.current.hasRemaining()) {
                int available = this.current.remaining();
                if (available < len - i) {
                    this.current.get(b, off + i, available);
                    i += available;
                    continue;
                }
                this.current.get(b, off + i, len - i);
                return len;
            }
            try {
                if (this.fetchChunk(block) && this.current != null) continue;
                break;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (!block) continue;
                throw new IOException(e);
            }
        }
        return i == 0 && this.eof ? -1 : i;
    }

    private int tryRead(boolean block) throws IOException {
        block6: {
            this.checkThrowable();
            this.checkNotClosed();
            if (this.eof) {
                return -1;
            }
            if (this.current != null && this.current.hasRemaining()) {
                return this.current.get() & 0xFF;
            }
            try {
                if (this.fetchChunk(block) && this.current != null) {
                    return this.current.get() & 0xFF;
                }
                if (block) {
                    return -1;
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (!block) break block6;
                throw new IOException(e);
            }
        }
        return this.eof ? -1 : Integer.MIN_VALUE;
    }
}

