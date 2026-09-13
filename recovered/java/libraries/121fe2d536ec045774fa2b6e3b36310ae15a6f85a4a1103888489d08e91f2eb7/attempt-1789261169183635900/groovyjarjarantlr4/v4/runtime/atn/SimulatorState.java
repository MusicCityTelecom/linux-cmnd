/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class SimulatorState {
    public final ParserRuleContext outerContext;
    public final DFAState s0;
    public final boolean useContext;
    public final ParserRuleContext remainingOuterContext;

    public SimulatorState(ParserRuleContext outerContext, @NotNull DFAState s0, boolean useContext, ParserRuleContext remainingOuterContext) {
        this.outerContext = outerContext != null ? outerContext : ParserRuleContext.emptyContext();
        this.s0 = s0;
        this.useContext = useContext;
        this.remainingOuterContext = remainingOuterContext;
    }
}

