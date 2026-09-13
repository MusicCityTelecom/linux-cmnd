/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class InvalidDestinationException
extends JMSException {
    public InvalidDestinationException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public InvalidDestinationException(String reason) {
        super(reason);
    }
}

