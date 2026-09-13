/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import javax.el.ELException;

public class PropertyNotWritableException
extends ELException {
    public PropertyNotWritableException() {
    }

    public PropertyNotWritableException(String pMessage) {
        super(pMessage);
    }

    public PropertyNotWritableException(Throwable exception) {
        super(exception);
    }

    public PropertyNotWritableException(String pMessage, Throwable pRootCause) {
        super(pMessage, pRootCause);
    }
}

