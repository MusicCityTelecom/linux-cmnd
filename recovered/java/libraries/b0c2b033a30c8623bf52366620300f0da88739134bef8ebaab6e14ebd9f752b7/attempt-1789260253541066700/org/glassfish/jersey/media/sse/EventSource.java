/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ClientExecutor;
import org.glassfish.jersey.internal.guava.ThreadFactoryBuilder;
import org.glassfish.jersey.internal.util.ExtendedLogger;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.media.sse.internal.EventProcessor;

public class EventSource
implements EventListener {
    public static final long RECONNECT_DEFAULT = 500L;
    private static final Level CONNECTION_ERROR_LEVEL = Level.FINE;
    private static final ExtendedLogger LOGGER = new ExtendedLogger(Logger.getLogger(EventSource.class.getName()), Level.FINEST);
    private final WebTarget target;
    private final long reconnectDelay;
    private final boolean disableKeepAlive;
    private final CloseableClientExecutor executor;
    private final AtomicReference<EventProcessor.State> state = new AtomicReference<EventProcessor.State>(EventProcessor.State.READY);
    private final List<EventListener> unboundListeners = new CopyOnWriteArrayList<EventListener>();
    private final ConcurrentMap<String, List<EventListener>> boundListeners = new ConcurrentHashMap<String, List<EventListener>>();
    private final EventProcessor.ShutdownHandler shutdownHandler = this::shutdown;

    public static Builder target(WebTarget endpoint) {
        return new Builder(endpoint);
    }

    public EventSource(WebTarget endpoint) {
        this(endpoint, true);
    }

    public EventSource(WebTarget endpoint, boolean open) {
        this(endpoint, null, 500L, true, open);
    }

    private EventSource(WebTarget target, String name, long reconnectDelay, boolean disableKeepAlive, boolean open) {
        if (target == null) {
            throw new NullPointerException("Web target is 'null'.");
        }
        this.target = SseFeature.register(target);
        this.reconnectDelay = reconnectDelay;
        this.disableKeepAlive = disableKeepAlive;
        String esName = name == null ? EventSource.createDefaultName(target) : name;
        this.executor = new CloseableClientExecutor(Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat(esName + "-%d").setDaemon(true).build()));
        if (open) {
            this.open();
        }
    }

    private static String createDefaultName(WebTarget target) {
        return String.format("jersey-sse-event-source-[%s]", target.getUri().toASCIIString().replace("%", "%%"));
    }

    public void open() {
        if (!this.state.compareAndSet(EventProcessor.State.READY, EventProcessor.State.OPEN)) {
            switch (this.state.get()) {
                case OPEN: {
                    throw new IllegalStateException(LocalizationMessages.EVENT_SOURCE_ALREADY_CONNECTED());
                }
                case CLOSED: {
                    throw new IllegalStateException(LocalizationMessages.EVENT_SOURCE_ALREADY_CLOSED());
                }
            }
        }
        EventProcessor.Builder builder = EventProcessor.builder(this.target, this.state, this.executor, this, this.shutdownHandler).boundListeners(this.boundListeners).unboundListeners(this.unboundListeners).reconnectDelay(this.reconnectDelay, TimeUnit.MILLISECONDS);
        if (this.disableKeepAlive) {
            builder.disableKeepAlive();
        }
        EventProcessor processor = builder.build();
        this.executor.submit(processor);
        processor.awaitFirstContact();
    }

    public boolean isOpen() {
        return this.state.get() == EventProcessor.State.OPEN;
    }

    public void register(EventListener listener) {
        this.register(listener, null, new String[0]);
    }

    public void register(EventListener listener, String eventName, String ... eventNames) {
        if (eventName == null) {
            this.unboundListeners.add(listener);
        } else {
            this.addBoundListener(eventName, listener);
            if (eventNames != null) {
                for (String name : eventNames) {
                    this.addBoundListener(name, listener);
                }
            }
        }
    }

    private void addBoundListener(String name, EventListener listener) {
        List listeners = this.boundListeners.putIfAbsent(name, new CopyOnWriteArrayList<EventListener>(Collections.singleton(listener)));
        if (listeners != null) {
            listeners.add(listener);
        }
    }

    @Override
    public void onEvent(InboundEvent inboundEvent) {
    }

    public void close() {
        this.close(5L, TimeUnit.SECONDS);
    }

    public boolean close(long timeout, TimeUnit unit) {
        this.shutdown();
        try {
            if (!this.executor.awaitTermination(timeout, unit)) {
                LOGGER.log(CONNECTION_ERROR_LEVEL, LocalizationMessages.EVENT_SOURCE_SHUTDOWN_TIMEOUT(this.target.getUri().toString()));
                return false;
            }
        }
        catch (InterruptedException e) {
            LOGGER.log(CONNECTION_ERROR_LEVEL, LocalizationMessages.EVENT_SOURCE_SHUTDOWN_INTERRUPTED(this.target.getUri().toString()));
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }

    private void shutdown() {
        if (this.state.getAndSet(EventProcessor.State.CLOSED) != EventProcessor.State.CLOSED) {
            LOGGER.debugLog("Shutting down event processing.");
            this.executor.close();
        }
    }

    public static class Builder {
        private final WebTarget endpoint;
        private long reconnect = 500L;
        private String name = null;
        private boolean disableKeepAlive = true;

        private Builder(WebTarget endpoint) {
            this.endpoint = endpoint;
        }

        public Builder named(String name) {
            this.name = name;
            return this;
        }

        public Builder usePersistentConnections() {
            this.disableKeepAlive = false;
            return this;
        }

        public Builder reconnectingEvery(long delay, TimeUnit unit) {
            this.reconnect = unit.toMillis(delay);
            return this;
        }

        public EventSource build() {
            return new EventSource(this.endpoint, this.name, this.reconnect, this.disableKeepAlive, false);
        }

        public EventSource open() {
            EventSource source = new EventSource(this.endpoint, this.name, this.reconnect, this.disableKeepAlive, false);
            source.open();
            return source;
        }
    }

    private static class CloseableClientExecutor
    implements ClientExecutor,
    Closeable {
        private final ScheduledExecutorService scheduledExecutorService;

        public CloseableClientExecutor(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return this.scheduledExecutorService.submit(task);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return this.scheduledExecutorService.submit(task);
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return this.scheduledExecutorService.submit(task, result);
        }

        @Override
        public <T> ScheduledFuture<T> schedule(Callable<T> callable, long delay, TimeUnit unit) {
            return this.scheduledExecutorService.schedule(callable, delay, unit);
        }

        @Override
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return this.scheduledExecutorService.schedule(command, delay, unit);
        }

        @Override
        public void close() {
            this.scheduledExecutorService.shutdownNow();
        }

        boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return this.scheduledExecutorService.awaitTermination(timeout, unit);
        }
    }
}

