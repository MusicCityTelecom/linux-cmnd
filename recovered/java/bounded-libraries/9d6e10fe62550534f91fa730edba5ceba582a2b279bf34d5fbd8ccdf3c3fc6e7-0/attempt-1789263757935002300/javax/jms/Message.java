/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import java.util.Enumeration;
import javax.jms.Destination;
import javax.jms.JMSException;

public interface Message {
    public static final int DEFAULT_DELIVERY_MODE = 2;
    public static final int DEFAULT_PRIORITY = 4;
    public static final long DEFAULT_TIME_TO_LIVE = 0L;

    public String getJMSMessageID() throws JMSException;

    public void setJMSMessageID(String var1) throws JMSException;

    public long getJMSTimestamp() throws JMSException;

    public void setJMSTimestamp(long var1) throws JMSException;

    public byte[] getJMSCorrelationIDAsBytes() throws JMSException;

    public void setJMSCorrelationIDAsBytes(byte[] var1) throws JMSException;

    public void setJMSCorrelationID(String var1) throws JMSException;

    public String getJMSCorrelationID() throws JMSException;

    public Destination getJMSReplyTo() throws JMSException;

    public void setJMSReplyTo(Destination var1) throws JMSException;

    public Destination getJMSDestination() throws JMSException;

    public void setJMSDestination(Destination var1) throws JMSException;

    public int getJMSDeliveryMode() throws JMSException;

    public void setJMSDeliveryMode(int var1) throws JMSException;

    public boolean getJMSRedelivered() throws JMSException;

    public void setJMSRedelivered(boolean var1) throws JMSException;

    public String getJMSType() throws JMSException;

    public void setJMSType(String var1) throws JMSException;

    public long getJMSExpiration() throws JMSException;

    public void setJMSExpiration(long var1) throws JMSException;

    public int getJMSPriority() throws JMSException;

    public void setJMSPriority(int var1) throws JMSException;

    public void clearProperties() throws JMSException;

    public boolean propertyExists(String var1) throws JMSException;

    public boolean getBooleanProperty(String var1) throws JMSException;

    public byte getByteProperty(String var1) throws JMSException;

    public short getShortProperty(String var1) throws JMSException;

    public int getIntProperty(String var1) throws JMSException;

    public long getLongProperty(String var1) throws JMSException;

    public float getFloatProperty(String var1) throws JMSException;

    public double getDoubleProperty(String var1) throws JMSException;

    public String getStringProperty(String var1) throws JMSException;

    public Object getObjectProperty(String var1) throws JMSException;

    public Enumeration getPropertyNames() throws JMSException;

    public void setBooleanProperty(String var1, boolean var2) throws JMSException;

    public void setByteProperty(String var1, byte var2) throws JMSException;

    public void setShortProperty(String var1, short var2) throws JMSException;

    public void setIntProperty(String var1, int var2) throws JMSException;

    public void setLongProperty(String var1, long var2) throws JMSException;

    public void setFloatProperty(String var1, float var2) throws JMSException;

    public void setDoubleProperty(String var1, double var2) throws JMSException;

    public void setStringProperty(String var1, String var2) throws JMSException;

    public void setObjectProperty(String var1, Object var2) throws JMSException;

    public void acknowledge() throws JMSException;

    public void clearBody() throws JMSException;
}

