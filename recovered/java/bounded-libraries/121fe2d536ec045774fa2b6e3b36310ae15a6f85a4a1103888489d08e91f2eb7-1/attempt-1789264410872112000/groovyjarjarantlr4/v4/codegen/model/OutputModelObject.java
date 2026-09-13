/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public abstract class OutputModelObject {
    public OutputModelFactory factory;
    public GrammarAST ast;

    public OutputModelObject() {
    }

    public OutputModelObject(OutputModelFactory factory) {
        this(factory, null);
    }

    public OutputModelObject(OutputModelFactory factory, GrammarAST ast) {
        this.factory = factory;
        this.ast = ast;
    }
}

