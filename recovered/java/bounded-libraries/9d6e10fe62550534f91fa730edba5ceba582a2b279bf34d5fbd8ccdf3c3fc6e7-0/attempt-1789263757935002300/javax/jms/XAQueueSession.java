/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.XASession;

public interface XAQueueSession
extends XASession {
    public QueueSession getQueueSession() throws JMSException;
}

