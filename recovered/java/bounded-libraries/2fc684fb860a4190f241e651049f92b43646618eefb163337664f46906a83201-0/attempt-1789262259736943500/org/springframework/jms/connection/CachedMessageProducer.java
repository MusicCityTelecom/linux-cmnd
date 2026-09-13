/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.CompletionListener
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageProducer
 *  javax.jms.Queue
 *  javax.jms.QueueSender
 *  javax.jms.Topic
 *  javax.jms.TopicPublisher
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.connection;

import javax.jms.CompletionListener;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import org.springframework.lang.Nullable;

class CachedMessageProducer
implements MessageProducer,
QueueSender,
TopicPublisher {
    private final MessageProducer target;
    @Nullable
    private Boolean originalDisableMessageID;
    @Nullable
    private Boolean originalDisableMessageTimestamp;
    @Nullable
    private Long originalDeliveryDelay;
    private int deliveryMode;
    private int priority;
    private long timeToLive;

    public CachedMessageProducer(MessageProducer target) throws JMSException {
        this.target = target;
        this.deliveryMode = target.getDeliveryMode();
        this.priority = target.getPriority();
        this.timeToLive = target.getTimeToLive();
    }

    public void setDisableMessageID(boolean disableMessageID) throws JMSException {
        if (this.originalDisableMessageID == null) {
            this.originalDisableMessageID = this.target.getDisableMessageID();
        }
        this.target.setDisableMessageID(disableMessageID);
    }

    public boolean getDisableMessageID() throws JMSException {
        return this.target.getDisableMessageID();
    }

    public void setDisableMessageTimestamp(boolean disableMessageTimestamp) throws JMSException {
        if (this.originalDisableMessageTimestamp == null) {
            this.originalDisableMessageTimestamp = this.target.getDisableMessageTimestamp();
        }
        this.target.setDisableMessageTimestamp(disableMessageTimestamp);
    }

    public boolean getDisableMessageTimestamp() throws JMSException {
        return this.target.getDisableMessageTimestamp();
    }

    public void setDeliveryDelay(long deliveryDelay) throws JMSException {
        if (this.originalDeliveryDelay == null) {
            this.originalDeliveryDelay = this.target.getDeliveryDelay();
        }
        this.target.setDeliveryDelay(deliveryDelay);
    }

    public long getDeliveryDelay() throws JMSException {
        return this.target.getDeliveryDelay();
    }

    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public int getDeliveryMode() {
        return this.deliveryMode;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getTimeToLive() {
        return this.timeToLive;
    }

    public Destination getDestination() throws JMSException {
        return this.target.getDestination();
    }

    public Queue getQueue() throws JMSException {
        return (Queue)this.target.getDestination();
    }

    public Topic getTopic() throws JMSException {
        return (Topic)this.target.getDestination();
    }

    public void send(Message message) throws JMSException {
        this.target.send(message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.target.send(message, deliveryMode, priority, timeToLive);
    }

    public void send(Destination destination, Message message) throws JMSException {
        this.target.send(destination, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.target.send(destination, message, deliveryMode, priority, timeToLive);
    }

    public void send(Message message, CompletionListener completionListener) throws JMSException {
        this.target.send(message, this.deliveryMode, this.priority, this.timeToLive, completionListener);
    }

    public void send(Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
        this.target.send(message, deliveryMode, priority, timeToLive, completionListener);
    }

    public void send(Destination destination, Message message, CompletionListener completionListener) throws JMSException {
        this.target.send(destination, message, this.deliveryMode, this.priority, this.timeToLive, completionListener);
    }

    public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
        this.target.send(destination, message, deliveryMode, priority, timeToLive, completionListener);
    }

    public void send(Queue queue, Message message) throws JMSException {
        this.target.send((Destination)queue, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.target.send((Destination)queue, message, deliveryMode, priority, timeToLive);
    }

    public void publish(Message message) throws JMSException {
        this.target.send(message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.target.send(message, deliveryMode, priority, timeToLive);
    }

    public void publish(Topic topic, Message message) throws JMSException {
        this.target.send((Destination)topic, message, this.deliveryMode, this.priority, this.timeToLive);
    }

    public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.target.send((Destination)topic, message, deliveryMode, priority, timeToLive);
    }

    public void close() throws JMSException {
        if (this.originalDisableMessageID != null) {
            this.target.setDisableMessageID(this.originalDisableMessageID.booleanValue());
            this.originalDisableMessageID = null;
        }
        if (this.originalDisableMessageTimestamp != null) {
            this.target.setDisableMessageTimestamp(this.originalDisableMessageTimestamp.booleanValue());
            this.originalDisableMessageTimestamp = null;
        }
        if (this.originalDeliveryDelay != null) {
            this.target.setDeliveryDelay(this.originalDeliveryDelay.longValue());
            this.originalDeliveryDelay = null;
        }
    }

    public String toString() {
        return "Cached JMS MessageProducer: " + this.target;
    }
}

