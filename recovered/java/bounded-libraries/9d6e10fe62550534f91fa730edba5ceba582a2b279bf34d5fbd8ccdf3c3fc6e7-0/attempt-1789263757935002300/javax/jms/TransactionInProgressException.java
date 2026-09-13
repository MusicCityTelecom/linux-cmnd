/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class TransactionInProgressException
extends JMSException {
    public TransactionInProgressException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public TransactionInProgressException(String reason) {
        super(reason);
    }
}

