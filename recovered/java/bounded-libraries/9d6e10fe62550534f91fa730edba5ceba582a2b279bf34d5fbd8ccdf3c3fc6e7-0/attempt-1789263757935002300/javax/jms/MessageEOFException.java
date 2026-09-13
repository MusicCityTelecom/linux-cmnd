/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class MessageEOFException
extends JMSException {
    public MessageEOFException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public MessageEOFException(String reason) {
        super(reason);
    }
}

