/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  reactor.core.CoreSubscriber
 */
package org.springframework.integration.handler;

import org.reactivestreams.Subscription;
import org.springframework.integration.handler.MessageHandlerSupport;
import org.springframework.integration.history.MessageHistory;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.support.management.metrics.SampleFacade;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import reactor.core.CoreSubscriber;

public abstract class AbstractMessageHandler
extends MessageHandlerSupport
implements MessageHandler,
CoreSubscriber<Message<?>> {
    public void handleMessage(Message<?> message) {
        Message<?> messageToUse = message;
        Assert.notNull(messageToUse, (String)"Message must not be null");
        if (this.isLoggingEnabled() && this.logger.isDebugEnabled()) {
            this.logger.debug((CharSequence)(this + " received message: " + messageToUse));
        }
        SampleFacade sample = null;
        MetricsCaptor metricsCaptor = this.getMetricsCaptor();
        if (metricsCaptor != null) {
            sample = metricsCaptor.start();
        }
        try {
            if (this.shouldTrack()) {
                messageToUse = MessageHistory.write(messageToUse, this, this.getMessageBuilderFactory());
            }
            this.handleMessageInternal(messageToUse);
            if (sample != null) {
                sample.stop(this.sendTimer());
            }
        }
        catch (Exception e) {
            if (sample != null) {
                sample.stop(this.buildSendTimer(false, e.getClass().getSimpleName()));
            }
            throw IntegrationUtils.wrapInHandlingExceptionIfNecessary(messageToUse, () -> "error occurred in message handler [" + this + "]", e);
        }
    }

    public void onSubscribe(Subscription subscription) {
        Assert.notNull((Object)subscription, (String)"'subscription' must not be null");
        subscription.request(Long.MAX_VALUE);
    }

    public void onError(Throwable throwable) {
    }

    public void onComplete() {
    }

    public void onNext(Message<?> message) {
        this.handleMessage(message);
    }

    protected abstract void handleMessageInternal(Message<?> var1);
}

