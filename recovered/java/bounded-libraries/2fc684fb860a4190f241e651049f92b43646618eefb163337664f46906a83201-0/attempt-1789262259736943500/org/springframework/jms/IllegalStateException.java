/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.IllegalStateException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class IllegalStateException
extends JmsException {
    public IllegalStateException(javax.jms.IllegalStateException cause) {
        super((Throwable)cause);
    }
}

