/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.v4.automata.ATNVisitor;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.BlockEndState;
import groovyjarjarantlr4.v4.runtime.atn.EpsilonTransition;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class TailEpsilonRemover
extends ATNVisitor {
    @NotNull
    private final ATN _atn;

    public TailEpsilonRemover(@NotNull ATN atn) {
        this._atn = atn;
    }

    @Override
    public void visitState(@NotNull ATNState p) {
        if (p.getStateType() == 1 && p.getNumberOfTransitions() == 1) {
            ATNState q = p.transition((int)0).target;
            if (p.transition(0) instanceof RuleTransition) {
                q = ((RuleTransition)p.transition((int)0)).followState;
            }
            if (q.getStateType() == 1) {
                ATNState r;
                Transition trans = q.transition(0);
                if (q.getNumberOfTransitions() == 1 && trans instanceof EpsilonTransition && ((r = trans.target) instanceof BlockEndState || r instanceof PlusLoopbackState || r instanceof StarLoopbackState)) {
                    if (p.transition(0) instanceof RuleTransition) {
                        ((RuleTransition)p.transition((int)0)).followState = r;
                    } else {
                        p.transition((int)0).target = r;
                    }
                    this._atn.removeState(q);
                }
            }
        }
    }
}

