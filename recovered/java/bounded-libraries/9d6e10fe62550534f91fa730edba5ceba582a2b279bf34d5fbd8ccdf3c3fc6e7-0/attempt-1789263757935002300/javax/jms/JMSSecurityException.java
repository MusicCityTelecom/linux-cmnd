/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class JMSSecurityException
extends JMSException {
    public JMSSecurityException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public JMSSecurityException(String reason) {
        super(reason);
    }
}

