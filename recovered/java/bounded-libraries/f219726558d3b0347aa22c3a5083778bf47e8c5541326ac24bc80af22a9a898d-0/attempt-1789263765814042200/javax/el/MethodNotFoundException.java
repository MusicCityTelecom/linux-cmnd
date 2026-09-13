/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import javax.el.ELException;

public class MethodNotFoundException
extends ELException {
    public MethodNotFoundException() {
    }

    public MethodNotFoundException(String message) {
        super(message);
    }

    public MethodNotFoundException(Throwable exception) {
        super(exception);
    }

    public MethodNotFoundException(String pMessage, Throwable pRootCause) {
        super(pMessage, pRootCause);
    }
}

