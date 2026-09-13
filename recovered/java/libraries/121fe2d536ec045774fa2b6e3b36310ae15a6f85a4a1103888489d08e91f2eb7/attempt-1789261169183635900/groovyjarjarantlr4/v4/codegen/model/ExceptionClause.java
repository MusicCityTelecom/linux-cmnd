/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;

public class ExceptionClause
extends SrcOp {
    @ModelElement
    public Action catchArg;
    @ModelElement
    public Action catchAction;

    public ExceptionClause(OutputModelFactory factory, ActionAST catchArg, ActionAST catchAction) {
        super(factory, catchArg);
        this.catchArg = new Action(factory, catchArg);
        this.catchAction = new Action(factory, catchAction);
    }
}

