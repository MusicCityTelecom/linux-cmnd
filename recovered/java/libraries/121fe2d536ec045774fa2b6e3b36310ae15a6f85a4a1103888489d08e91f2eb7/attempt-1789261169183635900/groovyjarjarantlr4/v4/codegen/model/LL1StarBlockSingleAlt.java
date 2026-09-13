/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1Loop;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class LL1StarBlockSingleAlt
extends LL1Loop {
    public LL1StarBlockSingleAlt(OutputModelFactory factory, GrammarAST starRoot, List<CodeBlockForAlt> alts) {
        super(factory, starRoot, alts);
        StarLoopEntryState star = (StarLoopEntryState)starRoot.atnState;
        this.loopBackStateNumber = star.loopBackState.stateNumber;
        this.decision = star.decision;
        IntervalSet[] altLookSets = factory.getGrammar().decisionLOOK.get(this.decision);
        assert (altLookSets.length == 2);
        IntervalSet enterLook = altLookSets[0];
        IntervalSet exitLook = altLookSets[1];
        this.loopExpr = this.addCodeForLoopLookaheadTempVar(enterLook);
    }
}

