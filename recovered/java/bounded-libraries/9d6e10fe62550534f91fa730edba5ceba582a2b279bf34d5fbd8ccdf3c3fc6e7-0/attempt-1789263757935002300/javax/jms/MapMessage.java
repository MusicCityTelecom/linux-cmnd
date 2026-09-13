/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import java.util.Enumeration;
import javax.jms.JMSException;
import javax.jms.Message;

public interface MapMessage
extends Message {
    public boolean getBoolean(String var1) throws JMSException;

    public byte getByte(String var1) throws JMSException;

    public short getShort(String var1) throws JMSException;

    public char getChar(String var1) throws JMSException;

    public int getInt(String var1) throws JMSException;

    public long getLong(String var1) throws JMSException;

    public float getFloat(String var1) throws JMSException;

    public double getDouble(String var1) throws JMSException;

    public String getString(String var1) throws JMSException;

    public byte[] getBytes(String var1) throws JMSException;

    public Object getObject(String var1) throws JMSException;

    public Enumeration getMapNames() throws JMSException;

    public void setBoolean(String var1, boolean var2) throws JMSException;

    public void setByte(String var1, byte var2) throws JMSException;

    public void setShort(String var1, short var2) throws JMSException;

    public void setChar(String var1, char var2) throws JMSException;

    public void setInt(String var1, int var2) throws JMSException;

    public void setLong(String var1, long var2) throws JMSException;

    public void setFloat(String var1, float var2) throws JMSException;

    public void setDouble(String var1, double var2) throws JMSException;

    public void setString(String var1, String var2) throws JMSException;

    public void setBytes(String var1, byte[] var2) throws JMSException;

    public void setBytes(String var1, byte[] var2, int var3, int var4) throws JMSException;

    public void setObject(String var1, Object var2) throws JMSException;

    public boolean itemExists(String var1) throws JMSException;
}

