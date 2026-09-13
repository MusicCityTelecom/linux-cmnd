/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.stomp;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.ConnectionHandlingStompSession;
import org.springframework.messaging.simp.stomp.DefaultStompSession;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

public abstract class StompClientSupport {
    private MessageConverter messageConverter = new SimpleMessageConverter();
    @Nullable
    private TaskScheduler taskScheduler;
    private long[] defaultHeartbeat = new long[]{10000L, 10000L};
    private long receiptTimeLimit = TimeUnit.SECONDS.toMillis(15L);

    public void setMessageConverter(MessageConverter messageConverter) {
        Assert.notNull((Object)messageConverter, (String)"MessageConverter must not be null");
        this.messageConverter = messageConverter;
    }

    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }

    public void setTaskScheduler(@Nullable TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Nullable
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    public void setDefaultHeartbeat(long[] heartbeat) {
        if (heartbeat.length != 2 || heartbeat[0] < 0L || heartbeat[1] < 0L) {
            throw new IllegalArgumentException("Invalid heart-beat: " + Arrays.toString(heartbeat));
        }
        this.defaultHeartbeat = heartbeat;
    }

    public long[] getDefaultHeartbeat() {
        return this.defaultHeartbeat;
    }

    public boolean isDefaultHeartbeatEnabled() {
        long[] heartbeat = this.getDefaultHeartbeat();
        return heartbeat[0] != 0L && heartbeat[1] != 0L;
    }

    public void setReceiptTimeLimit(long receiptTimeLimit) {
        Assert.isTrue((receiptTimeLimit > 0L ? 1 : 0) != 0, (String)"Receipt time limit must be larger than zero");
        this.receiptTimeLimit = receiptTimeLimit;
    }

    public long getReceiptTimeLimit() {
        return this.receiptTimeLimit;
    }

    protected ConnectionHandlingStompSession createSession(@Nullable StompHeaders connectHeaders, StompSessionHandler handler) {
        connectHeaders = this.processConnectHeaders(connectHeaders);
        DefaultStompSession session = new DefaultStompSession(handler, connectHeaders);
        session.setMessageConverter(this.getMessageConverter());
        session.setTaskScheduler(this.getTaskScheduler());
        session.setReceiptTimeLimit(this.getReceiptTimeLimit());
        return session;
    }

    protected StompHeaders processConnectHeaders(@Nullable StompHeaders connectHeaders) {
        StompHeaders stompHeaders = connectHeaders = connectHeaders != null ? connectHeaders : new StompHeaders();
        if (connectHeaders.getHeartbeat() == null) {
            connectHeaders.setHeartbeat(this.getDefaultHeartbeat());
        }
        return connectHeaders;
    }
}

