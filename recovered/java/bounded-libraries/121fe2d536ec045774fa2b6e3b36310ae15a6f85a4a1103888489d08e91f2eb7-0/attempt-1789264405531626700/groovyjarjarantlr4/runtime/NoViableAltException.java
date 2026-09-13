/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class NoViableAltException
extends RecognitionException {
    public String grammarDecisionDescription;
    public int decisionNumber;
    public int stateNumber;

    public NoViableAltException() {
    }

    public NoViableAltException(String grammarDecisionDescription, int decisionNumber, int stateNumber, IntStream input) {
        super(input);
        this.grammarDecisionDescription = grammarDecisionDescription;
        this.decisionNumber = decisionNumber;
        this.stateNumber = stateNumber;
    }

    public String toString() {
        if (this.input instanceof CharStream) {
            return "NoViableAltException('" + (char)this.getUnexpectedType() + "'@[" + this.grammarDecisionDescription + "])";
        }
        return "NoViableAltException(" + this.getUnexpectedType() + "@[" + this.grammarDecisionDescription + "])";
    }
}

