/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;

public interface QueueReceiver
extends MessageConsumer {
    public Queue getQueue() throws JMSException;
}

