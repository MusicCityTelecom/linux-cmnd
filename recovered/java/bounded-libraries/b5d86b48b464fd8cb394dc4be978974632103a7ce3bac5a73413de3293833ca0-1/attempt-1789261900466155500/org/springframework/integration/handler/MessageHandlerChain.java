/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.context.Lifecycle;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.CompositeMessageHandler;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public class MessageHandlerChain
extends AbstractMessageProducingHandler
implements CompositeMessageHandler,
ManageableLifecycle {
    private final Object initializationMonitor = new Object();
    private final ReentrantLock lifecycleLock = new ReentrantLock();
    private List<MessageHandler> handlers;
    private volatile boolean initialized;
    private volatile boolean running;

    public void setHandlers(List<MessageHandler> handlers) {
        this.handlers = new LinkedList<MessageHandler>(handlers);
    }

    @Override
    public List<MessageHandler> getHandlers() {
        return Collections.unmodifiableList(this.handlers);
    }

    @Override
    public String getComponentType() {
        return "chain";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.chain;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void onInit() {
        super.onInit();
        Object object = this.initializationMonitor;
        synchronized (object) {
            if (!this.initialized) {
                Assert.notEmpty(this.handlers, (String)"handler list must not be empty");
                this.configureChain();
                this.initialized = true;
            }
        }
    }

    private void configureChain() {
        Assert.isTrue((this.handlers.size() == new HashSet<MessageHandler>(this.handlers).size() ? 1 : 0) != 0, (String)"duplicate handlers are not allowed in a chain");
        for (int i = 0; i < this.handlers.size(); ++i) {
            MessageHandler handler = this.handlers.get(i);
            if (i < this.handlers.size() - 1) {
                Assert.isInstanceOf(MessageProducer.class, (Object)handler, (String)"All handlers except for the last one in the chain must implement the MessageProducer interface.");
                MessageHandler nextHandler = this.handlers.get(i + 1);
                MessageChannel nextChannel = (message, timeout) -> {
                    nextHandler.handleMessage(message);
                    return true;
                };
                ((MessageProducer)handler).setOutputChannel(nextChannel);
                if (!(handler instanceof MessageHandlerChain)) continue;
                ((MessageHandlerChain)handler).initialized = false;
                ((MessageHandlerChain)handler).afterPropertiesSet();
                continue;
            }
            if (handler instanceof MessageProducer) {
                ReplyForwardingMessageChannel replyChannel = new ReplyForwardingMessageChannel();
                ((MessageProducer)handler).setOutputChannel(replyChannel);
                continue;
            }
            Assert.isNull((Object)this.getOutputChannel(), (String)"An output channel was provided, but the final handler in the chain does not implement the MessageProducer interface.");
        }
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        if (!this.initialized) {
            this.onInit();
        }
        this.handlers.get(0).handleMessage(message);
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }

    @Override
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

    @Override
    public final void start() {
        this.lifecycleLock.lock();
        try {
            if (!this.running) {
                this.doStart();
                this.running = true;
                this.logger.info(() -> "started " + this);
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public final void stop() {
        this.lifecycleLock.lock();
        try {
            if (this.running) {
                this.doStop();
                this.running = false;
                this.logger.info(() -> "stopped " + this);
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    public final void stop(Runnable callback) {
        this.lifecycleLock.lock();
        try {
            this.stop();
            callback.run();
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    private void doStop() {
        for (MessageHandler handler : this.handlers) {
            if (!(handler instanceof Lifecycle)) continue;
            ((Lifecycle)handler).stop();
        }
    }

    private void doStart() {
        for (MessageHandler handler : this.handlers) {
            if (!(handler instanceof Lifecycle)) continue;
            ((Lifecycle)handler).start();
        }
    }

    private final class ReplyForwardingMessageChannel
    implements MessageChannel {
        ReplyForwardingMessageChannel() {
        }

        public boolean send(Message<?> message, long timeout) {
            MessageHandlerChain.this.produceOutput(message, message);
            return true;
        }
    }
}

