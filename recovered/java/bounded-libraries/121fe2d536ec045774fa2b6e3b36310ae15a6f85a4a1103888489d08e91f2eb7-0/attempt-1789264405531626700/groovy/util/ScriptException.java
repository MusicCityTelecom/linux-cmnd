/*
 * Decompiled with CFR 0.152.
 */
package groovy.util;

public class ScriptException
extends Exception {
    private static final long serialVersionUID = 3447547760007143671L;

    public ScriptException() {
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }
}

