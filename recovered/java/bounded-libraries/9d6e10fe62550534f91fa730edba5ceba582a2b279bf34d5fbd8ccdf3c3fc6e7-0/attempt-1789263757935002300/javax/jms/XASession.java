/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.transaction.xa.XAResource;

public interface XASession
extends Session {
    public Session getSession() throws JMSException;

    public XAResource getXAResource();

    public boolean getTransacted() throws JMSException;

    public void commit() throws JMSException;

    public void rollback() throws JMSException;
}

