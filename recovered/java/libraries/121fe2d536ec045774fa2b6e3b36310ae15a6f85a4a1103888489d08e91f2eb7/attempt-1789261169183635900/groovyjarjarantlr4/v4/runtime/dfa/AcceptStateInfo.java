/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.atn.LexerActionExecutor;

public class AcceptStateInfo {
    private final int prediction;
    private final LexerActionExecutor lexerActionExecutor;

    public AcceptStateInfo(int prediction) {
        this.prediction = prediction;
        this.lexerActionExecutor = null;
    }

    public AcceptStateInfo(int prediction, LexerActionExecutor lexerActionExecutor) {
        this.prediction = prediction;
        this.lexerActionExecutor = lexerActionExecutor;
    }

    public int getPrediction() {
        return this.prediction;
    }

    public LexerActionExecutor getLexerActionExecutor() {
        return this.lexerActionExecutor;
    }
}

