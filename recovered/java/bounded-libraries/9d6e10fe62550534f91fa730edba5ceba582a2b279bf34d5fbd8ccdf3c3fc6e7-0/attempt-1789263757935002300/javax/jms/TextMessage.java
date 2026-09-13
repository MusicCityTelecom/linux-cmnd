/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;

public interface TextMessage
extends Message {
    public void setText(String var1) throws JMSException;

    public String getText() throws JMSException;
}

