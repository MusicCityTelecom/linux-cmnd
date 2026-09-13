/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import javax.el.ELException;

public class PropertyNotFoundException
extends ELException {
    public PropertyNotFoundException() {
    }

    public PropertyNotFoundException(String message) {
        super(message);
    }

    public PropertyNotFoundException(Throwable exception) {
        super(exception);
    }

    public PropertyNotFoundException(String pMessage, Throwable pRootCause) {
        super(pMessage, pRootCause);
    }
}

