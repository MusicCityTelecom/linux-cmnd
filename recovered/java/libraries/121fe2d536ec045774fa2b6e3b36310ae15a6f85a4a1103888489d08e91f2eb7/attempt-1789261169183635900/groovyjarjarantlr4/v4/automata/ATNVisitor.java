/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.HashSet;
import java.util.Set;

public class ATNVisitor {
    public void visit(@NotNull ATNState s) {
        this.visit_(s, new HashSet<Integer>());
    }

    public void visit_(@NotNull ATNState s, @NotNull Set<Integer> visited) {
        if (!visited.add(s.stateNumber)) {
            return;
        }
        visited.add(s.stateNumber);
        this.visitState(s);
        int n = s.getNumberOfTransitions();
        for (int i = 0; i < n; ++i) {
            Transition t = s.transition(i);
            this.visit_(t.target, visited);
        }
    }

    public void visitState(@NotNull ATNState s) {
    }
}

