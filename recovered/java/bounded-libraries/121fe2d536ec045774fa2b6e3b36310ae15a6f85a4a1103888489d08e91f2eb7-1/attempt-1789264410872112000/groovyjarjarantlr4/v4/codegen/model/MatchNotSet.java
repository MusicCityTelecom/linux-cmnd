/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.MatchSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class MatchNotSet
extends MatchSet {
    public String varName = "_la";

    public MatchNotSet(OutputModelFactory factory, GrammarAST ast) {
        super(factory, ast);
    }
}

