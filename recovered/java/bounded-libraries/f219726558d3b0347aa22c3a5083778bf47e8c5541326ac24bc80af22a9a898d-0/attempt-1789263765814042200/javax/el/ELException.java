/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

public class ELException
extends RuntimeException {
    public ELException() {
    }

    public ELException(String pMessage) {
        super(pMessage);
    }

    public ELException(Throwable pRootCause) {
        super(pRootCause);
    }

    public ELException(String pMessage, Throwable pRootCause) {
        super(pMessage, pRootCause);
    }
}

