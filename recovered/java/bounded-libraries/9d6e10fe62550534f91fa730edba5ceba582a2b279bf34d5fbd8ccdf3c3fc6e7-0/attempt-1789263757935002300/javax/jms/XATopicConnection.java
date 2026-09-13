/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.XAConnection;
import javax.jms.XATopicSession;

public interface XATopicConnection
extends XAConnection,
TopicConnection {
    public XATopicSession createXATopicSession() throws JMSException;

    public TopicSession createTopicSession(boolean var1, int var2) throws JMSException;
}

