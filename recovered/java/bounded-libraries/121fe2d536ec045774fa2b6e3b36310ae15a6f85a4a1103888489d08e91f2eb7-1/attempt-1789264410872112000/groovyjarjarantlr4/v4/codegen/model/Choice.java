/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CaptureNextTokenType;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.RuleElement;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.TestSetInline;
import groovyjarjarantlr4.v4.codegen.model.ThrowNoViableAlt;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenTypeDecl;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.ArrayList;
import java.util.List;

public abstract class Choice
extends RuleElement {
    public int decision = -1;
    public Decl label;
    @ModelElement
    public List<CodeBlockForAlt> alts;
    @ModelElement
    public List<SrcOp> preamble = new ArrayList<SrcOp>();

    public Choice(OutputModelFactory factory, GrammarAST blkOrEbnfRootAST, List<CodeBlockForAlt> alts) {
        super(factory, blkOrEbnfRootAST);
        this.alts = alts;
    }

    public void addPreambleOp(SrcOp op) {
        this.preamble.add(op);
    }

    public List<String[]> getAltLookaheadAsStringLists(IntervalSet[] altLookSets) {
        ArrayList<String[]> altLook = new ArrayList<String[]>();
        for (IntervalSet s : altLookSets) {
            altLook.add(this.factory.getTarget().getTokenTypesAsTargetLabels(this.factory.getGrammar(), s.toArray()));
        }
        return altLook;
    }

    public TestSetInline addCodeForLookaheadTempVar(IntervalSet look) {
        List<SrcOp> testOps = this.factory.getLL1Test(look, this.ast);
        TestSetInline expr = Utils.find(testOps, TestSetInline.class);
        if (expr != null) {
            TokenTypeDecl d = new TokenTypeDecl(this.factory, expr.varName);
            this.factory.getCurrentRuleFunction().addLocalDecl(d);
            CaptureNextTokenType nextType = new CaptureNextTokenType(this.factory, expr.varName);
            this.addPreambleOp(nextType);
        }
        return expr;
    }

    public ThrowNoViableAlt getThrowNoViableAlt(OutputModelFactory factory, GrammarAST blkAST, IntervalSet expecting) {
        return new ThrowNoViableAlt(factory, blkAST, expecting);
    }
}

