/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TopicConnection;

public interface TopicConnectionFactory
extends ConnectionFactory {
    public TopicConnection createTopicConnection() throws JMSException;

    public TopicConnection createTopicConnection(String var1, String var2) throws JMSException;
}

