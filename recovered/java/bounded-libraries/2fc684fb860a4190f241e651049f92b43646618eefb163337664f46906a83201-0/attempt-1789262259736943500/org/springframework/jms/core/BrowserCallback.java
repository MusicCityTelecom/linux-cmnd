/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.QueueBrowser
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.core;

import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface BrowserCallback<T> {
    @Nullable
    public T doInJms(Session var1, QueueBrowser var2) throws JMSException;
}

