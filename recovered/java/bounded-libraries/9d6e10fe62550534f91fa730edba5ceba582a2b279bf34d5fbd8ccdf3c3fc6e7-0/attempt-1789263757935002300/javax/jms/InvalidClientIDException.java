/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class InvalidClientIDException
extends JMSException {
    public InvalidClientIDException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public InvalidClientIDException(String reason) {
        super(reason);
    }
}

