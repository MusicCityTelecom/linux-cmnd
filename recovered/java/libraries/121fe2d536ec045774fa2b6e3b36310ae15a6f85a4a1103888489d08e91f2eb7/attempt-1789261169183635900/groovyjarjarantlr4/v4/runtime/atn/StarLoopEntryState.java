/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopbackState;
import java.util.BitSet;

public final class StarLoopEntryState
extends DecisionState {
    public StarLoopbackState loopBackState;
    public boolean precedenceRuleDecision;
    public BitSet precedenceLoopbackStates;

    @Override
    public int getStateType() {
        return 10;
    }
}

