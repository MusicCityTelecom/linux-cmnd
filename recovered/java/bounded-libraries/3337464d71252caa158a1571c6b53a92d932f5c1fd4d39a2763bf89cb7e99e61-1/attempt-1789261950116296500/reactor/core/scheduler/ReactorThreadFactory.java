/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import reactor.core.scheduler.NonBlocking;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

class ReactorThreadFactory
implements ThreadFactory,
Supplier<String>,
Thread.UncaughtExceptionHandler {
    private final String name;
    private final AtomicLong counterReference;
    private final boolean daemon;
    private final boolean rejectBlocking;
    @Nullable
    private final BiConsumer<Thread, Throwable> uncaughtExceptionHandler;

    ReactorThreadFactory(String name, AtomicLong counterReference, boolean daemon, boolean rejectBlocking, @Nullable BiConsumer<Thread, Throwable> uncaughtExceptionHandler) {
        this.name = name;
        this.counterReference = counterReference;
        this.daemon = daemon;
        this.rejectBlocking = rejectBlocking;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public final Thread newThread(@NonNull Runnable runnable) {
        Thread t;
        String newThreadName = this.name + "-" + this.counterReference.incrementAndGet();
        Thread thread = t = this.rejectBlocking ? new NonBlockingThread(runnable, newThreadName) : new Thread(runnable, newThreadName);
        if (this.daemon) {
            t.setDaemon(true);
        }
        if (this.uncaughtExceptionHandler != null) {
            t.setUncaughtExceptionHandler(this);
        }
        return t;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (this.uncaughtExceptionHandler == null) {
            return;
        }
        this.uncaughtExceptionHandler.accept(t, e);
    }

    @Override
    public final String get() {
        return this.name;
    }

    static final class NonBlockingThread
    extends Thread
    implements NonBlocking {
        public NonBlockingThread(Runnable target, String name) {
            super(target, name);
        }
    }
}

