/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.TransactionRolledBackException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class TransactionRolledBackException
extends JmsException {
    public TransactionRolledBackException(javax.jms.TransactionRolledBackException cause) {
        super((Throwable)cause);
    }
}

