/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.support.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.Nullable;

public interface SmartMessageConverter
extends MessageConverter {
    public Message toMessage(Object var1, Session var2, @Nullable Object var3) throws JMSException, MessageConversionException;
}

