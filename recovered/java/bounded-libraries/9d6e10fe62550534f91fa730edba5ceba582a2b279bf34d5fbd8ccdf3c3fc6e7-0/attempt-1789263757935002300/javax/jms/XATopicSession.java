/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.TopicSession;
import javax.jms.XASession;

public interface XATopicSession
extends XASession {
    public TopicSession getTopicSession() throws JMSException;
}

