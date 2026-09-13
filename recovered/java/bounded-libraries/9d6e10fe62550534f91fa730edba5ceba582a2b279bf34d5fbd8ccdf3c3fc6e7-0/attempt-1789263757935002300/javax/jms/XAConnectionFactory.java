/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.XAConnection;

public interface XAConnectionFactory {
    public XAConnection createXAConnection() throws JMSException;

    public XAConnection createXAConnection(String var1, String var2) throws JMSException;
}

