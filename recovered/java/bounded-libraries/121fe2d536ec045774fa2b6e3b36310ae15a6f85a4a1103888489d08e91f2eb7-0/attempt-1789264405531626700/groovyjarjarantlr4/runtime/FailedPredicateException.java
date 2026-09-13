/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class FailedPredicateException
extends RecognitionException {
    public String ruleName;
    public String predicateText;

    public FailedPredicateException() {
    }

    public FailedPredicateException(IntStream input, String ruleName, String predicateText) {
        super(input);
        this.ruleName = ruleName;
        this.predicateText = predicateText;
    }

    public String toString() {
        return "FailedPredicateException(" + this.ruleName + ",{" + this.predicateText + "}?)";
    }
}

