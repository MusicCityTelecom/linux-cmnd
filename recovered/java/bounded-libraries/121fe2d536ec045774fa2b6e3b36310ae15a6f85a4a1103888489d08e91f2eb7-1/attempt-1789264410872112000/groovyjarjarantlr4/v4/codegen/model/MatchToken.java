/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.LabeledOp;
import groovyjarjarantlr4.v4.codegen.model.RuleElement;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;
import java.util.List;

public class MatchToken
extends RuleElement
implements LabeledOp {
    public String name;
    public int ttype;
    public List<Decl> labels = new ArrayList<Decl>();

    public MatchToken(OutputModelFactory factory, TerminalAST ast) {
        super(factory, ast);
        Grammar g = factory.getGrammar();
        this.ttype = g.getTokenType(ast.getText());
        this.name = factory.getTarget().getTokenTypeAsTargetLabel(g, this.ttype);
    }

    public MatchToken(OutputModelFactory factory, GrammarAST ast) {
        super(factory, ast);
    }

    @Override
    public List<Decl> getLabels() {
        return this.labels;
    }
}

