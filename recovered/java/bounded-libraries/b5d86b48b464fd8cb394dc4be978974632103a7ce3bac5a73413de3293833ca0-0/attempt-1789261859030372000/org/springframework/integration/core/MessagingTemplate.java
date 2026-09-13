/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.GenericMessagingTemplate
 */
package org.springframework.integration.core;

import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.GenericMessagingTemplate;

public class MessagingTemplate
extends GenericMessagingTemplate {
    private BeanFactory beanFactory;
    private volatile boolean throwExceptionOnLateReplySet;

    public MessagingTemplate() {
    }

    public MessagingTemplate(MessageChannel defaultChannel) {
        super.setDefaultDestination((Object)defaultChannel);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.setDestinationResolver(ChannelResolverUtils.getChannelResolver(beanFactory));
    }

    public void setThrowExceptionOnLateReply(boolean throwExceptionOnLateReply) {
        super.setThrowExceptionOnLateReply(throwExceptionOnLateReply);
        this.throwExceptionOnLateReplySet = true;
    }

    public void setDefaultChannel(MessageChannel channel) {
        super.setDefaultDestination((Object)channel);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Message<?> sendAndReceive(MessageChannel destination, Message<?> requestMessage) {
        if (!this.throwExceptionOnLateReplySet) {
            MessagingTemplate messagingTemplate = this;
            synchronized (messagingTemplate) {
                if (!this.throwExceptionOnLateReplySet) {
                    Properties integrationProperties = IntegrationContextUtils.getIntegrationProperties(this.beanFactory);
                    Boolean throwExceptionOnLateReply = Boolean.valueOf(integrationProperties.getProperty("spring.integration.messagingTemplate.throwExceptionOnLateReply"));
                    super.setThrowExceptionOnLateReply(throwExceptionOnLateReply.booleanValue());
                    this.throwExceptionOnLateReplySet = true;
                }
            }
        }
        return super.sendAndReceive((Object)destination, requestMessage);
    }

    public Object receiveAndConvert(MessageChannel destination, long timeout) {
        Message message = this.doReceive(destination, timeout);
        if (message != null) {
            return this.doConvert(message, Object.class);
        }
        return null;
    }

    public Message<?> receive(MessageChannel destination, long timeout) {
        return this.doReceive(destination, timeout);
    }
}

