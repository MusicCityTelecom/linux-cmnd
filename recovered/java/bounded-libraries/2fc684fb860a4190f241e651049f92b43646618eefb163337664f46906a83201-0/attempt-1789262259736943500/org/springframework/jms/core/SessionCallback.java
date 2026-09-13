/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface SessionCallback<T> {
    @Nullable
    public T doInJms(Session var1) throws JMSException;
}

