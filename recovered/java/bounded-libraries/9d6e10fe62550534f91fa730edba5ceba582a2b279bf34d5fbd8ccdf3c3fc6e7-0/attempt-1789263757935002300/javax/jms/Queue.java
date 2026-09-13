/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Destination;
import javax.jms.JMSException;

public interface Queue
extends Destination {
    public String getQueueName() throws JMSException;

    public String toString();
}

