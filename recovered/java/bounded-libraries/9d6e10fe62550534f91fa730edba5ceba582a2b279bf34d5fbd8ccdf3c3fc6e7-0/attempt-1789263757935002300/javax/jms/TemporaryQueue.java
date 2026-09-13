/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Queue;

public interface TemporaryQueue
extends Queue {
    public void delete() throws JMSException;
}

