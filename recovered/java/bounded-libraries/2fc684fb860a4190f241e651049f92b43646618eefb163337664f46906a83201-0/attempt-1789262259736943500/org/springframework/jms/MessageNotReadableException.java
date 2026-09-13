/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageNotReadableException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class MessageNotReadableException
extends JmsException {
    public MessageNotReadableException(javax.jms.MessageNotReadableException cause) {
        super((Throwable)cause);
    }
}

