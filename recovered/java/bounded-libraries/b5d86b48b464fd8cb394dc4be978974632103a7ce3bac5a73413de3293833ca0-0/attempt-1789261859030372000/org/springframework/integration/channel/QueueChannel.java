/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.springframework.integration.channel.AbstractPollableChannel;
import org.springframework.integration.channel.QueueChannelOperations;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.support.management.metrics.GaugeFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class QueueChannel
extends AbstractPollableChannel
implements QueueChannelOperations {
    private final Queue<Message<?>> queue;
    protected final Semaphore queueSemaphore = new Semaphore(0);
    @Nullable
    private GaugeFacade sizeGauge;
    @Nullable
    private GaugeFacade remainingCapacityGauge;

    public QueueChannel(Queue<Message<?>> queue) {
        Assert.notNull(queue, (String)"'queue' must not be null");
        this.queue = queue;
    }

    public QueueChannel(int capacity) {
        Assert.isTrue((capacity > 0 ? 1 : 0) != 0, (String)"The capacity must be a positive integer. For a zero-capacity alternative, consider using a 'RendezvousChannel'.");
        this.queue = new LinkedBlockingQueue(capacity);
    }

    public QueueChannel() {
        this(new LinkedBlockingQueue());
    }

    @Override
    public void registerMetricsCaptor(MetricsCaptor metricsCaptor) {
        super.registerMetricsCaptor(metricsCaptor);
        this.sizeGauge = metricsCaptor.gaugeBuilder("spring.integration.channel.queue.size", this, channel -> this.getQueueSize()).tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("type", "channel").description("The size of the queue channel").build();
        this.remainingCapacityGauge = metricsCaptor.gaugeBuilder("spring.integration.channel.queue.remaining.capacity", this, channel -> this.getRemainingCapacity()).tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("type", "channel").description("The remaining capacity of the queue channel").build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean doSend(Message<?> message, long timeout) {
        Assert.notNull(message, (String)"'message' must not be null");
        if (this.queue instanceof BlockingQueue) {
            BlockingQueue blockingQueue = (BlockingQueue)this.queue;
            if (timeout > 0L) {
                return blockingQueue.offer(message, timeout, TimeUnit.MILLISECONDS);
            }
            if (timeout == 0L) {
                return blockingQueue.offer(message);
            }
            blockingQueue.put(message);
            return true;
        }
        try {
            boolean blockingQueue = this.queue.offer(message);
            this.queueSemaphore.release();
            return blockingQueue;
        }
        catch (Throwable throwable) {
            try {
                this.queueSemaphore.release();
                throw throwable;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
    }

    @Override
    @Nullable
    protected Message<?> doReceive(long timeout) {
        try {
            if (timeout > 0L) {
                if (this.queue instanceof BlockingQueue) {
                    return (Message)((BlockingQueue)this.queue).poll(timeout, TimeUnit.MILLISECONDS);
                }
                return this.pollNonBlockingQueue(timeout);
            }
            if (timeout == 0L) {
                return this.queue.poll();
            }
            if (this.queue instanceof BlockingQueue) {
                return (Message)((BlockingQueue)this.queue).take();
            }
            Message<?> message = this.queue.poll();
            while (message == null) {
                this.queueSemaphore.tryAcquire(50L, TimeUnit.MILLISECONDS);
                message = this.queue.poll();
            }
            return message;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Nullable
    private Message<?> pollNonBlockingQueue(long timeout) throws InterruptedException {
        Message<?> message = this.queue.poll();
        if (message == null) {
            long nanos = TimeUnit.MILLISECONDS.toNanos(timeout);
            long deadline = System.nanoTime() + nanos;
            while (message == null && nanos > 0L) {
                this.queueSemaphore.tryAcquire(nanos, TimeUnit.NANOSECONDS);
                message = this.queue.poll();
                if (message != null) continue;
                nanos = deadline - System.nanoTime();
            }
        }
        return message;
    }

    @Override
    public List<Message<?>> clear() {
        ArrayList clearedMessages = new ArrayList();
        if (this.queue instanceof BlockingQueue) {
            ((BlockingQueue)this.queue).drainTo(clearedMessages);
        } else {
            Message<?> message;
            while ((message = this.queue.poll()) != null) {
                clearedMessages.add(message);
            }
        }
        return clearedMessages;
    }

    @Override
    public List<Message<?>> purge(@Nullable MessageSelector selector) {
        Object[] array;
        if (selector == null) {
            return this.clear();
        }
        ArrayList purgedMessages = new ArrayList();
        for (Object o : array = this.queue.toArray()) {
            Message message = (Message)o;
            if (selector.accept(message) || !this.queue.remove(message)) continue;
            purgedMessages.add(message);
        }
        return purgedMessages;
    }

    @Override
    public int getQueueSize() {
        return this.queue.size();
    }

    @Override
    public int getRemainingCapacity() {
        if (this.queue instanceof BlockingQueue) {
            return ((BlockingQueue)this.queue).remainingCapacity();
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (this.sizeGauge != null) {
            this.sizeGauge.remove();
        }
        if (this.remainingCapacityGauge != null) {
            this.remainingCapacityGauge.remove();
        }
    }
}

