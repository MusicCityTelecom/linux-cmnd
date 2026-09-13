/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.InvalidDestinationException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class InvalidDestinationException
extends JmsException {
    public InvalidDestinationException(javax.jms.InvalidDestinationException cause) {
        super((Throwable)cause);
    }
}

