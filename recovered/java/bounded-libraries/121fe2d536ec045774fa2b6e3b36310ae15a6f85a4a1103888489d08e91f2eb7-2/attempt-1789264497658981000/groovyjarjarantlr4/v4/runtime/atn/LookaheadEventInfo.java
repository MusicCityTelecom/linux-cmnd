/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.DecisionEventInfo;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class LookaheadEventInfo
extends DecisionEventInfo {
    public final int predictedAlt;

    public LookaheadEventInfo(int decision, @Nullable SimulatorState state, int predictedAlt, @NotNull TokenStream input, int startIndex, int stopIndex, boolean fullCtx) {
        super(decision, state, input, startIndex, stopIndex, fullCtx);
        this.predictedAlt = predictedAlt;
    }
}

