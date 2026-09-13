/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageFormatException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class MessageFormatException
extends JmsException {
    public MessageFormatException(javax.jms.MessageFormatException cause) {
        super((Throwable)cause);
    }
}

