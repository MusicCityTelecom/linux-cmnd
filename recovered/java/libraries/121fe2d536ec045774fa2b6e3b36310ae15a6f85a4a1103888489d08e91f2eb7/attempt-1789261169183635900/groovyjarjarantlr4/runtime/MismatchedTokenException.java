/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class MismatchedTokenException
extends RecognitionException {
    public int expecting = 0;

    public MismatchedTokenException() {
    }

    public MismatchedTokenException(int expecting, IntStream input) {
        super(input);
        this.expecting = expecting;
    }

    public String toString() {
        return "MismatchedTokenException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
    }
}

