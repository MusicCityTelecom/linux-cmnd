/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

public class DeprecationException
extends RuntimeException {
    private static final long serialVersionUID = 8828016729085737697L;

    public DeprecationException(String message) {
        super(message);
    }

    public DeprecationException(String message, Throwable cause) {
        super(message, cause);
    }
}

