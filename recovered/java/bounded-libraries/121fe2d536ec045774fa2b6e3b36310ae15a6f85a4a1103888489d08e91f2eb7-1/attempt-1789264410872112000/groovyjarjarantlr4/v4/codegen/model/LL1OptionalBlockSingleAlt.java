/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1Choice;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class LL1OptionalBlockSingleAlt
extends LL1Choice {
    @ModelElement
    public SrcOp expr;
    @ModelElement
    public List<SrcOp> followExpr;

    public LL1OptionalBlockSingleAlt(OutputModelFactory factory, GrammarAST blkAST, List<CodeBlockForAlt> alts) {
        super(factory, blkAST, alts);
        this.decision = ((DecisionState)blkAST.atnState).decision;
        IntervalSet[] altLookSets = factory.getGrammar().decisionLOOK.get(this.decision);
        this.altLook = this.getAltLookaheadAsStringLists(altLookSets);
        IntervalSet look = altLookSets[0];
        IntervalSet followLook = altLookSets[1];
        IntervalSet expecting = look.or(followLook);
        this.error = this.getThrowNoViableAlt(factory, blkAST, expecting);
        this.expr = this.addCodeForLookaheadTempVar(look);
        this.followExpr = factory.getLL1Test(followLook, blkAST);
    }
}

