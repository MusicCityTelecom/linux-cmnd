/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1Loop;
import groovyjarjarantlr4.v4.runtime.atn.PlusBlockStartState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class LL1PlusBlockSingleAlt
extends LL1Loop {
    public LL1PlusBlockSingleAlt(OutputModelFactory factory, GrammarAST plusRoot, List<CodeBlockForAlt> alts) {
        super(factory, plusRoot, alts);
        BlockAST blkAST = (BlockAST)plusRoot.getChild(0);
        PlusBlockStartState blkStart = (PlusBlockStartState)blkAST.atnState;
        this.stateNumber = blkStart.loopBackState.stateNumber;
        this.blockStartStateNumber = blkStart.stateNumber;
        PlusBlockStartState plus = (PlusBlockStartState)blkAST.atnState;
        this.decision = plus.loopBackState.decision;
        IntervalSet[] altLookSets = factory.getGrammar().decisionLOOK.get(this.decision);
        IntervalSet loopBackLook = altLookSets[0];
        this.loopExpr = this.addCodeForLoopLookaheadTempVar(loopBackLook);
    }
}

