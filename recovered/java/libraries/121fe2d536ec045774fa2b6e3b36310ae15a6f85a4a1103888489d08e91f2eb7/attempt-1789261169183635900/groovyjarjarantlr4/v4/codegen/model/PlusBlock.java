/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.Loop;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.ThrowNoViableAlt;
import groovyjarjarantlr4.v4.runtime.atn.PlusBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class PlusBlock
extends Loop {
    @ModelElement
    public ThrowNoViableAlt error;

    public PlusBlock(OutputModelFactory factory, GrammarAST plusRoot, List<CodeBlockForAlt> alts) {
        super(factory, plusRoot, alts);
        BlockAST blkAST = (BlockAST)plusRoot.getChild(0);
        PlusBlockStartState blkStart = (PlusBlockStartState)blkAST.atnState;
        PlusLoopbackState loop = blkStart.loopBackState;
        this.stateNumber = blkStart.loopBackState.stateNumber;
        this.blockStartStateNumber = blkStart.stateNumber;
        this.loopBackStateNumber = loop.stateNumber;
        this.error = this.getThrowNoViableAlt(factory, plusRoot, null);
        this.decision = loop.decision;
    }
}

