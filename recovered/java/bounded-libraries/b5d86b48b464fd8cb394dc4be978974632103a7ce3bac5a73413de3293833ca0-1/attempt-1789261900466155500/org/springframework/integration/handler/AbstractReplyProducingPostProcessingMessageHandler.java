/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.PostProcessingMessageHandler;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public abstract class AbstractReplyProducingPostProcessingMessageHandler
extends AbstractReplyProducingMessageHandler
implements PostProcessingMessageHandler {
    private volatile boolean postProcessWithinAdvice = true;

    public void setPostProcessWithinAdvice(boolean postProcessWithinAdvice) {
        this.postProcessWithinAdvice = postProcessWithinAdvice;
    }

    @Override
    @Nullable
    protected final Object handleRequestMessage(Message<?> requestMessage) {
        Object result = this.doHandleRequestMessage(requestMessage);
        if (this.postProcessWithinAdvice || !this.hasAdviceChain()) {
            this.postProcess(requestMessage, result);
        }
        return result;
    }

    @Override
    @Nullable
    protected final Object doInvokeAdvisedRequestHandler(Message<?> message) {
        Object result = super.doInvokeAdvisedRequestHandler(message);
        if (!this.postProcessWithinAdvice) {
            this.postProcess(message, result);
        }
        return result;
    }

    @Nullable
    protected abstract Object doHandleRequestMessage(Message<?> var1);
}

