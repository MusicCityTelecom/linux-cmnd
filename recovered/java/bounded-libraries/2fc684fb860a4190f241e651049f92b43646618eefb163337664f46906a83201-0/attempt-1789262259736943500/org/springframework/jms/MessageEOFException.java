/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageEOFException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class MessageEOFException
extends JmsException {
    public MessageEOFException(javax.jms.MessageEOFException cause) {
        super((Throwable)cause);
    }
}

