/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.DecisionEventInfo;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class PredicateEvalInfo
extends DecisionEventInfo {
    public final SemanticContext semctx;
    public final int predictedAlt;
    public final boolean evalResult;

    public PredicateEvalInfo(@NotNull SimulatorState state, int decision, @NotNull TokenStream input, int startIndex, int stopIndex, @NotNull SemanticContext semctx, boolean evalResult, int predictedAlt) {
        super(decision, state, input, startIndex, stopIndex, state.useContext);
        this.semctx = semctx;
        this.evalResult = evalResult;
        this.predictedAlt = predictedAlt;
    }
}

