/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Connection;
import javax.jms.JMSException;

public interface ConnectionFactory {
    public Connection createConnection() throws JMSException;

    public Connection createConnection(String var1, String var2) throws JMSException;
}

