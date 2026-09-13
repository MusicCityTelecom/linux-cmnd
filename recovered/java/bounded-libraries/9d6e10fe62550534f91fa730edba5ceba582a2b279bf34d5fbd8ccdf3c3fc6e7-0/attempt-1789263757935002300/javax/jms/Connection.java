/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

public interface Connection {
    public Session createSession(boolean var1, int var2) throws JMSException;

    public String getClientID() throws JMSException;

    public void setClientID(String var1) throws JMSException;

    public ConnectionMetaData getMetaData() throws JMSException;

    public ExceptionListener getExceptionListener() throws JMSException;

    public void setExceptionListener(ExceptionListener var1) throws JMSException;

    public void start() throws JMSException;

    public void stop() throws JMSException;

    public void close() throws JMSException;

    public ConnectionConsumer createConnectionConsumer(Destination var1, String var2, ServerSessionPool var3, int var4) throws JMSException;

    public ConnectionConsumer createDurableConnectionConsumer(Topic var1, String var2, String var3, ServerSessionPool var4, int var5) throws JMSException;
}

