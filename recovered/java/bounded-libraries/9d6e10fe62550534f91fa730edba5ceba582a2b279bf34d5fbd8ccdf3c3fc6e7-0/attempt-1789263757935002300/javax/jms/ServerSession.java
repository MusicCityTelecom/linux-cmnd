/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Session;

public interface ServerSession {
    public Session getSession() throws JMSException;

    public void start() throws JMSException;
}

