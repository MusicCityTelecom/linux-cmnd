/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CaptureNextTokenType;
import groovyjarjarantlr4.v4.codegen.model.MatchToken;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.TestSetInline;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenTypeDecl;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class MatchSet
extends MatchToken {
    @ModelElement
    public TestSetInline expr;
    @ModelElement
    public CaptureNextTokenType capture;

    public MatchSet(OutputModelFactory factory, GrammarAST ast) {
        super(factory, ast);
        SetTransition st = (SetTransition)ast.atnState.transition(0);
        int wordSize = factory.getGenerator().getTarget().getInlineTestSetWordSize();
        this.expr = new TestSetInline(factory, null, st.set, wordSize);
        TokenTypeDecl d = new TokenTypeDecl(factory, this.expr.varName);
        factory.getCurrentRuleFunction().addLocalDecl(d);
        this.capture = new CaptureNextTokenType(factory, this.expr.varName);
    }
}

