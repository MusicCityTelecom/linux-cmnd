/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;

public class ArgAction
extends Action {
    public String ctxType;

    public ArgAction(OutputModelFactory factory, ActionAST ast, String ctxType) {
        super(factory, ast);
        this.ctxType = ctxType;
    }
}

