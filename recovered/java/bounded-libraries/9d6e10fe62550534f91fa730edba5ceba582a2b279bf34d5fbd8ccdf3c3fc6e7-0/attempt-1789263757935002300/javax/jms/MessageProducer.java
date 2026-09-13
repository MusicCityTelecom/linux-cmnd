/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public interface MessageProducer {
    public void setDisableMessageID(boolean var1) throws JMSException;

    public boolean getDisableMessageID() throws JMSException;

    public void setDisableMessageTimestamp(boolean var1) throws JMSException;

    public boolean getDisableMessageTimestamp() throws JMSException;

    public void setDeliveryMode(int var1) throws JMSException;

    public int getDeliveryMode() throws JMSException;

    public void setPriority(int var1) throws JMSException;

    public int getPriority() throws JMSException;

    public void setTimeToLive(long var1) throws JMSException;

    public long getTimeToLive() throws JMSException;

    public Destination getDestination() throws JMSException;

    public void close() throws JMSException;

    public void send(Message var1) throws JMSException;

    public void send(Message var1, int var2, int var3, long var4) throws JMSException;

    public void send(Destination var1, Message var2) throws JMSException;

    public void send(Destination var1, Message var2, int var3, int var4, long var5) throws JMSException;
}

