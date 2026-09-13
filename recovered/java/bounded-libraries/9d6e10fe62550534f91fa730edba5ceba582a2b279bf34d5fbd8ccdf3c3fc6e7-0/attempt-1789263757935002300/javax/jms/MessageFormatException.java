/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class MessageFormatException
extends JMSException {
    public MessageFormatException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public MessageFormatException(String reason) {
        super(reason);
    }
}

