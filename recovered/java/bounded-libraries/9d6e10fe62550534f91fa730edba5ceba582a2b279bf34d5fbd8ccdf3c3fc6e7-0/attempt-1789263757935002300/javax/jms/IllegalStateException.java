/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class IllegalStateException
extends JMSException {
    public IllegalStateException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public IllegalStateException(String reason) {
        super(reason);
    }
}

