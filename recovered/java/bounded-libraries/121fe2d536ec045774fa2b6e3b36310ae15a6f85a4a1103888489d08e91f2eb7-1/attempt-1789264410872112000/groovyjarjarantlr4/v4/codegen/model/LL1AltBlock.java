/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1Choice;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class LL1AltBlock
extends LL1Choice {
    public LL1AltBlock(OutputModelFactory factory, GrammarAST blkAST, List<CodeBlockForAlt> alts) {
        super(factory, blkAST, alts);
        this.decision = ((DecisionState)blkAST.atnState).decision;
        IntervalSet[] altLookSets = factory.getGrammar().decisionLOOK.get(this.decision);
        this.altLook = this.getAltLookaheadAsStringLists(altLookSets);
        IntervalSet expecting = IntervalSet.or(altLookSets);
        this.error = this.getThrowNoViableAlt(factory, blkAST, expecting);
    }
}

