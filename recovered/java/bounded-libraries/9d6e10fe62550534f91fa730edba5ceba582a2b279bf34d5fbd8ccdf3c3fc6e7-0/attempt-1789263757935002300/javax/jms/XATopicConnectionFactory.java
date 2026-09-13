/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.TopicConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.jms.XATopicConnection;

public interface XATopicConnectionFactory
extends XAConnectionFactory,
TopicConnectionFactory {
    public XATopicConnection createXATopicConnection() throws JMSException;

    public XATopicConnection createXATopicConnection(String var1, String var2) throws JMSException;
}

