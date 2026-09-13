/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.Lifecycle;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

class StandardIntegrationFlowRegistration
implements IntegrationFlowContext.IntegrationFlowRegistration {
    private final IntegrationFlow integrationFlow;
    private final IntegrationFlowContext integrationFlowContext;
    private final String id;
    private MessageChannel inputChannel;
    private MessagingTemplate messagingTemplate;
    private ConfigurableListableBeanFactory beanFactory;

    StandardIntegrationFlowRegistration(IntegrationFlow integrationFlow2, IntegrationFlowContext integrationFlowContext, String id) {
        this.integrationFlow = integrationFlow2;
        this.integrationFlowContext = integrationFlowContext;
        this.id = id;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public IntegrationFlow getIntegrationFlow() {
        return this.integrationFlow;
    }

    @Override
    public MessageChannel getInputChannel() {
        if (this.inputChannel == null) {
            this.inputChannel = this.integrationFlow.getInputChannel();
            if (this.inputChannel == null) {
                throw new IllegalStateException("Only 'IntegrationFlow' instances started from the 'MessageChannel' (e.g. extracted from 'IntegrationFlow' Lambdas) can be used for direct 'send' operation. But [" + this.integrationFlow + "] isn't one of them.\nConsider 'BeanFactory.getBean()' usage for sending messages to the required 'MessageChannel'.");
            }
        }
        return this.inputChannel;
    }

    @Override
    public MessagingTemplate getMessagingTemplate() {
        if (this.messagingTemplate == null) {
            this.messagingTemplate = new MessagingTemplate(this.getInputChannel()){

                public Message<?> receive() {
                    return this.receiveAndConvert(Message.class);
                }

                public <T> T receiveAndConvert(Class<T> targetClass) {
                    throw new UnsupportedOperationException("The 'receive()/receiveAndConvert()' isn't supported on the 'IntegrationFlow' input channel.");
                }
            };
            this.messagingTemplate.setBeanFactory((BeanFactory)this.beanFactory);
        }
        return this.messagingTemplate;
    }

    @Override
    public void start() {
        if (!(this.integrationFlow instanceof Lifecycle)) {
            throw new IllegalStateException("For 'autoStartup' mode the 'IntegrationFlow' must be an instance of 'Lifecycle'.\nConsider to implement it for [" + this.integrationFlow + "]. Or start dependent components on their own.");
        }
        ((Lifecycle)this.integrationFlow).start();
    }

    @Override
    public void stop() {
        if (this.integrationFlow instanceof Lifecycle) {
            ((Lifecycle)this.integrationFlow).stop();
        }
    }

    @Override
    public void destroy() {
        this.integrationFlowContext.remove(this.id);
    }

    public String toString() {
        return "IntegrationFlowRegistration{integrationFlow=" + this.integrationFlow + ", id='" + this.id + '\'' + ", inputChannel=" + this.inputChannel + '}';
    }
}

