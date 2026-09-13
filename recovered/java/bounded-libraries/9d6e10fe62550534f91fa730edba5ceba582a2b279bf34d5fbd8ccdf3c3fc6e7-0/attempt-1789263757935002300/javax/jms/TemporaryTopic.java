/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Topic;

public interface TemporaryTopic
extends Topic {
    public void delete() throws JMSException;
}

