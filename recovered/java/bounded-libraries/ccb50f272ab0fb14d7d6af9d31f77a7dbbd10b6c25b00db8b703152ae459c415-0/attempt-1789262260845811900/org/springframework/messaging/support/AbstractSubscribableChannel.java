/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.AbstractMessageChannel;

public abstract class AbstractSubscribableChannel
extends AbstractMessageChannel
implements SubscribableChannel {
    private final Set<MessageHandler> handlers = new CopyOnWriteArraySet<MessageHandler>();

    public Set<MessageHandler> getSubscribers() {
        return Collections.unmodifiableSet(this.handlers);
    }

    public boolean hasSubscription(MessageHandler handler) {
        return this.handlers.contains(handler);
    }

    @Override
    public boolean subscribe(MessageHandler handler) {
        boolean result = this.handlers.add(handler);
        if (result && this.logger.isDebugEnabled()) {
            this.logger.debug((Object)(this.getBeanName() + " added " + handler));
        }
        return result;
    }

    @Override
    public boolean unsubscribe(MessageHandler handler) {
        boolean result = this.handlers.remove(handler);
        if (result && this.logger.isDebugEnabled()) {
            this.logger.debug((Object)(this.getBeanName() + " removed " + handler));
        }
        return result;
    }
}

