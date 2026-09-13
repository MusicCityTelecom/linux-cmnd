/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.ResolvableType
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.AlternativeJdkIdGenerator
 *  org.springframework.util.Assert
 *  org.springframework.util.IdGenerator
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.ListenableFutureCallback
 *  org.springframework.util.concurrent.SettableListenableFuture
 */
package org.springframework.messaging.simp.stomp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.stomp.ConnectionHandlingStompSession;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompDecoder;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;

public class DefaultStompSession
implements ConnectionHandlingStompSession {
    private static final Log logger = SimpLogging.forLogName(DefaultStompSession.class);
    private static final IdGenerator idGenerator = new AlternativeJdkIdGenerator();
    public static final byte[] EMPTY_PAYLOAD = new byte[0];
    private static final long HEARTBEAT_MULTIPLIER = 3L;
    private static final Message<byte[]> HEARTBEAT;
    private final String sessionId;
    private final StompSessionHandler sessionHandler;
    private final StompHeaders connectHeaders;
    private final SettableListenableFuture<StompSession> sessionFuture = new SettableListenableFuture();
    private MessageConverter converter = new SimpleMessageConverter();
    @Nullable
    private TaskScheduler taskScheduler;
    private long receiptTimeLimit = TimeUnit.SECONDS.toMillis(15L);
    private volatile boolean autoReceiptEnabled;
    @Nullable
    private volatile TcpConnection<byte[]> connection;
    @Nullable
    private volatile String version;
    private final AtomicInteger subscriptionIndex = new AtomicInteger();
    private final Map<String, DefaultSubscription> subscriptions = new ConcurrentHashMap<String, DefaultSubscription>(4);
    private final AtomicInteger receiptIndex = new AtomicInteger();
    private final Map<String, ReceiptHandler> receiptHandlers = new ConcurrentHashMap<String, ReceiptHandler>(4);
    private volatile boolean closing;

    public DefaultStompSession(StompSessionHandler sessionHandler, StompHeaders connectHeaders) {
        Assert.notNull((Object)sessionHandler, (String)"StompSessionHandler must not be null");
        Assert.notNull((Object)connectHeaders, (String)"StompHeaders must not be null");
        this.sessionId = idGenerator.generateId().toString();
        this.sessionHandler = sessionHandler;
        this.connectHeaders = connectHeaders;
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public StompHeaderAccessor getConnectHeaders() {
        StompHeaderAccessor accessor = this.createHeaderAccessor(StompCommand.CONNECT);
        accessor.addNativeHeaders(this.connectHeaders);
        return accessor;
    }

    public StompSessionHandler getSessionHandler() {
        return this.sessionHandler;
    }

    @Override
    public ListenableFuture<StompSession> getSessionFuture() {
        return this.sessionFuture;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        Assert.notNull((Object)messageConverter, (String)"MessageConverter must not be null");
        this.converter = messageConverter;
    }

    public MessageConverter getMessageConverter() {
        return this.converter;
    }

    public void setTaskScheduler(@Nullable TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Nullable
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    public void setReceiptTimeLimit(long receiptTimeLimit) {
        Assert.isTrue((receiptTimeLimit > 0L ? 1 : 0) != 0, (String)"Receipt time limit must be larger than zero");
        this.receiptTimeLimit = receiptTimeLimit;
    }

    public long getReceiptTimeLimit() {
        return this.receiptTimeLimit;
    }

    @Override
    public void setAutoReceipt(boolean autoReceiptEnabled) {
        this.autoReceiptEnabled = autoReceiptEnabled;
    }

    public boolean isAutoReceiptEnabled() {
        return this.autoReceiptEnabled;
    }

    @Override
    public boolean isConnected() {
        return this.connection != null;
    }

    @Override
    public StompSession.Receiptable send(String destination, Object payload) {
        StompHeaders headers = new StompHeaders();
        headers.setDestination(destination);
        return this.send(headers, payload);
    }

    @Override
    public StompSession.Receiptable send(StompHeaders headers, Object payload) {
        Assert.hasText((String)headers.getDestination(), (String)"Destination header is required");
        String receiptId = this.checkOrAddReceipt(headers);
        ReceiptHandler receiptable = new ReceiptHandler(receiptId);
        StompHeaderAccessor accessor = this.createHeaderAccessor(StompCommand.SEND);
        accessor.addNativeHeaders(headers);
        Message<byte[]> message = this.createMessage(accessor, payload);
        this.execute(message);
        return receiptable;
    }

    @Nullable
    private String checkOrAddReceipt(StompHeaders headers) {
        String receiptId = headers.getReceipt();
        if (this.isAutoReceiptEnabled() && receiptId == null) {
            receiptId = String.valueOf(this.receiptIndex.getAndIncrement());
            headers.setReceipt(receiptId);
        }
        return receiptId;
    }

    private StompHeaderAccessor createHeaderAccessor(StompCommand command) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(command);
        accessor.setSessionId(this.sessionId);
        accessor.setLeaveMutable(true);
        return accessor;
    }

    private Message<byte[]> createMessage(StompHeaderAccessor accessor, @Nullable Object payload) {
        Message<Object> message;
        accessor.updateSimpMessageHeadersFromStompHeaders();
        if (ObjectUtils.isEmpty((Object)payload)) {
            message = MessageBuilder.createMessage(EMPTY_PAYLOAD, accessor.getMessageHeaders());
        } else {
            message = this.getMessageConverter().toMessage(payload, accessor.getMessageHeaders());
            accessor.updateStompHeadersFromSimpMessageHeaders();
            if (message == null) {
                throw new MessageConversionException("Unable to convert payload with type='" + payload.getClass().getName() + "', contentType='" + accessor.getContentType() + "', converter=[" + this.getMessageConverter() + "]");
            }
        }
        return message;
    }

    private void execute(Message<byte[]> message) {
        TcpConnection<byte[]> conn;
        StompHeaderAccessor accessor;
        if (logger.isTraceEnabled() && (accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class)) != null) {
            logger.trace((Object)("Sending " + accessor.getDetailedLogMessage(message.getPayload())));
        }
        Assert.state(((conn = this.connection) != null ? 1 : 0) != 0, (String)"Connection closed");
        try {
            conn.send(message).get();
        }
        catch (ExecutionException ex) {
            throw new MessageDeliveryException(message, ex.getCause());
        }
        catch (Throwable ex) {
            throw new MessageDeliveryException(message, ex);
        }
    }

    @Override
    public StompSession.Subscription subscribe(String destination, StompFrameHandler handler) {
        StompHeaders headers = new StompHeaders();
        headers.setDestination(destination);
        return this.subscribe(headers, handler);
    }

    @Override
    public StompSession.Subscription subscribe(StompHeaders headers, StompFrameHandler handler) {
        Assert.hasText((String)headers.getDestination(), (String)"Destination header is required");
        Assert.notNull((Object)handler, (String)"StompFrameHandler must not be null");
        String subscriptionId = headers.getId();
        if (!StringUtils.hasText((String)subscriptionId)) {
            subscriptionId = String.valueOf(this.subscriptionIndex.getAndIncrement());
            headers.setId(subscriptionId);
        }
        this.checkOrAddReceipt(headers);
        DefaultSubscription subscription = new DefaultSubscription(headers, handler);
        StompHeaderAccessor accessor = this.createHeaderAccessor(StompCommand.SUBSCRIBE);
        accessor.addNativeHeaders(headers);
        Message<byte[]> message = this.createMessage(accessor, EMPTY_PAYLOAD);
        this.execute(message);
        return subscription;
    }

    @Override
    public StompSession.Receiptable acknowledge(String messageId, boolean consumed) {
        StompHeaders headers = new StompHeaders();
        if ("1.1".equals(this.version)) {
            headers.setMessageId(messageId);
        } else {
            headers.setId(messageId);
        }
        return this.acknowledge(headers, consumed);
    }

    @Override
    public StompSession.Receiptable acknowledge(StompHeaders headers, boolean consumed) {
        String receiptId = this.checkOrAddReceipt(headers);
        ReceiptHandler receiptable = new ReceiptHandler(receiptId);
        StompCommand command = consumed ? StompCommand.ACK : StompCommand.NACK;
        StompHeaderAccessor accessor = this.createHeaderAccessor(command);
        accessor.addNativeHeaders(headers);
        Message<byte[]> message = this.createMessage(accessor, null);
        this.execute(message);
        return receiptable;
    }

    private void unsubscribe(String id, @Nullable StompHeaders headers) {
        StompHeaderAccessor accessor = this.createHeaderAccessor(StompCommand.UNSUBSCRIBE);
        if (headers != null) {
            accessor.addNativeHeaders(headers);
        }
        accessor.setSubscriptionId(id);
        Message<byte[]> message = this.createMessage(accessor, EMPTY_PAYLOAD);
        this.execute(message);
    }

    @Override
    public void disconnect() {
        this.disconnect(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void disconnect(@Nullable StompHeaders headers) {
        this.closing = true;
        try {
            StompHeaderAccessor accessor = this.createHeaderAccessor(StompCommand.DISCONNECT);
            if (headers != null) {
                accessor.addNativeHeaders(headers);
            }
            Message<byte[]> message = this.createMessage(accessor, EMPTY_PAYLOAD);
            this.execute(message);
        }
        finally {
            this.resetConnection();
        }
    }

    @Override
    public void afterConnected(TcpConnection<byte[]> connection) {
        this.connection = connection;
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Connection established in session id=" + this.sessionId));
        }
        StompHeaderAccessor accessor = this.createHeaderAccessor(StompCommand.CONNECT);
        accessor.addNativeHeaders(this.connectHeaders);
        if (this.connectHeaders.getAcceptVersion() == null) {
            accessor.setAcceptVersion("1.1,1.2");
        }
        Message<byte[]> message = this.createMessage(accessor, EMPTY_PAYLOAD);
        this.execute(message);
    }

    @Override
    public void afterConnectFailure(Throwable ex) {
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Failed to connect session id=" + this.sessionId), ex);
        }
        this.sessionFuture.setException(ex);
        this.sessionHandler.handleTransportError(this, ex);
    }

    @Override
    public void handleMessage(Message<byte[]> message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Assert.state((accessor != null ? 1 : 0) != 0, (String)"No StompHeaderAccessor");
        accessor.setSessionId(this.sessionId);
        StompCommand command = accessor.getCommand();
        Map<String, List<String>> nativeHeaders = accessor.getNativeHeaders();
        StompHeaders headers = StompHeaders.readOnlyStompHeaders(nativeHeaders);
        boolean isHeartbeat = accessor.isHeartbeat();
        if (logger.isTraceEnabled()) {
            logger.trace((Object)("Received " + accessor.getDetailedLogMessage(message.getPayload())));
        }
        try {
            if (StompCommand.MESSAGE.equals((Object)command)) {
                DefaultSubscription subscription = this.subscriptions.get(headers.getSubscription());
                if (subscription != null) {
                    this.invokeHandler(subscription.getHandler(), message, headers);
                } else if (logger.isDebugEnabled()) {
                    logger.debug((Object)("No handler for: " + accessor.getDetailedLogMessage(message.getPayload()) + ". Perhaps just unsubscribed?"));
                }
            } else if (StompCommand.RECEIPT.equals((Object)command)) {
                String receiptId = headers.getReceiptId();
                ReceiptHandler handler = this.receiptHandlers.get(receiptId);
                if (handler != null) {
                    handler.handleReceiptReceived();
                } else if (logger.isDebugEnabled()) {
                    logger.debug((Object)("No matching receipt: " + accessor.getDetailedLogMessage(message.getPayload())));
                }
            } else if (StompCommand.CONNECTED.equals((Object)command)) {
                this.initHeartbeatTasks(headers);
                this.version = headers.getFirst("version");
                this.sessionFuture.set((Object)this);
                this.sessionHandler.afterConnected(this, headers);
            } else if (StompCommand.ERROR.equals((Object)command)) {
                this.invokeHandler(this.sessionHandler, message, headers);
            } else if (!isHeartbeat && logger.isTraceEnabled()) {
                logger.trace((Object)"Message not handled.");
            }
        }
        catch (Throwable ex) {
            this.sessionHandler.handleException(this, command, headers, message.getPayload(), ex);
        }
    }

    private void invokeHandler(StompFrameHandler handler, Message<byte[]> message, StompHeaders headers) {
        if (message.getPayload().length == 0) {
            handler.handleFrame(headers, null);
            return;
        }
        Type payloadType = handler.getPayloadType(headers);
        Class resolvedType = ResolvableType.forType((Type)payloadType).resolve();
        if (resolvedType == null) {
            throw new MessageConversionException("Unresolvable payload type [" + payloadType + "] from handler type [" + handler.getClass() + "]");
        }
        Object object = this.getMessageConverter().fromMessage(message, resolvedType);
        if (object == null) {
            throw new MessageConversionException("No suitable converter for payload type [" + payloadType + "] from handler type [" + handler.getClass() + "]");
        }
        handler.handleFrame(headers, object);
    }

    private void initHeartbeatTasks(StompHeaders connectedHeaders) {
        long interval;
        long[] connect = this.connectHeaders.getHeartbeat();
        long[] connected = connectedHeaders.getHeartbeat();
        if (connect == null || connected == null) {
            return;
        }
        TcpConnection<byte[]> con = this.connection;
        Assert.state((con != null ? 1 : 0) != 0, (String)"No TcpConnection available");
        if (connect[0] > 0L && connected[1] > 0L) {
            interval = Math.max(connect[0], connected[1]);
            con.onWriteInactivity(new WriteInactivityTask(), interval);
        }
        if (connect[1] > 0L && connected[0] > 0L) {
            interval = Math.max(connect[1], connected[0]) * 3L;
            con.onReadInactivity(new ReadInactivityTask(), interval);
        }
    }

    @Override
    public void handleFailure(Throwable ex) {
        block2: {
            try {
                this.sessionFuture.setException(ex);
                this.sessionHandler.handleTransportError(this, ex);
            }
            catch (Throwable ex2) {
                if (!logger.isDebugEnabled()) break block2;
                logger.debug((Object)"Uncaught failure while handling transport failure", ex2);
            }
        }
    }

    @Override
    public void afterConnectionClosed() {
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Connection closed in session id=" + this.sessionId));
        }
        if (!this.closing) {
            this.resetConnection();
            this.handleFailure(new ConnectionLostException("Connection closed"));
        }
    }

    private void resetConnection() {
        TcpConnection<byte[]> conn = this.connection;
        this.connection = null;
        if (conn != null) {
            try {
                conn.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    static {
        StompHeaderAccessor accessor = StompHeaderAccessor.createForHeartbeat();
        HEARTBEAT = MessageBuilder.createMessage(StompDecoder.HEARTBEAT_PAYLOAD, accessor.getMessageHeaders());
    }

    private class ReadInactivityTask
    implements Runnable {
        private ReadInactivityTask() {
        }

        @Override
        public void run() {
            DefaultStompSession.this.closing = true;
            String error = "Server has gone quiet. Closing connection in session id=" + DefaultStompSession.this.sessionId + ".";
            if (logger.isDebugEnabled()) {
                logger.debug((Object)error);
            }
            DefaultStompSession.this.resetConnection();
            DefaultStompSession.this.handleFailure(new IllegalStateException(error));
        }
    }

    private class WriteInactivityTask
    implements Runnable {
        private WriteInactivityTask() {
        }

        @Override
        public void run() {
            TcpConnection conn = DefaultStompSession.this.connection;
            if (conn != null) {
                conn.send(HEARTBEAT).addCallback((ListenableFutureCallback)new ListenableFutureCallback<Void>(){

                    public void onSuccess(@Nullable Void result) {
                    }

                    public void onFailure(Throwable ex) {
                        DefaultStompSession.this.handleFailure(ex);
                    }
                });
            }
        }
    }

    private class DefaultSubscription
    extends ReceiptHandler
    implements StompSession.Subscription {
        private final StompHeaders headers;
        private final StompFrameHandler handler;

        public DefaultSubscription(StompHeaders headers, StompFrameHandler handler) {
            super(headers.getReceipt());
            Assert.notNull((Object)headers.getDestination(), (String)"Destination must not be null");
            Assert.notNull((Object)handler, (String)"StompFrameHandler must not be null");
            this.headers = headers;
            this.handler = handler;
            DefaultStompSession.this.subscriptions.put(headers.getId(), this);
        }

        @Override
        @Nullable
        public String getSubscriptionId() {
            return this.headers.getId();
        }

        @Override
        public StompHeaders getSubscriptionHeaders() {
            return this.headers;
        }

        public StompFrameHandler getHandler() {
            return this.handler;
        }

        @Override
        public void unsubscribe() {
            this.unsubscribe(null);
        }

        @Override
        public void unsubscribe(@Nullable StompHeaders headers) {
            String id = this.headers.getId();
            if (id != null) {
                DefaultStompSession.this.subscriptions.remove(id);
                DefaultStompSession.this.unsubscribe(id, headers);
            }
        }

        public String toString() {
            return "Subscription [id=" + this.getSubscriptionId() + ", destination='" + this.headers.getDestination() + "', receiptId='" + this.getReceiptId() + "', handler=" + this.getHandler() + "]";
        }
    }

    private class ReceiptHandler
    implements StompSession.Receiptable {
        @Nullable
        private final String receiptId;
        private final List<Runnable> receiptCallbacks = new ArrayList<Runnable>(2);
        private final List<Runnable> receiptLostCallbacks = new ArrayList<Runnable>(2);
        @Nullable
        private ScheduledFuture<?> future;
        @Nullable
        private Boolean result;

        public ReceiptHandler(String receiptId) {
            this.receiptId = receiptId;
            if (receiptId != null) {
                this.initReceiptHandling();
            }
        }

        private void initReceiptHandling() {
            Assert.notNull((Object)DefaultStompSession.this.getTaskScheduler(), (String)"To track receipts, a TaskScheduler must be configured");
            DefaultStompSession.this.receiptHandlers.put(this.receiptId, this);
            Date startTime = new Date(System.currentTimeMillis() + DefaultStompSession.this.getReceiptTimeLimit());
            this.future = DefaultStompSession.this.getTaskScheduler().schedule(this::handleReceiptNotReceived, startTime);
        }

        @Override
        @Nullable
        public String getReceiptId() {
            return this.receiptId;
        }

        @Override
        public void addReceiptTask(Runnable task) {
            this.addTask(task, true);
        }

        @Override
        public void addReceiptLostTask(Runnable task) {
            this.addTask(task, false);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void addTask(Runnable task, boolean successTask) {
            Assert.notNull((Object)this.receiptId, (String)"To track receipts, set autoReceiptEnabled=true or add 'receiptId' header");
            ReceiptHandler receiptHandler = this;
            synchronized (receiptHandler) {
                if (this.result != null && this.result == successTask) {
                    this.invoke(Collections.singletonList(task));
                } else if (successTask) {
                    this.receiptCallbacks.add(task);
                } else {
                    this.receiptLostCallbacks.add(task);
                }
            }
        }

        private void invoke(List<Runnable> callbacks) {
            for (Runnable runnable : callbacks) {
                try {
                    runnable.run();
                }
                catch (Throwable throwable) {}
            }
        }

        public void handleReceiptReceived() {
            this.handleInternal(true);
        }

        public void handleReceiptNotReceived() {
            this.handleInternal(false);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void handleInternal(boolean result) {
            ReceiptHandler receiptHandler = this;
            synchronized (receiptHandler) {
                if (this.result != null) {
                    return;
                }
                this.result = result;
                this.invoke(result ? this.receiptCallbacks : this.receiptLostCallbacks);
                DefaultStompSession.this.receiptHandlers.remove(this.receiptId);
                if (this.future != null) {
                    this.future.cancel(true);
                }
            }
        }
    }
}

