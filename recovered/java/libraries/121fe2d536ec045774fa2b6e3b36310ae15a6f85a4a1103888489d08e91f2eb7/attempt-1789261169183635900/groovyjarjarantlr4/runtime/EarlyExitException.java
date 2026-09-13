/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class EarlyExitException
extends RecognitionException {
    public int decisionNumber;

    public EarlyExitException() {
    }

    public EarlyExitException(int decisionNumber, IntStream input) {
        super(input);
        this.decisionNumber = decisionNumber;
    }
}

