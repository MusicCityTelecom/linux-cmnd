/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;

public interface QueueConnection
extends Connection {
    public QueueSession createQueueSession(boolean var1, int var2) throws JMSException;

    public ConnectionConsumer createConnectionConsumer(Queue var1, String var2, ServerSessionPool var3, int var4) throws JMSException;
}

