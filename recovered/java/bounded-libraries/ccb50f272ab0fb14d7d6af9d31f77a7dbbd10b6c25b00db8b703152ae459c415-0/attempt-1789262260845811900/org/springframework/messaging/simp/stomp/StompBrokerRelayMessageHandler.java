/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.ListenableFutureCallback
 *  org.springframework.util.concurrent.ListenableFutureTask
 */
package org.springframework.messaging.simp.stomp;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompDecoder;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.simp.stomp.StompTcpConnectionHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.messaging.tcp.FixedIntervalReconnectStrategy;
import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.messaging.tcp.TcpOperations;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

public class StompBrokerRelayMessageHandler
extends AbstractBrokerMessageHandler {
    public static final String SYSTEM_SESSION_ID = "_system_";
    private static final long HEARTBEAT_MULTIPLIER = 3L;
    private static final int MAX_TIME_TO_CONNECTED_FRAME = 60000;
    private static final byte[] EMPTY_PAYLOAD = new byte[0];
    private static final ListenableFutureTask<Void> EMPTY_TASK = new ListenableFutureTask((Callable)new VoidCallable());
    private static final StompHeaderAccessor HEART_BEAT_ACCESSOR;
    private static final Message<byte[]> HEARTBEAT_MESSAGE;
    private String relayHost = "127.0.0.1";
    private int relayPort = 61613;
    private String clientLogin = "guest";
    private String clientPasscode = "guest";
    private String systemLogin = "guest";
    private String systemPasscode = "guest";
    private long systemHeartbeatSendInterval = 10000L;
    private long systemHeartbeatReceiveInterval = 10000L;
    private final Map<String, MessageHandler> systemSubscriptions = new HashMap<String, MessageHandler>(4);
    @Nullable
    private String virtualHost;
    @Nullable
    private TcpOperations<byte[]> tcpClient;
    @Nullable
    private MessageHeaderInitializer headerInitializer;
    private final DefaultStats stats = new DefaultStats();
    private final Map<String, RelayConnectionHandler> connectionHandlers = new ConcurrentHashMap<String, RelayConnectionHandler>();
    @Nullable
    private TaskScheduler taskScheduler;

    public StompBrokerRelayMessageHandler(SubscribableChannel inboundChannel, MessageChannel outboundChannel, SubscribableChannel brokerChannel, Collection<String> destinationPrefixes) {
        super(inboundChannel, outboundChannel, brokerChannel, destinationPrefixes);
    }

    public void setRelayHost(String relayHost) {
        Assert.hasText((String)relayHost, (String)"relayHost must not be empty");
        this.relayHost = relayHost;
    }

    public String getRelayHost() {
        return this.relayHost;
    }

    public void setRelayPort(int relayPort) {
        this.relayPort = relayPort;
    }

    public int getRelayPort() {
        return this.relayPort;
    }

    public void setClientLogin(String clientLogin) {
        Assert.hasText((String)clientLogin, (String)"clientLogin must not be empty");
        this.clientLogin = clientLogin;
    }

    public String getClientLogin() {
        return this.clientLogin;
    }

    public void setClientPasscode(String clientPasscode) {
        Assert.hasText((String)clientPasscode, (String)"clientPasscode must not be empty");
        this.clientPasscode = clientPasscode;
    }

    public String getClientPasscode() {
        return this.clientPasscode;
    }

    public void setSystemLogin(String systemLogin) {
        Assert.hasText((String)systemLogin, (String)"systemLogin must not be empty");
        this.systemLogin = systemLogin;
    }

    public String getSystemLogin() {
        return this.systemLogin;
    }

    public void setSystemPasscode(String systemPasscode) {
        this.systemPasscode = systemPasscode;
    }

    public String getSystemPasscode() {
        return this.systemPasscode;
    }

    public void setSystemHeartbeatSendInterval(long systemHeartbeatSendInterval) {
        this.systemHeartbeatSendInterval = systemHeartbeatSendInterval;
    }

    public long getSystemHeartbeatSendInterval() {
        return this.systemHeartbeatSendInterval;
    }

    public void setSystemHeartbeatReceiveInterval(long heartbeatReceiveInterval) {
        this.systemHeartbeatReceiveInterval = heartbeatReceiveInterval;
    }

    public long getSystemHeartbeatReceiveInterval() {
        return this.systemHeartbeatReceiveInterval;
    }

    public void setSystemSubscriptions(@Nullable Map<String, MessageHandler> subscriptions) {
        this.systemSubscriptions.clear();
        if (subscriptions != null) {
            this.systemSubscriptions.putAll(subscriptions);
        }
    }

    public Map<String, MessageHandler> getSystemSubscriptions() {
        return this.systemSubscriptions;
    }

    public void setVirtualHost(@Nullable String virtualHost) {
        this.virtualHost = virtualHost;
    }

    @Nullable
    public String getVirtualHost() {
        return this.virtualHost;
    }

    public void setTcpClient(@Nullable TcpOperations<byte[]> tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Nullable
    public TcpOperations<byte[]> getTcpClient() {
        return this.tcpClient;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    public String getStatsInfo() {
        return this.stats.toString();
    }

    public Stats getStats() {
        return this.stats;
    }

    public int getConnectionCount() {
        return this.connectionHandlers.size();
    }

    public void setTaskScheduler(@Nullable TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Nullable
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    @Override
    protected void startInternal() {
        if (this.tcpClient == null) {
            this.tcpClient = this.initTcpClient();
        }
        if (this.logger.isInfoEnabled()) {
            this.logger.info((Object)("Starting \"system\" session, " + this.toString()));
        }
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECT);
        accessor.setAcceptVersion("1.1,1.2");
        accessor.setLogin(this.systemLogin);
        accessor.setPasscode(this.systemPasscode);
        accessor.setHeartbeat(this.systemHeartbeatSendInterval, this.systemHeartbeatReceiveInterval);
        String virtualHost = this.getVirtualHost();
        if (virtualHost != null) {
            accessor.setHost(virtualHost);
        }
        accessor.setSessionId(SYSTEM_SESSION_ID);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Forwarding " + accessor.getShortLogMessage(EMPTY_PAYLOAD)));
        }
        SystemSessionConnectionHandler handler = new SystemSessionConnectionHandler(accessor);
        this.connectionHandlers.put(handler.getSessionId(), handler);
        this.stats.incrementConnectCount();
        this.tcpClient.connect(handler, new FixedIntervalReconnectStrategy(5000L));
        if (this.taskScheduler != null) {
            this.taskScheduler.scheduleWithFixedDelay((Runnable)new ClientSendMessageCountTask(), 5000L);
        }
    }

    private ReactorNettyTcpClient<byte[]> initTcpClient() {
        StompDecoder decoder = new StompDecoder();
        if (this.headerInitializer != null) {
            decoder.setHeaderInitializer(this.headerInitializer);
        }
        StompReactorNettyCodec codec = new StompReactorNettyCodec(decoder);
        ReactorNettyTcpClient<byte[]> client = new ReactorNettyTcpClient<byte[]>(this.relayHost, this.relayPort, codec);
        client.setLogger(SimpLogging.forLog(client.getLogger()));
        return client;
    }

    @Override
    protected void stopInternal() {
        this.publishBrokerUnavailableEvent();
        if (this.tcpClient != null) {
            try {
                this.tcpClient.shutdown().get(5000L, TimeUnit.MILLISECONDS);
            }
            catch (Throwable ex) {
                this.logger.error((Object)"Error in shutdown of TCP client", ex);
            }
        }
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        StompCommand command;
        StompHeaderAccessor stompAccessor;
        String sessionId = SimpMessageHeaderAccessor.getSessionId(message.getHeaders());
        if (!this.isBrokerAvailable()) {
            if (sessionId == null || SYSTEM_SESSION_ID.equals(sessionId)) {
                throw new MessageDeliveryException("Message broker not active. Consider subscribing to receive BrokerAvailabilityEvent's from an ApplicationListener Spring bean.");
            }
            RelayConnectionHandler handler = this.connectionHandlers.get(sessionId);
            if (handler != null) {
                handler.sendStompErrorFrameToClient("Broker not available.");
                handler.clearConnection();
            } else {
                StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
                if (this.getHeaderInitializer() != null) {
                    this.getHeaderInitializer().initHeaders(accessor);
                }
                accessor.setSessionId(sessionId);
                Principal user = SimpMessageHeaderAccessor.getUser(message.getHeaders());
                if (user != null) {
                    accessor.setUser(user);
                }
                accessor.setMessage("Broker not available.");
                MessageHeaders headers = accessor.getMessageHeaders();
                this.getClientOutboundChannel().send(MessageBuilder.createMessage(EMPTY_PAYLOAD, headers));
            }
            return;
        }
        MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, MessageHeaderAccessor.class);
        if (accessor == null) {
            throw new IllegalStateException("No header accessor (not using the SimpMessagingTemplate?): " + message);
        }
        if (accessor instanceof StompHeaderAccessor) {
            stompAccessor = (StompHeaderAccessor)accessor;
            command = stompAccessor.getCommand();
        } else if (accessor instanceof SimpMessageHeaderAccessor) {
            stompAccessor = StompHeaderAccessor.wrap(message);
            command = stompAccessor.getCommand();
            if (command == null) {
                command = stompAccessor.updateStompCommandAsClientMessage();
            }
        } else {
            throw new IllegalStateException("Unexpected header accessor type " + accessor.getClass() + " in " + message);
        }
        if (sessionId == null) {
            if (!SimpMessageType.MESSAGE.equals((Object)stompAccessor.getMessageType())) {
                if (this.logger.isErrorEnabled()) {
                    this.logger.error((Object)("Only STOMP SEND supported from within the server side. Ignoring " + message));
                }
                return;
            }
            sessionId = SYSTEM_SESSION_ID;
            stompAccessor.setSessionId(sessionId);
        }
        if (StompCommand.CONNECT.equals((Object)command) || StompCommand.STOMP.equals((Object)command)) {
            if (this.connectionHandlers.get(sessionId) != null) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn((Object)("Ignoring CONNECT in session " + sessionId + ". Already connected."));
                }
                return;
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)stompAccessor.getShortLogMessage(EMPTY_PAYLOAD));
            }
            stompAccessor = stompAccessor.isMutable() ? stompAccessor : StompHeaderAccessor.wrap(message);
            stompAccessor.setLogin(this.clientLogin);
            stompAccessor.setPasscode(this.clientPasscode);
            if (this.getVirtualHost() != null) {
                stompAccessor.setHost(this.getVirtualHost());
            }
            RelayConnectionHandler handler = new RelayConnectionHandler(sessionId, stompAccessor);
            this.connectionHandlers.put(sessionId, handler);
            this.stats.incrementConnectCount();
            Assert.state((this.tcpClient != null ? 1 : 0) != 0, (String)"No TCP client available");
            this.tcpClient.connect(handler);
        } else if (StompCommand.DISCONNECT.equals((Object)command)) {
            RelayConnectionHandler handler = this.connectionHandlers.get(sessionId);
            if (handler == null) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Ignoring DISCONNECT in session " + sessionId + ". Connection already cleaned up."));
                }
                return;
            }
            this.stats.incrementDisconnectCount();
            handler.forward(message, stompAccessor);
        } else {
            RelayConnectionHandler handler = this.connectionHandlers.get(sessionId);
            if (handler == null) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("No TCP connection for session " + sessionId + " in " + message));
                }
                return;
            }
            String destination = stompAccessor.getDestination();
            if (command != null && command.requiresDestination() && !this.checkDestinationPrefix(destination)) {
                if (handler.shouldSendHeartbeatForIgnoredMessage()) {
                    handler.forward(HEARTBEAT_MESSAGE, HEART_BEAT_ACCESSOR);
                }
                return;
            }
            handler.forward(message, stompAccessor);
        }
    }

    public String toString() {
        return "StompBrokerRelay[" + this.getTcpClientInfo() + "]";
    }

    private String getTcpClientInfo() {
        return this.tcpClient != null ? this.tcpClient.toString() : this.relayHost + ":" + this.relayPort;
    }

    static {
        EMPTY_TASK.run();
        HEART_BEAT_ACCESSOR = StompHeaderAccessor.createForHeartbeat();
        HEARTBEAT_MESSAGE = MessageBuilder.createMessage(StompDecoder.HEARTBEAT_PAYLOAD, HEART_BEAT_ACCESSOR.getMessageHeaders());
    }

    private class DefaultStats
    implements Stats {
        private final AtomicInteger connect = new AtomicInteger();
        private final AtomicInteger connected = new AtomicInteger();
        private final AtomicInteger disconnect = new AtomicInteger();

        private DefaultStats() {
        }

        public void incrementConnectCount() {
            this.connect.incrementAndGet();
        }

        public void incrementConnectedCount() {
            this.connected.incrementAndGet();
        }

        public void incrementDisconnectCount() {
            this.disconnect.incrementAndGet();
        }

        @Override
        public int getTotalHandlers() {
            return StompBrokerRelayMessageHandler.this.connectionHandlers.size();
        }

        @Override
        public int getTotalConnect() {
            return this.connect.get();
        }

        @Override
        public int getTotalConnected() {
            return this.connected.get();
        }

        @Override
        public int getTotalDisconnect() {
            return this.disconnect.get();
        }

        public String toString() {
            return StompBrokerRelayMessageHandler.this.connectionHandlers.size() + " sessions, " + StompBrokerRelayMessageHandler.this.getTcpClientInfo() + (StompBrokerRelayMessageHandler.this.isBrokerAvailable() ? " (available)" : " (not available)") + ", processed CONNECT(" + this.connect.get() + ")-CONNECTED(" + this.connected.get() + ")-DISCONNECT(" + this.disconnect.get() + ")";
        }
    }

    public static interface Stats {
        public int getTotalHandlers();

        public int getTotalConnect();

        public int getTotalConnected();

        public int getTotalDisconnect();
    }

    private static class VoidCallable
    implements Callable<Void> {
        private VoidCallable() {
        }

        @Override
        public Void call() {
            return null;
        }
    }

    private class ClientSendMessageCountTask
    implements Runnable {
        private ClientSendMessageCountTask() {
        }

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            for (RelayConnectionHandler handler : StompBrokerRelayMessageHandler.this.connectionHandlers.values()) {
                handler.updateClientSendMessageCount(now);
            }
        }
    }

    private class SystemSessionConnectionHandler
    extends RelayConnectionHandler {
        public SystemSessionConnectionHandler(StompHeaderAccessor connectHeaders) {
            super(StompBrokerRelayMessageHandler.SYSTEM_SESSION_ID, connectHeaders, false);
        }

        @Override
        protected void afterStompConnected(StompHeaderAccessor connectedHeaders) {
            if (StompBrokerRelayMessageHandler.this.logger.isInfoEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.info((Object)"\"System\" session connected.");
            }
            super.afterStompConnected(connectedHeaders);
            StompBrokerRelayMessageHandler.this.publishBrokerAvailableEvent();
            this.sendSystemSubscriptions();
        }

        @Override
        protected void initHeartbeats(StompHeaderAccessor connectedHeaders) {
            long interval;
            TcpConnection<byte[]> con = this.getTcpConnection();
            Assert.state((con != null ? 1 : 0) != 0, (String)"No TcpConnection available");
            long clientSendInterval = this.getConnectHeaders().getHeartbeat()[0];
            long clientReceiveInterval = this.getConnectHeaders().getHeartbeat()[1];
            long serverSendInterval = connectedHeaders.getHeartbeat()[0];
            long serverReceiveInterval = connectedHeaders.getHeartbeat()[1];
            if (clientSendInterval > 0L && serverReceiveInterval > 0L) {
                interval = Math.max(clientSendInterval, serverReceiveInterval);
                con.onWriteInactivity(() -> con.send(HEARTBEAT_MESSAGE).addCallback(result -> {}, ex -> this.handleTcpConnectionFailure("Failed to forward heartbeat: " + ex.getMessage(), ex)), interval);
            }
            if (clientReceiveInterval > 0L && serverSendInterval > 0L) {
                interval = Math.max(clientReceiveInterval, serverSendInterval) * 3L;
                con.onReadInactivity(() -> this.handleTcpConnectionFailure("No messages received in " + interval + " ms.", null), interval);
            }
        }

        private void sendSystemSubscriptions() {
            int i2 = 0;
            for (String destination : StompBrokerRelayMessageHandler.this.getSystemSubscriptions().keySet()) {
                TcpConnection<byte[]> conn;
                StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
                accessor.setSubscriptionId(String.valueOf(i2++));
                accessor.setDestination(destination);
                if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                    StompBrokerRelayMessageHandler.this.logger.debug((Object)("Subscribing to " + destination + " on \"system\" connection."));
                }
                if ((conn = this.getTcpConnection()) == null) continue;
                MessageHeaders headers = accessor.getMessageHeaders();
                conn.send(MessageBuilder.createMessage(EMPTY_PAYLOAD, headers)).addCallback(result -> {}, ex -> {
                    String error = "Failed to subscribe in \"system\" session.";
                    this.handleTcpConnectionFailure(error, ex);
                });
            }
        }

        @Override
        protected void handleInboundMessage(Message<?> message) {
            block7: {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null && StompCommand.MESSAGE.equals((Object)accessor.getCommand())) {
                    String destination = accessor.getDestination();
                    if (destination == null) {
                        if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                            StompBrokerRelayMessageHandler.this.logger.debug((Object)("Got message on \"system\" connection, with no destination: " + accessor.getDetailedLogMessage(message.getPayload())));
                        }
                        return;
                    }
                    if (!StompBrokerRelayMessageHandler.this.getSystemSubscriptions().containsKey(destination)) {
                        if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                            StompBrokerRelayMessageHandler.this.logger.debug((Object)("Got message on \"system\" connection with no handler: " + accessor.getDetailedLogMessage(message.getPayload())));
                        }
                        return;
                    }
                    try {
                        MessageHandler handler = StompBrokerRelayMessageHandler.this.getSystemSubscriptions().get(destination);
                        handler.handleMessage(message);
                    }
                    catch (Throwable ex) {
                        if (!StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) break block7;
                        StompBrokerRelayMessageHandler.this.logger.debug((Object)"Error while handling message on \"system\" connection.", ex);
                    }
                }
            }
        }

        @Override
        protected void handleTcpConnectionFailure(String errorMessage, @Nullable Throwable ex) {
            super.handleTcpConnectionFailure(errorMessage, ex);
            StompBrokerRelayMessageHandler.this.publishBrokerUnavailableEvent();
        }

        @Override
        public void afterConnectionClosed() {
            super.afterConnectionClosed();
            StompBrokerRelayMessageHandler.this.publishBrokerUnavailableEvent();
        }

        @Override
        public ListenableFuture<Void> forward(Message<?> message, StompHeaderAccessor accessor) {
            try {
                ListenableFuture<Void> future = super.forward(message, accessor);
                if (message.getHeaders().get("simpIgnoreError") == null) {
                    future.get();
                }
                return future;
            }
            catch (Throwable ex) {
                throw new MessageDeliveryException(message, ex);
            }
        }

        @Override
        protected boolean shouldSendHeartbeatForIgnoredMessage() {
            return false;
        }
    }

    private class RelayConnectionHandler
    implements StompTcpConnectionHandler<byte[]> {
        private final String sessionId;
        private final StompHeaderAccessor connectHeaders;
        private final boolean isRemoteClientSession;
        private final MessageChannel outboundChannel;
        @Nullable
        private volatile TcpConnection<byte[]> tcpConnection;
        private volatile boolean isStompConnected;
        private long clientSendInterval;
        @Nullable
        private final AtomicInteger clientSendMessageCount;
        private long clientSendMessageTimestamp;

        protected RelayConnectionHandler(String sessionId, StompHeaderAccessor connectHeaders) {
            this(sessionId, connectHeaders, true);
        }

        private RelayConnectionHandler(String sessionId, StompHeaderAccessor connectHeaders, boolean isClientSession) {
            Assert.notNull((Object)sessionId, (String)"'sessionId' must not be null");
            Assert.notNull((Object)connectHeaders, (String)"'connectHeaders' must not be null");
            this.sessionId = sessionId;
            this.connectHeaders = connectHeaders;
            this.isRemoteClientSession = isClientSession;
            this.outboundChannel = StompBrokerRelayMessageHandler.this.getClientOutboundChannelForSession(sessionId);
            if (isClientSession && StompBrokerRelayMessageHandler.this.taskScheduler != null) {
                this.clientSendInterval = connectHeaders.getHeartbeat()[0];
            }
            if (this.clientSendInterval > 0L) {
                this.clientSendMessageCount = new AtomicInteger();
                this.clientSendMessageTimestamp = System.currentTimeMillis();
            } else {
                this.clientSendMessageCount = null;
            }
        }

        @Override
        public String getSessionId() {
            return this.sessionId;
        }

        @Override
        public StompHeaderAccessor getConnectHeaders() {
            return this.connectHeaders;
        }

        @Nullable
        protected TcpConnection<byte[]> getTcpConnection() {
            return this.tcpConnection;
        }

        @Override
        public void afterConnected(TcpConnection<byte[]> connection) {
            if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.debug((Object)("TCP connection opened in session=" + this.getSessionId()));
            }
            this.tcpConnection = connection;
            connection.onReadInactivity(() -> {
                if (this.tcpConnection != null && !this.isStompConnected) {
                    this.handleTcpConnectionFailure("No CONNECTED frame received in 60000 ms.", null);
                }
            }, 60000L);
            connection.send(MessageBuilder.createMessage(EMPTY_PAYLOAD, this.connectHeaders.getMessageHeaders()));
        }

        @Override
        public void afterConnectFailure(Throwable ex) {
            this.handleTcpConnectionFailure("Failed to connect: " + ex.getMessage(), ex);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void handleTcpConnectionFailure(String error, @Nullable Throwable ex) {
            if (StompBrokerRelayMessageHandler.this.logger.isInfoEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.info((Object)("TCP connection failure in session " + this.sessionId + ": " + error), ex);
            }
            try {
                this.sendStompErrorFrameToClient(error);
            }
            finally {
                block9: {
                    try {
                        this.clearConnection();
                    }
                    catch (Throwable ex2) {
                        if (!StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) break block9;
                        StompBrokerRelayMessageHandler.this.logger.debug((Object)("Failure while clearing TCP connection state in session " + this.sessionId), ex2);
                    }
                }
            }
        }

        private void sendStompErrorFrameToClient(String errorText) {
            if (this.isRemoteClientSession) {
                StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
                if (StompBrokerRelayMessageHandler.this.getHeaderInitializer() != null) {
                    StompBrokerRelayMessageHandler.this.getHeaderInitializer().initHeaders(accessor);
                }
                accessor.setSessionId(this.sessionId);
                Principal user = this.connectHeaders.getUser();
                if (user != null) {
                    accessor.setUser(user);
                }
                accessor.setMessage(errorText);
                accessor.setLeaveMutable(true);
                Message<byte[]> errorMessage = MessageBuilder.createMessage(EMPTY_PAYLOAD, accessor.getMessageHeaders());
                this.handleInboundMessage(errorMessage);
            }
        }

        protected void handleInboundMessage(Message<?> message) {
            if (this.isRemoteClientSession) {
                this.outboundChannel.send(message);
            }
        }

        @Override
        public void handleMessage(Message<byte[]> message) {
            StompCommand command;
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            Assert.state((accessor != null ? 1 : 0) != 0, (String)"No StompHeaderAccessor");
            accessor.setSessionId(this.sessionId);
            Principal user = this.connectHeaders.getUser();
            if (user != null) {
                accessor.setUser(user);
            }
            if (StompCommand.CONNECTED.equals((Object)(command = accessor.getCommand()))) {
                if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                    StompBrokerRelayMessageHandler.this.logger.debug((Object)("Received " + accessor.getShortLogMessage(EMPTY_PAYLOAD)));
                }
                this.afterStompConnected(accessor);
            } else if (StompBrokerRelayMessageHandler.this.logger.isErrorEnabled() && StompCommand.ERROR.equals((Object)command)) {
                StompBrokerRelayMessageHandler.this.logger.error((Object)("Received " + accessor.getShortLogMessage(message.getPayload())));
            } else if (StompBrokerRelayMessageHandler.this.logger.isTraceEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.trace((Object)("Received " + accessor.getDetailedLogMessage(message.getPayload())));
            }
            this.handleInboundMessage(message);
        }

        protected void afterStompConnected(StompHeaderAccessor connectedHeaders) {
            this.isStompConnected = true;
            StompBrokerRelayMessageHandler.this.stats.incrementConnectedCount();
            this.initHeartbeats(connectedHeaders);
        }

        protected void initHeartbeats(StompHeaderAccessor connectedHeaders) {
            if (StompBrokerRelayMessageHandler.this.taskScheduler != null) {
                long interval = connectedHeaders.getHeartbeat()[1];
                this.clientSendInterval = Math.max(interval, this.clientSendInterval);
            }
        }

        protected boolean shouldSendHeartbeatForIgnoredMessage() {
            return this.clientSendMessageCount != null && this.clientSendMessageCount.get() == 0;
        }

        void updateClientSendMessageCount(long now) {
            if (this.clientSendMessageCount != null && this.clientSendInterval > now - this.clientSendMessageTimestamp) {
                this.clientSendMessageCount.set(0);
                this.clientSendMessageTimestamp = now;
            }
        }

        @Override
        public void handleFailure(Throwable ex) {
            if (this.tcpConnection != null) {
                this.handleTcpConnectionFailure("Transport failure: " + ex.getMessage(), ex);
            } else if (StompBrokerRelayMessageHandler.this.logger.isErrorEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.error((Object)("Transport failure: " + ex));
            }
        }

        @Override
        public void afterConnectionClosed() {
            if (this.tcpConnection == null) {
                return;
            }
            try {
                if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                    StompBrokerRelayMessageHandler.this.logger.debug((Object)("TCP connection to broker closed in session " + this.sessionId));
                }
                this.sendStompErrorFrameToClient("Connection to broker closed.");
            }
            finally {
                try {
                    this.tcpConnection = null;
                    this.clearConnection();
                }
                catch (Throwable throwable) {}
            }
        }

        public ListenableFuture<Void> forward(final Message<?> message, final StompHeaderAccessor accessor) {
            TcpConnection<byte[]> conn = this.tcpConnection;
            if (!this.isStompConnected || conn == null) {
                if (this.isRemoteClientSession) {
                    if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                        StompBrokerRelayMessageHandler.this.logger.debug((Object)("TCP connection closed already, ignoring " + accessor.getShortLogMessage(message.getPayload())));
                    }
                    return EMPTY_TASK;
                }
                throw new IllegalStateException("Cannot forward messages " + (conn != null ? "before STOMP CONNECTED. " : "while inactive. ") + "Consider subscribing to receive BrokerAvailabilityEvent's from an ApplicationListener Spring bean. Dropped " + accessor.getShortLogMessage(message.getPayload()));
            }
            if (this.clientSendMessageCount != null) {
                this.clientSendMessageCount.incrementAndGet();
            }
            Message<?> messageToSend = accessor.isMutable() && accessor.isModified() ? MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders()) : message;
            StompCommand command = accessor.getCommand();
            if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled() && (StompCommand.SEND.equals((Object)command) || StompCommand.SUBSCRIBE.equals((Object)command) || StompCommand.UNSUBSCRIBE.equals((Object)command) || StompCommand.DISCONNECT.equals((Object)command))) {
                StompBrokerRelayMessageHandler.this.logger.debug((Object)("Forwarding " + accessor.getShortLogMessage(message.getPayload())));
            } else if (StompBrokerRelayMessageHandler.this.logger.isTraceEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.trace((Object)("Forwarding " + accessor.getDetailedLogMessage(message.getPayload())));
            }
            ListenableFuture<Void> future = conn.send(messageToSend);
            future.addCallback((ListenableFutureCallback)new ListenableFutureCallback<Void>(){

                public void onSuccess(@Nullable Void result) {
                    if (accessor.getCommand() == StompCommand.DISCONNECT) {
                        RelayConnectionHandler.this.afterDisconnectSent(accessor);
                    }
                }

                public void onFailure(Throwable ex) {
                    if (RelayConnectionHandler.this.tcpConnection != null) {
                        RelayConnectionHandler.this.handleTcpConnectionFailure("failed to forward " + accessor.getShortLogMessage(message.getPayload()), ex);
                    } else if (StompBrokerRelayMessageHandler.this.logger.isErrorEnabled()) {
                        StompBrokerRelayMessageHandler.this.logger.error((Object)("Failed to forward " + accessor.getShortLogMessage(message.getPayload())));
                    }
                }
            });
            return future;
        }

        private void afterDisconnectSent(StompHeaderAccessor accessor) {
            block3: {
                if (accessor.getReceipt() == null) {
                    try {
                        this.clearConnection();
                    }
                    catch (Throwable ex) {
                        if (!StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) break block3;
                        StompBrokerRelayMessageHandler.this.logger.debug((Object)("Failure while clearing TCP connection state in session " + this.sessionId), ex);
                    }
                }
            }
        }

        public void clearConnection() {
            if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                StompBrokerRelayMessageHandler.this.logger.debug((Object)("Cleaning up connection state for session " + this.sessionId));
            }
            if (this.isRemoteClientSession) {
                StompBrokerRelayMessageHandler.this.connectionHandlers.remove(this.sessionId);
            }
            this.isStompConnected = false;
            TcpConnection<byte[]> conn = this.tcpConnection;
            this.tcpConnection = null;
            if (conn != null) {
                if (StompBrokerRelayMessageHandler.this.logger.isDebugEnabled()) {
                    StompBrokerRelayMessageHandler.this.logger.debug((Object)("Closing TCP connection in session " + this.sessionId));
                }
                conn.close();
            }
        }

        public String toString() {
            return "StompConnectionHandler[sessionId=" + this.sessionId + "]";
        }
    }
}

