/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Topic;

public interface TopicSubscriber
extends MessageConsumer {
    public Topic getTopic() throws JMSException;

    public boolean getNoLocal() throws JMSException;
}

