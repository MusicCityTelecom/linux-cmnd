/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;

public class ResourceAllocationException
extends JMSException {
    public ResourceAllocationException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public ResourceAllocationException(String reason) {
        super(reason);
    }
}

