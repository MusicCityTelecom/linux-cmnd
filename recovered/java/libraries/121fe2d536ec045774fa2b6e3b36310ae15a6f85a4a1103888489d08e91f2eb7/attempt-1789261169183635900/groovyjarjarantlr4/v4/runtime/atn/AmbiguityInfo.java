/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.DecisionEventInfo;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.BitSet;

public class AmbiguityInfo
extends DecisionEventInfo {
    @NotNull
    private final BitSet ambigAlts;

    public AmbiguityInfo(int decision, @NotNull SimulatorState state, @NotNull BitSet ambigAlts, @NotNull TokenStream input, int startIndex, int stopIndex) {
        super(decision, state, input, startIndex, stopIndex, state.useContext);
        this.ambigAlts = ambigAlts;
    }

    @NotNull
    public BitSet getAmbiguousAlternatives() {
        return this.ambigAlts;
    }
}

