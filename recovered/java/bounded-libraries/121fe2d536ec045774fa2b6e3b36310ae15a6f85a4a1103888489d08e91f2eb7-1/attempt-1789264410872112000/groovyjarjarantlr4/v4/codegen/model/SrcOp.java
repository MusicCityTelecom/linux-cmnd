/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForOuterMostAlt;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.decl.CodeBlock;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public abstract class SrcOp
extends OutputModelObject {
    public int uniqueID;
    public CodeBlock enclosingBlock;
    public RuleFunction enclosingRuleRunction;

    public SrcOp(OutputModelFactory factory) {
        this(factory, null);
    }

    public SrcOp(OutputModelFactory factory, GrammarAST ast) {
        super(factory, ast);
        if (ast != null) {
            this.uniqueID = ast.token.getTokenIndex();
        }
        this.enclosingBlock = factory.getCurrentBlock();
        this.enclosingRuleRunction = factory.getCurrentRuleFunction();
    }

    public CodeBlockForOuterMostAlt getOuterMostAltCodeBlock() {
        if (this instanceof CodeBlockForOuterMostAlt) {
            return (CodeBlockForOuterMostAlt)this;
        }
        CodeBlock p = this.enclosingBlock;
        while (p != null) {
            if (p instanceof CodeBlockForOuterMostAlt) {
                return (CodeBlockForOuterMostAlt)p;
            }
            p = p.enclosingBlock;
        }
        return null;
    }

    public String getContextName() {
        CodeBlockForOuterMostAlt alt = this.getOuterMostAltCodeBlock();
        if (alt != null && alt.altLabel != null) {
            return alt.altLabel;
        }
        return this.enclosingRuleRunction.name;
    }
}

