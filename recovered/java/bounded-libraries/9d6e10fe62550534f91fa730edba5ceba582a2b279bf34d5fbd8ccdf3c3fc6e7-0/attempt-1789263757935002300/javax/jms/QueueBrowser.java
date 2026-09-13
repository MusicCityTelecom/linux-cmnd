/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import java.util.Enumeration;
import javax.jms.JMSException;
import javax.jms.Queue;

public interface QueueBrowser {
    public Queue getQueue() throws JMSException;

    public String getMessageSelector() throws JMSException;

    public Enumeration getEnumeration() throws JMSException;

    public void close() throws JMSException;
}

