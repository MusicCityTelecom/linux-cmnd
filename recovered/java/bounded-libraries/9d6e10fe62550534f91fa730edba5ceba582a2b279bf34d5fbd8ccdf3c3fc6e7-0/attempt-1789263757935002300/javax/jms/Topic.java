/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Destination;
import javax.jms.JMSException;

public interface Topic
extends Destination {
    public String getTopicName() throws JMSException;

    public String toString();
}

