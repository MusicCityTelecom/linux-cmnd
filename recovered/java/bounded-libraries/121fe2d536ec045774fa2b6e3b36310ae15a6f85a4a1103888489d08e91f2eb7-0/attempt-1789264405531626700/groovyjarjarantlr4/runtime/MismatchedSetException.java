/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class MismatchedSetException
extends RecognitionException {
    public BitSet expecting;

    public MismatchedSetException() {
    }

    public MismatchedSetException(BitSet expecting, IntStream input) {
        super(input);
        this.expecting = expecting;
    }

    public String toString() {
        return "MismatchedSetException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
    }
}

