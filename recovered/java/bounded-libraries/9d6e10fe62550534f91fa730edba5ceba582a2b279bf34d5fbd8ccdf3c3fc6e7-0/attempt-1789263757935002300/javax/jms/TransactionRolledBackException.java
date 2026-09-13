/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class TransactionRolledBackException
extends JMSException {
    public TransactionRolledBackException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public TransactionRolledBackException(String reason) {
        super(reason);
    }
}

