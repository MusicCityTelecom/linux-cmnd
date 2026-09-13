/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.ServerSessionPool;

public interface ConnectionConsumer {
    public ServerSessionPool getServerSessionPool() throws JMSException;

    public void close() throws JMSException;
}

