/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.TransactionInProgressException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class TransactionInProgressException
extends JmsException {
    public TransactionInProgressException(javax.jms.TransactionInProgressException cause) {
        super((Throwable)cause);
    }
}

