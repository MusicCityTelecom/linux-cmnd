/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSubscriber;

public interface TopicSession
extends Session {
    public Topic createTopic(String var1) throws JMSException;

    public TopicSubscriber createSubscriber(Topic var1) throws JMSException;

    public TopicSubscriber createSubscriber(Topic var1, String var2, boolean var3) throws JMSException;

    public TopicSubscriber createDurableSubscriber(Topic var1, String var2) throws JMSException;

    public TopicSubscriber createDurableSubscriber(Topic var1, String var2, String var3, boolean var4) throws JMSException;

    public TopicPublisher createPublisher(Topic var1) throws JMSException;

    public TemporaryTopic createTemporaryTopic() throws JMSException;

    public void unsubscribe(String var1) throws JMSException;
}

