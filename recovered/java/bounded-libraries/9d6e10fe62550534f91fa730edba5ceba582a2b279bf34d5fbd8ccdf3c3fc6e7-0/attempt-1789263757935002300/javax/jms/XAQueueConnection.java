/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.XAConnection;
import javax.jms.XAQueueSession;

public interface XAQueueConnection
extends XAConnection,
QueueConnection {
    public XAQueueSession createXAQueueSession() throws JMSException;

    public QueueSession createQueueSession(boolean var1, int var2) throws JMSException;
}

