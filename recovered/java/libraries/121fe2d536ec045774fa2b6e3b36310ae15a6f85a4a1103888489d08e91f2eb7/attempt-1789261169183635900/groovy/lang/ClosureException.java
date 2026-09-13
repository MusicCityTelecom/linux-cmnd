/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Closure;

public class ClosureException
extends RuntimeException {
    private static final long serialVersionUID = -945565055750688719L;
    private final Closure closure;

    public ClosureException(Closure closure, Throwable cause) {
        super("Exception thrown by call to closure: " + closure + " reason: " + cause, cause);
        this.closure = closure;
    }

    public Closure getClosure() {
        return this.closure;
    }
}

