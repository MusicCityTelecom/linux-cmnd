/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;

public interface StreamMessage
extends Message {
    public boolean readBoolean() throws JMSException;

    public byte readByte() throws JMSException;

    public short readShort() throws JMSException;

    public char readChar() throws JMSException;

    public int readInt() throws JMSException;

    public long readLong() throws JMSException;

    public float readFloat() throws JMSException;

    public double readDouble() throws JMSException;

    public String readString() throws JMSException;

    public int readBytes(byte[] var1) throws JMSException;

    public Object readObject() throws JMSException;

    public void writeBoolean(boolean var1) throws JMSException;

    public void writeByte(byte var1) throws JMSException;

    public void writeShort(short var1) throws JMSException;

    public void writeChar(char var1) throws JMSException;

    public void writeInt(int var1) throws JMSException;

    public void writeLong(long var1) throws JMSException;

    public void writeFloat(float var1) throws JMSException;

    public void writeDouble(double var1) throws JMSException;

    public void writeString(String var1) throws JMSException;

    public void writeBytes(byte[] var1) throws JMSException;

    public void writeBytes(byte[] var1, int var2, int var3) throws JMSException;

    public void writeObject(Object var1) throws JMSException;

    public void reset() throws JMSException;
}

