/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.Session
 */
package org.springframework.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@FunctionalInterface
public interface SessionAwareMessageListener<M extends Message> {
    public void onMessage(M var1, Session var2) throws JMSException;
}

