/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageListener
 *  javax.jms.Queue
 *  javax.jms.QueueReceiver
 *  javax.jms.Topic
 *  javax.jms.TopicSubscriber
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.connection;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import org.springframework.lang.Nullable;

class CachedMessageConsumer
implements MessageConsumer,
QueueReceiver,
TopicSubscriber {
    protected final MessageConsumer target;

    public CachedMessageConsumer(MessageConsumer target) {
        this.target = target;
    }

    public String getMessageSelector() throws JMSException {
        return this.target.getMessageSelector();
    }

    @Nullable
    public Queue getQueue() throws JMSException {
        return this.target instanceof QueueReceiver ? ((QueueReceiver)this.target).getQueue() : null;
    }

    @Nullable
    public Topic getTopic() throws JMSException {
        return this.target instanceof TopicSubscriber ? ((TopicSubscriber)this.target).getTopic() : null;
    }

    public boolean getNoLocal() throws JMSException {
        return this.target instanceof TopicSubscriber && ((TopicSubscriber)this.target).getNoLocal();
    }

    public MessageListener getMessageListener() throws JMSException {
        return this.target.getMessageListener();
    }

    public void setMessageListener(MessageListener messageListener) throws JMSException {
        this.target.setMessageListener(messageListener);
    }

    public Message receive() throws JMSException {
        return this.target.receive();
    }

    public Message receive(long timeout) throws JMSException {
        return this.target.receive(timeout);
    }

    public Message receiveNoWait() throws JMSException {
        return this.target.receiveNoWait();
    }

    public void close() throws JMSException {
    }

    public String toString() {
        return "Cached JMS MessageConsumer: " + this.target;
    }
}

