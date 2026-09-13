/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public interface MessageConsumer {
    public String getMessageSelector() throws JMSException;

    public MessageListener getMessageListener() throws JMSException;

    public void setMessageListener(MessageListener var1) throws JMSException;

    public Message receive() throws JMSException;

    public Message receive(long var1) throws JMSException;

    public Message receiveNoWait() throws JMSException;

    public void close() throws JMSException;
}

