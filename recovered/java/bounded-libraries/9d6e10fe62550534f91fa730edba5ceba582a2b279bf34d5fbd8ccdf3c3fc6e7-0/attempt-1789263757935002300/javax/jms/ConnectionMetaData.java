/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import java.util.Enumeration;
import javax.jms.JMSException;

public interface ConnectionMetaData {
    public String getJMSVersion() throws JMSException;

    public int getJMSMajorVersion() throws JMSException;

    public int getJMSMinorVersion() throws JMSException;

    public String getJMSProviderName() throws JMSException;

    public String getProviderVersion() throws JMSException;

    public int getProviderMajorVersion() throws JMSException;

    public int getProviderMinorVersion() throws JMSException;

    public Enumeration getJMSXPropertyNames() throws JMSException;
}

