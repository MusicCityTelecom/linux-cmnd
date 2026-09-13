/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.InvalidSelectorException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class InvalidSelectorException
extends JmsException {
    public InvalidSelectorException(javax.jms.InvalidSelectorException cause) {
        super((Throwable)cause);
    }
}

