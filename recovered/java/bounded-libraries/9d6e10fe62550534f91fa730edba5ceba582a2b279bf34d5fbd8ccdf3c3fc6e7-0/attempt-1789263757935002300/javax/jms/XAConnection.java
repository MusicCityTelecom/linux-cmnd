/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.XASession;

public interface XAConnection
extends Connection {
    public XASession createXASession() throws JMSException;

    public Session createSession(boolean var1, int var2) throws JMSException;
}

