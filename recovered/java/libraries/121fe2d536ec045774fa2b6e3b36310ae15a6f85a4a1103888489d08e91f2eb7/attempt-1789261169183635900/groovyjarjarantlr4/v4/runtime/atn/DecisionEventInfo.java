/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class DecisionEventInfo {
    public final int decision;
    @Nullable
    public final SimulatorState state;
    @NotNull
    public final TokenStream input;
    public final int startIndex;
    public final int stopIndex;
    public final boolean fullCtx;

    public DecisionEventInfo(int decision, @Nullable SimulatorState state, @NotNull TokenStream input, int startIndex, int stopIndex, boolean fullCtx) {
        this.decision = decision;
        this.fullCtx = fullCtx;
        this.stopIndex = stopIndex;
        this.input = input;
        this.startIndex = startIndex;
        this.state = state;
    }
}

