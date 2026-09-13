/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class InvalidSelectorException
extends JMSException {
    public InvalidSelectorException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public InvalidSelectorException(String reason) {
        super(reason);
    }
}

