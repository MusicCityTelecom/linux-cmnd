/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;

public interface QueueSender
extends MessageProducer {
    public Queue getQueue() throws JMSException;

    public void send(Message var1) throws JMSException;

    public void send(Message var1, int var2, int var3, long var4) throws JMSException;

    public void send(Queue var1, Message var2) throws JMSException;

    public void send(Queue var1, Message var2, int var3, int var4, long var5) throws JMSException;
}

