/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.client.ClientExecutor;
import org.glassfish.jersey.internal.util.ExtendedLogger;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.media.sse.SseFeature;

public class EventProcessor
implements Runnable,
EventListener {
    private static final Level CONNECTION_ERROR_LEVEL = Level.FINE;
    private static final ExtendedLogger LOGGER = new ExtendedLogger(Logger.getLogger(EventProcessor.class.getName()), Level.FINEST);
    private final CountDownLatch firstContactSignal;
    private String lastEventId;
    private long reconnectDelay;
    private final WebTarget target;
    private final boolean disableKeepAlive;
    private final ClientExecutor executor;
    private final AtomicReference<State> state;
    private final List<EventListener> unboundListeners;
    private final Map<String, List<EventListener>> boundListeners;
    private final ShutdownHandler shutdownHandler;
    private final EventListener eventListener;

    private EventProcessor(EventProcessor that) {
        this.firstContactSignal = null;
        this.reconnectDelay = that.reconnectDelay;
        this.lastEventId = that.lastEventId;
        this.target = that.target;
        this.disableKeepAlive = that.disableKeepAlive;
        this.executor = that.executor;
        this.state = that.state;
        this.boundListeners = that.boundListeners;
        this.unboundListeners = that.unboundListeners;
        this.eventListener = that.eventListener;
        this.shutdownHandler = that.shutdownHandler;
    }

    private EventProcessor(Builder builder) {
        this.firstContactSignal = new CountDownLatch(1);
        this.reconnectDelay = builder.reconnectDelay;
        this.lastEventId = builder.lastEventId;
        this.target = builder.target;
        this.disableKeepAlive = builder.disableKeepAlive;
        this.executor = builder.clientExecutor;
        this.state = builder.state;
        this.boundListeners = builder.boundListeners == null ? Collections.EMPTY_MAP : builder.boundListeners;
        this.unboundListeners = builder.unboundListeners == null ? Collections.EMPTY_LIST : builder.unboundListeners;
        this.eventListener = builder.eventListener;
        this.shutdownHandler = builder.shutdownHandler;
    }

    public static Builder builder(WebTarget target, AtomicReference<State> state, ClientExecutor clientExecutor, EventListener eventListener, ShutdownHandler shutdownHandler) {
        return new Builder(target, state, clientExecutor, eventListener, shutdownHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        LOGGER.debugLog("Listener task started.");
        ChunkedInput eventInput = null;
        try {
            try {
                Invocation.Builder request = this.prepareHandshakeRequest();
                if (this.state.get() == State.OPEN) {
                    LOGGER.debugLog("Connecting...");
                    eventInput = request.get(EventInput.class);
                    LOGGER.debugLog("Connected!");
                }
            }
            finally {
                if (this.firstContactSignal != null) {
                    this.firstContactSignal.countDown();
                }
            }
            Thread execThread = Thread.currentThread();
            while (this.state.get() == State.OPEN && !execThread.isInterrupted()) {
                if (eventInput == null || eventInput.isClosed()) {
                    LOGGER.debugLog("Connection lost - scheduling reconnect in {0} ms", this.reconnectDelay);
                    this.scheduleReconnect(this.reconnectDelay);
                    break;
                }
                this.onEvent((InboundEvent)eventInput.read());
            }
        }
        catch (ServiceUnavailableException ex) {
            LOGGER.debugLog("Received HTTP 503");
            long delay = this.reconnectDelay;
            if (ex.hasRetryAfter()) {
                LOGGER.debugLog("Recovering from HTTP 503 using HTTP Retry-After header value as a reconnect delay");
                Date requestTime = new Date();
                delay = ex.getRetryTime(requestTime).getTime() - requestTime.getTime();
                delay = delay > 0L ? delay : 0L;
            }
            LOGGER.debugLog("Recovering from HTTP 503 - scheduling to reconnect in {0} ms", delay);
            this.scheduleReconnect(delay);
        }
        catch (Exception ex) {
            if (LOGGER.isLoggable(CONNECTION_ERROR_LEVEL)) {
                LOGGER.log(CONNECTION_ERROR_LEVEL, String.format("Unable to connect - closing the event source to %s.", this.target.getUri().toASCIIString()), ex);
            }
            this.shutdownHandler.shutdown();
        }
        finally {
            if (eventInput != null && !eventInput.isClosed()) {
                eventInput.close();
            }
            LOGGER.debugLog("Listener task finished.");
        }
    }

    @Override
    public void onEvent(InboundEvent event) {
        List<EventListener> eventListeners;
        if (event == null) {
            return;
        }
        LOGGER.debugLog("New event received.");
        if (event.getId() != null) {
            this.lastEventId = event.getId();
        }
        if (event.isReconnectDelaySet()) {
            this.reconnectDelay = event.getReconnectDelay();
        }
        this.notify(this.eventListener, event);
        this.notify(this.unboundListeners, event);
        String eventName = event.getName();
        if (eventName != null && (eventListeners = this.boundListeners.get(eventName)) != null) {
            this.notify(eventListeners, event);
        }
    }

    private void notify(Collection<EventListener> listeners, InboundEvent event) {
        for (EventListener listener : listeners) {
            this.notify(listener, event);
        }
    }

    private void notify(EventListener listener, InboundEvent event) {
        block2: {
            try {
                listener.onEvent(event);
            }
            catch (Exception ex) {
                if (!LOGGER.isLoggable(Level.FINE)) break block2;
                LOGGER.log(Level.FINE, String.format("Event notification in a listener of %s class failed.", listener.getClass().getName()), ex);
            }
        }
    }

    private void scheduleReconnect(long delay) {
        State s = this.state.get();
        if (s != State.OPEN) {
            LOGGER.debugLog("Aborting reconnect of event source in {0} state", this.state);
            return;
        }
        EventProcessor processor = new EventProcessor(this);
        if (delay > 0L) {
            this.executor.schedule(processor, delay, TimeUnit.MILLISECONDS);
        } else {
            this.executor.submit(processor);
        }
    }

    private Invocation.Builder prepareHandshakeRequest() {
        Invocation.Builder request = this.target.request(SseFeature.SERVER_SENT_EVENTS_TYPE);
        if (this.lastEventId != null && !this.lastEventId.isEmpty()) {
            request.header("Last-Event-ID", this.lastEventId);
        }
        if (this.disableKeepAlive) {
            request.header("Connection", "close");
        }
        return request;
    }

    public void awaitFirstContact() {
        LOGGER.debugLog("Awaiting first contact signal.");
        try {
            if (this.firstContactSignal == null) {
                return;
            }
            try {
                this.firstContactSignal.await();
            }
            catch (InterruptedException ex) {
                LOGGER.log(CONNECTION_ERROR_LEVEL, LocalizationMessages.EVENT_SOURCE_OPEN_CONNECTION_INTERRUPTED(), ex);
                Thread.currentThread().interrupt();
            }
        }
        finally {
            LOGGER.debugLog("First contact signal released.");
        }
    }

    public static interface ShutdownHandler {
        public void shutdown();
    }

    public static class Builder {
        private final WebTarget target;
        private final AtomicReference<State> state;
        private final ClientExecutor clientExecutor;
        private final EventListener eventListener;
        private final ShutdownHandler shutdownHandler;
        private long reconnectDelay;
        private TimeUnit reconnectUnit;
        private String lastEventId;
        private boolean disableKeepAlive;
        private List<EventListener> unboundListeners;
        private Map<String, List<EventListener>> boundListeners;

        private Builder(WebTarget target, AtomicReference<State> state, ClientExecutor clientExecutor, EventListener eventListener, ShutdownHandler shutdownHandler) {
            this.target = target;
            this.state = state;
            this.clientExecutor = clientExecutor;
            this.eventListener = eventListener;
            this.shutdownHandler = shutdownHandler;
        }

        public Builder reconnectDelay(long reconnectDelay, TimeUnit unit) {
            this.reconnectDelay = reconnectDelay;
            this.reconnectUnit = this.reconnectUnit;
            return this;
        }

        public Builder unboundListeners(List<EventListener> unboundListeners) {
            this.unboundListeners = unboundListeners;
            return this;
        }

        public Builder boundListeners(Map<String, List<EventListener>> boundListeners) {
            this.boundListeners = boundListeners;
            return this;
        }

        public Builder disableKeepAlive() {
            this.disableKeepAlive = true;
            return this;
        }

        public EventProcessor build() {
            return new EventProcessor(this);
        }
    }

    public static enum State {
        READY,
        OPEN,
        CLOSED;

    }
}

