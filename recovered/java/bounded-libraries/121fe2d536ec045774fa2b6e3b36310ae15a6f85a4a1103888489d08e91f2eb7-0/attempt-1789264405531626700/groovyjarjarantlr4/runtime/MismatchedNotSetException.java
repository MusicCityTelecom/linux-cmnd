/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.MismatchedSetException;

public class MismatchedNotSetException
extends MismatchedSetException {
    public MismatchedNotSetException() {
    }

    public MismatchedNotSetException(BitSet expecting, IntStream input) {
        super(expecting, input);
    }

    public String toString() {
        return "MismatchedNotSetException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
    }
}

