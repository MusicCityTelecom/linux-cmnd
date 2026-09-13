/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 */
package org.springframework.jms.connection;

import javax.jms.JMSException;
import org.springframework.jms.JmsException;

public class SynchedLocalTransactionFailedException
extends JmsException {
    public SynchedLocalTransactionFailedException(String msg, JMSException cause) {
        super(msg, cause);
    }
}

