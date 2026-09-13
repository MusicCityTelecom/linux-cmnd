/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Topic;
import javax.jms.TopicSession;

public interface TopicConnection
extends Connection {
    public TopicSession createTopicSession(boolean var1, int var2) throws JMSException;

    public ConnectionConsumer createConnectionConsumer(Topic var1, String var2, ServerSessionPool var3, int var4) throws JMSException;

    public ConnectionConsumer createDurableConnectionConsumer(Topic var1, String var2, String var3, ServerSessionPool var4, int var5) throws JMSException;
}

