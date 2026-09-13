/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class RuleElement
extends SrcOp {
    public int stateNumber;

    public RuleElement(OutputModelFactory factory, GrammarAST ast) {
        super(factory, ast);
        if (ast != null && ast.atnState != null) {
            this.stateNumber = ast.atnState.stateNumber;
        }
    }
}

