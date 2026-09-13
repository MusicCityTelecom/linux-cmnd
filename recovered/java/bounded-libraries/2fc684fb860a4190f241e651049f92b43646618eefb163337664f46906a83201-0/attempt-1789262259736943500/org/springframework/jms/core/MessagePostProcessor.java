/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 */
package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.Message;

@FunctionalInterface
public interface MessagePostProcessor {
    public Message postProcessMessage(Message var1) throws JMSException;
}

