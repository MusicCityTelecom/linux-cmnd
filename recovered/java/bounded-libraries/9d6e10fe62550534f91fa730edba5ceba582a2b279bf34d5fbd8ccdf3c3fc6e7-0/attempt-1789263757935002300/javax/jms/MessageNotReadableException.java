/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class MessageNotReadableException
extends JMSException {
    public MessageNotReadableException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public MessageNotReadableException(String reason) {
        super(reason);
    }
}

