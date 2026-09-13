/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class ThrowRecognitionException
extends SrcOp {
    public int decision;
    public String grammarFile;
    public int grammarLine;
    public int grammarCharPosInLine;

    public ThrowRecognitionException(OutputModelFactory factory, GrammarAST ast, IntervalSet expecting) {
        super(factory, ast);
        this.grammarLine = ast.getLine();
        this.grammarLine = ast.getCharPositionInLine();
        this.grammarFile = factory.getGrammar().fileName;
    }
}

