/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.InvalidClientIDException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class InvalidClientIDException
extends JmsException {
    public InvalidClientIDException(javax.jms.InvalidClientIDException cause) {
        super((Throwable)cause);
    }
}

