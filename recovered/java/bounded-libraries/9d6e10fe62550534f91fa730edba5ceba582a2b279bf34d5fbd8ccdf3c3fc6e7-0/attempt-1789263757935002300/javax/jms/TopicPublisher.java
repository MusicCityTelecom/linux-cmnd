/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Topic;

public interface TopicPublisher
extends MessageProducer {
    public Topic getTopic() throws JMSException;

    public void publish(Message var1) throws JMSException;

    public void publish(Message var1, int var2, int var3, long var4) throws JMSException;

    public void publish(Topic var1, Message var2) throws JMSException;

    public void publish(Topic var1, Message var2, int var3, int var4, long var5) throws JMSException;
}

