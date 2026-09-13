/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public interface Session
extends Runnable {
    public static final int AUTO_ACKNOWLEDGE = 1;
    public static final int CLIENT_ACKNOWLEDGE = 2;
    public static final int DUPS_OK_ACKNOWLEDGE = 3;
    public static final int SESSION_TRANSACTED = 0;

    public BytesMessage createBytesMessage() throws JMSException;

    public MapMessage createMapMessage() throws JMSException;

    public Message createMessage() throws JMSException;

    public ObjectMessage createObjectMessage() throws JMSException;

    public ObjectMessage createObjectMessage(Serializable var1) throws JMSException;

    public StreamMessage createStreamMessage() throws JMSException;

    public TextMessage createTextMessage() throws JMSException;

    public TextMessage createTextMessage(String var1) throws JMSException;

    public boolean getTransacted() throws JMSException;

    public int getAcknowledgeMode() throws JMSException;

    public void commit() throws JMSException;

    public void rollback() throws JMSException;

    public void close() throws JMSException;

    public void recover() throws JMSException;

    public MessageListener getMessageListener() throws JMSException;

    public void setMessageListener(MessageListener var1) throws JMSException;

    public void run();

    public MessageProducer createProducer(Destination var1) throws JMSException;

    public MessageConsumer createConsumer(Destination var1) throws JMSException;

    public MessageConsumer createConsumer(Destination var1, String var2) throws JMSException;

    public MessageConsumer createConsumer(Destination var1, String var2, boolean var3) throws JMSException;

    public Queue createQueue(String var1) throws JMSException;

    public Topic createTopic(String var1) throws JMSException;

    public TopicSubscriber createDurableSubscriber(Topic var1, String var2) throws JMSException;

    public TopicSubscriber createDurableSubscriber(Topic var1, String var2, String var3, boolean var4) throws JMSException;

    public QueueBrowser createBrowser(Queue var1) throws JMSException;

    public QueueBrowser createBrowser(Queue var1, String var2) throws JMSException;

    public TemporaryQueue createTemporaryQueue() throws JMSException;

    public TemporaryTopic createTemporaryTopic() throws JMSException;

    public void unsubscribe(String var1) throws JMSException;
}

