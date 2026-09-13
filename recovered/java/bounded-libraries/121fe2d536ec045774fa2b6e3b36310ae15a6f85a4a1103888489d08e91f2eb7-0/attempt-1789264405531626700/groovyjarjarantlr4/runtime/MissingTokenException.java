/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.MismatchedTokenException;

public class MissingTokenException
extends MismatchedTokenException {
    public Object inserted;

    public MissingTokenException() {
    }

    public MissingTokenException(int expecting, IntStream input, Object inserted) {
        super(expecting, input);
        this.inserted = inserted;
    }

    public int getMissingType() {
        return this.expecting;
    }

    public String toString() {
        if (this.inserted != null && this.token != null) {
            return "MissingTokenException(inserted " + this.inserted + " at " + this.token.getText() + ")";
        }
        if (this.token != null) {
            return "MissingTokenException(at " + this.token.getText() + ")";
        }
        return "MissingTokenException";
    }
}

