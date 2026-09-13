/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import java.util.concurrent.CancellationException;

public class ParseCancellationException
extends CancellationException {
    private static final long serialVersionUID = -3529552099366979683L;

    public ParseCancellationException() {
    }

    public ParseCancellationException(String message) {
        super(message);
    }

    public ParseCancellationException(Throwable cause) {
        this.initCause(cause);
    }

    public ParseCancellationException(String message, Throwable cause) {
        super(message);
        this.initCause(cause);
    }
}

