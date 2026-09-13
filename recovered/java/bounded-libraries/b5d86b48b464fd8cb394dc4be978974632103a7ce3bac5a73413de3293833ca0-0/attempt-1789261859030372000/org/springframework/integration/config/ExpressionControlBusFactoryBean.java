/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.MethodFilter
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.config;

import org.springframework.expression.MethodFilter;
import org.springframework.integration.config.AbstractSimpleMessageHandlerFactoryBean;
import org.springframework.integration.expression.ControlBusMethodFilter;
import org.springframework.integration.handler.ExpressionCommandMessageProcessor;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.messaging.MessageHandler;

public class ExpressionControlBusFactoryBean
extends AbstractSimpleMessageHandlerFactoryBean<MessageHandler> {
    private static final MethodFilter METHOD_FILTER = new ControlBusMethodFilter();
    private Long sendTimeout;

    public void setSendTimeout(Long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    @Override
    protected MessageHandler createHandler() {
        ExpressionCommandMessageProcessor processor = new ExpressionCommandMessageProcessor(METHOD_FILTER, this.getBeanFactory());
        ServiceActivatingHandler handler = new ServiceActivatingHandler(processor);
        if (this.sendTimeout != null) {
            handler.setSendTimeout(this.sendTimeout);
        }
        return handler;
    }
}

