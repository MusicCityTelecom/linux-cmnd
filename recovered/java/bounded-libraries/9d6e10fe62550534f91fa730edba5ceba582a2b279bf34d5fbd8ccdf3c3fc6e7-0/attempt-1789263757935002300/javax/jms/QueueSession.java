/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

public interface QueueSession
extends Session {
    public Queue createQueue(String var1) throws JMSException;

    public QueueReceiver createReceiver(Queue var1) throws JMSException;

    public QueueReceiver createReceiver(Queue var1, String var2) throws JMSException;

    public QueueSender createSender(Queue var1) throws JMSException;

    public QueueBrowser createBrowser(Queue var1) throws JMSException;

    public QueueBrowser createBrowser(Queue var1, String var2) throws JMSException;

    public TemporaryQueue createTemporaryQueue() throws JMSException;
}

