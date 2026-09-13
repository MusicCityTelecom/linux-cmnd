/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class MessageNotWriteableException
extends JMSException {
    public MessageNotWriteableException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public MessageNotWriteableException(String reason) {
        super(reason);
    }
}

