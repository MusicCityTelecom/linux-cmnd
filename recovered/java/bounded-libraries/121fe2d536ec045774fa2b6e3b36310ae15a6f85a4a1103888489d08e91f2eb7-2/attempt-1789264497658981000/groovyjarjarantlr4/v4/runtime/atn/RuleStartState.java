/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;

public final class RuleStartState
extends ATNState {
    public RuleStopState stopState;
    public boolean isPrecedenceRule;
    public boolean leftFactored;

    @Override
    public int getStateType() {
        return 2;
    }
}

