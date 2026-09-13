/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.channel;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.support.channel.HeaderChannelRegistry;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class DefaultHeaderChannelRegistry
extends IntegrationObjectSupport
implements HeaderChannelRegistry,
ManageableLifecycle,
Runnable {
    private static final int DEFAULT_REAPER_DELAY = 60000;
    protected static final AtomicLong id = new AtomicLong();
    protected final Map<String, MessageChannelWrapper> channels = new ConcurrentHashMap<String, MessageChannelWrapper>();
    protected final String uuid = UUID.randomUUID().toString() + ":";
    private boolean removeOnGet;
    private long reaperDelay;
    private volatile ScheduledFuture<?> reaperScheduledFuture;
    private volatile boolean running;
    private volatile boolean explicitlyStopped;

    public DefaultHeaderChannelRegistry() {
        this(60000L);
    }

    public DefaultHeaderChannelRegistry(long reaperDelay) {
        this.setReaperDelay(reaperDelay);
    }

    public final void setReaperDelay(long reaperDelay) {
        Assert.isTrue((reaperDelay > 0L ? 1 : 0) != 0, (String)"'reaperDelay' must be > 0");
        this.reaperDelay = reaperDelay;
    }

    public final long getReaperDelay() {
        return this.reaperDelay;
    }

    public void setRemoveOnGet(boolean removeOnGet) {
        this.removeOnGet = removeOnGet;
    }

    @Override
    public final int size() {
        return this.channels.size();
    }

    @Override
    protected void onInit() {
        super.onInit();
        Assert.notNull((Object)this.getTaskScheduler(), (String)"a task scheduler is required");
    }

    @Override
    public synchronized void start() {
        if (!this.running) {
            Assert.notNull((Object)this.getTaskScheduler(), (String)"a task scheduler is required");
            this.reaperScheduledFuture = this.getTaskScheduler().schedule((Runnable)this, new Date(System.currentTimeMillis() + this.reaperDelay));
            this.running = true;
        }
    }

    @Override
    public synchronized void stop() {
        this.running = false;
        if (this.reaperScheduledFuture != null) {
            this.reaperScheduledFuture.cancel(true);
            this.reaperScheduledFuture = null;
        }
        this.explicitlyStopped = true;
    }

    public void stop(Runnable callback) {
        this.stop();
        callback.run();
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    @Nullable
    public Object channelToChannelName(@Nullable Object channel) {
        return this.channelToChannelName(channel, this.reaperDelay);
    }

    @Override
    @Nullable
    public Object channelToChannelName(@Nullable Object channel, long timeToLive) {
        if (!this.running && !this.explicitlyStopped && this.getTaskScheduler() != null) {
            this.start();
        }
        if (channel instanceof MessageChannel) {
            String name = this.uuid + id.incrementAndGet();
            this.channels.put(name, new MessageChannelWrapper((MessageChannel)channel, System.currentTimeMillis() + timeToLive));
            this.logger.debug(() -> "Registered " + channel + " as " + name);
            return name;
        }
        return channel;
    }

    @Override
    @Nullable
    public MessageChannel channelNameToChannel(@Nullable String name) {
        if (name != null) {
            MessageChannelWrapper messageChannelWrapper = this.removeOnGet ? this.channels.remove(name) : this.channels.get(name);
            if (messageChannelWrapper != null) {
                this.logger.debug(() -> "Retrieved " + messageChannelWrapper.getChannel() + " with " + name);
            }
            return messageChannelWrapper == null ? null : messageChannelWrapper.getChannel();
        }
        return null;
    }

    @Override
    public synchronized void runReaper() {
        if (this.reaperScheduledFuture != null) {
            this.reaperScheduledFuture.cancel(true);
            this.reaperScheduledFuture = null;
        }
        this.run();
    }

    @Override
    public synchronized void run() {
        this.logger.trace(() -> "Reaper started; channels size=" + this.channels.size());
        Iterator<Map.Entry<String, MessageChannelWrapper>> iterator = this.channels.entrySet().iterator();
        long now = System.currentTimeMillis();
        while (iterator.hasNext()) {
            Map.Entry<String, MessageChannelWrapper> entry = iterator.next();
            if (entry.getValue().getExpireAt() >= now) continue;
            this.logger.debug(() -> "Expiring " + (String)entry.getKey() + " (" + ((MessageChannelWrapper)entry.getValue()).getChannel() + ")");
            iterator.remove();
        }
        this.reaperScheduledFuture = this.getTaskScheduler().schedule((Runnable)this, new Date(System.currentTimeMillis() + this.reaperDelay));
        this.logger.trace(() -> "Reaper completed; channels size=" + this.channels.size());
    }

    private static final class MessageChannelWrapper {
        private final MessageChannel channel;
        private final long expireAt;

        MessageChannelWrapper(MessageChannel channel, long expireAt) {
            this.channel = channel;
            this.expireAt = expireAt;
        }

        public long getExpireAt() {
            return this.expireAt;
        }

        public MessageChannel getChannel() {
            return this.channel;
        }
    }
}

