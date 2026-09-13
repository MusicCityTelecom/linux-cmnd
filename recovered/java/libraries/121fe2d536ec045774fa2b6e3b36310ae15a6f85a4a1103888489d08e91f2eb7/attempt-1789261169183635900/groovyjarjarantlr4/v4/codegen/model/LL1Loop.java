/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CaptureNextTokenType;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.TestSetInline;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.ArrayList;
import java.util.List;

public abstract class LL1Loop
extends Choice {
    public int blockStartStateNumber;
    public int loopBackStateNumber;
    @ModelElement
    public OutputModelObject loopExpr;
    @ModelElement
    public List<SrcOp> iteration;

    public LL1Loop(OutputModelFactory factory, GrammarAST blkAST, List<CodeBlockForAlt> alts) {
        super(factory, blkAST, alts);
    }

    public void addIterationOp(SrcOp op) {
        if (this.iteration == null) {
            this.iteration = new ArrayList<SrcOp>();
        }
        this.iteration.add(op);
    }

    public SrcOp addCodeForLoopLookaheadTempVar(IntervalSet look) {
        TestSetInline expr = this.addCodeForLookaheadTempVar(look);
        if (expr != null) {
            CaptureNextTokenType nextType = new CaptureNextTokenType(this.factory, expr.varName);
            this.addIterationOp(nextType);
        }
        return expr;
    }
}

