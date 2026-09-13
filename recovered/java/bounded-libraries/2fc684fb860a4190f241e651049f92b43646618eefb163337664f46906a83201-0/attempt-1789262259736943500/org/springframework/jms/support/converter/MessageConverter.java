/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 */
package org.springframework.jms.support.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;

public interface MessageConverter {
    public Message toMessage(Object var1, Session var2) throws JMSException, MessageConversionException;

    public Object fromMessage(Message var1) throws JMSException, MessageConversionException;
}

