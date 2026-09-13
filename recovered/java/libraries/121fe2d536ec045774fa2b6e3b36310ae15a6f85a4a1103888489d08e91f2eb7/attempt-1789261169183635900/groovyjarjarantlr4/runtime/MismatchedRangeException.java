/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class MismatchedRangeException
extends RecognitionException {
    public int a;
    public int b;

    public MismatchedRangeException() {
    }

    public MismatchedRangeException(int a, int b, IntStream input) {
        super(input);
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "MismatchedNotSetException(" + this.getUnexpectedType() + " not in [" + this.a + "," + this.b + "])";
    }
}

