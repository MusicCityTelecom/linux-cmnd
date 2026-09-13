/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.MessageNotWriteableException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class MessageNotWriteableException
extends JmsException {
    public MessageNotWriteableException(javax.jms.MessageNotWriteableException cause) {
        super((Throwable)cause);
    }
}

