/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;

public interface ObjectMessage
extends Message {
    public void setObject(Serializable var1) throws JMSException;

    public Serializable getObject() throws JMSException;
}

