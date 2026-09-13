/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.DecisionEventInfo;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class ErrorInfo
extends DecisionEventInfo {
    public ErrorInfo(int decision, @NotNull SimulatorState state, @NotNull TokenStream input, int startIndex, int stopIndex) {
        super(decision, state, input, startIndex, stopIndex, state.useContext);
    }
}

