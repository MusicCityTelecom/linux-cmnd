/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;

public interface XAQueueConnectionFactory
extends XAConnectionFactory,
QueueConnectionFactory {
    public XAQueueConnection createXAQueueConnection() throws JMSException;

    public XAQueueConnection createXAQueueConnection(String var1, String var2) throws JMSException;
}

