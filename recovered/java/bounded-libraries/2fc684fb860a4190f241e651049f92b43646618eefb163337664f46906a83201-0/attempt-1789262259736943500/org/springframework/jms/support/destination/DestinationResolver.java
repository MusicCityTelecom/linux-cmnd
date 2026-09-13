/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Session
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.support.destination;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface DestinationResolver {
    public Destination resolveDestinationName(@Nullable Session var1, String var2, boolean var3) throws JMSException;
}

