/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;

public interface QueueConnectionFactory
extends ConnectionFactory {
    public QueueConnection createQueueConnection() throws JMSException;

    public QueueConnection createQueueConnection(String var1, String var2) throws JMSException;
}

