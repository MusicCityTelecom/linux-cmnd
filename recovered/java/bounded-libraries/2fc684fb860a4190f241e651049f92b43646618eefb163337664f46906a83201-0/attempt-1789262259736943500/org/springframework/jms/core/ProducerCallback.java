/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.MessageProducer
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface ProducerCallback<T> {
    @Nullable
    public T doInJms(Session var1, MessageProducer var2) throws JMSException;
}

