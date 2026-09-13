/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.util.Assert;

public class MessageGroupStoreReaper
implements Runnable,
DisposableBean,
InitializingBean,
SmartLifecycle {
    private static Log logger = LogFactory.getLog(MessageGroupStoreReaper.class);
    private final ReentrantLock lifecycleLock = new ReentrantLock();
    private MessageGroupStore messageGroupStore;
    private boolean expireOnDestroy = false;
    private long timeout = -1L;
    private int phase = 0;
    private boolean autoStartup = true;
    private volatile boolean running;

    public MessageGroupStoreReaper(MessageGroupStore messageGroupStore) {
        this.messageGroupStore = messageGroupStore;
    }

    public MessageGroupStoreReaper() {
    }

    public void setExpireOnDestroy(boolean expireOnDestroy) {
        this.expireOnDestroy = expireOnDestroy;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setMessageGroupStore(MessageGroupStore messageGroupStore) {
        this.messageGroupStore = messageGroupStore;
    }

    public void afterPropertiesSet() {
        Assert.state((this.messageGroupStore != null ? 1 : 0) != 0, (String)"A MessageGroupStore must be provided");
    }

    public void destroy() {
        if (this.expireOnDestroy) {
            if (this.isRunning()) {
                logger.info((Object)("Expiring all messages from message group store: " + this.messageGroupStore));
                this.messageGroupStore.expireMessageGroups(0L);
            } else {
                logger.debug((Object)"'expireOnDestroy' is set to 'true' but the reaper is not currently running");
            }
        }
    }

    @Override
    public void run() {
        if (this.timeout >= 0L && this.isRunning()) {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("Expiring all messages older than timeout=" + this.timeout + " from message group store: " + this.messageGroupStore));
            }
            this.messageGroupStore.expireMessageGroups(this.timeout);
        }
    }

    public final void start() {
        this.lifecycleLock.lock();
        try {
            if (!this.running) {
                this.running = true;
                if (logger.isInfoEnabled()) {
                    logger.info((Object)("started " + this));
                }
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    public void stop() {
        this.lifecycleLock.lock();
        try {
            if (this.running) {
                this.destroy();
                if (logger.isInfoEnabled()) {
                    logger.info((Object)("stopped " + this));
                }
            }
            this.running = false;
        }
        catch (Exception e) {
            logger.error((Object)"failed to stop bean", (Throwable)e);
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    public final boolean isRunning() {
        this.lifecycleLock.lock();
        try {
            boolean bl = this.running;
            return bl;
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    public int getPhase() {
        return this.phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }
}

