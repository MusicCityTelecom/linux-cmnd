/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.gateway;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.gateway.RequestReplyExchanger;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class GatewayMessageHandler
extends AbstractReplyProducingMessageHandler
implements ManageableLifecycle {
    private final GatewayProxyFactoryBean gatewayProxyFactoryBean = new GatewayProxyFactoryBean();
    private RequestReplyExchanger exchanger;
    private volatile boolean running;

    public void setRequestChannel(MessageChannel requestChannel) {
        this.gatewayProxyFactoryBean.setDefaultRequestChannel(requestChannel);
    }

    public void setRequestChannelName(String requestChannel) {
        this.gatewayProxyFactoryBean.setDefaultRequestChannelName(requestChannel);
    }

    public void setReplyChannel(MessageChannel replyChannel) {
        this.gatewayProxyFactoryBean.setDefaultReplyChannel(replyChannel);
    }

    public void setReplyChannelName(String replyChannel) {
        this.gatewayProxyFactoryBean.setDefaultReplyChannelName(replyChannel);
    }

    public void setErrorChannel(MessageChannel errorChannel) {
        this.gatewayProxyFactoryBean.setErrorChannel(errorChannel);
    }

    public void setErrorChannelName(String errorChannel) {
        this.gatewayProxyFactoryBean.setErrorChannelName(errorChannel);
    }

    public void setRequestTimeout(Long requestTimeout) {
        this.gatewayProxyFactoryBean.setDefaultRequestTimeout(requestTimeout);
    }

    public void setReplyTimeout(Long replyTimeout) {
        this.gatewayProxyFactoryBean.setDefaultReplyTimeout(replyTimeout);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object handleRequestMessage(Message<?> requestMessage) {
        if (this.exchanger == null) {
            GatewayMessageHandler gatewayMessageHandler = this;
            synchronized (gatewayMessageHandler) {
                if (this.exchanger == null) {
                    this.initialize();
                }
            }
        }
        return this.exchanger.exchange(requestMessage);
    }

    private void initialize() {
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            ((ConfigurableListableBeanFactory)beanFactory).initializeBean((Object)this.gatewayProxyFactoryBean, this.getComponentName() + "#gpfb");
        }
        try {
            this.exchanger = (RequestReplyExchanger)this.gatewayProxyFactoryBean.getObject();
        }
        catch (Exception e) {
            throw new BeanCreationException("Can't instantiate the GatewayProxyFactoryBean: " + this, (Throwable)e);
        }
        if (this.running) {
            this.gatewayProxyFactoryBean.stop();
            this.gatewayProxyFactoryBean.start();
        }
    }

    @Override
    public void start() {
        this.gatewayProxyFactoryBean.start();
        this.running = true;
    }

    @Override
    public void stop() {
        this.gatewayProxyFactoryBean.stop();
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }
}

