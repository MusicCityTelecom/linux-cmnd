/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.ThrowRecognitionException;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class ThrowNoViableAlt
extends ThrowRecognitionException {
    public ThrowNoViableAlt(OutputModelFactory factory, GrammarAST blkOrEbnfRootAST, IntervalSet expecting) {
        super(factory, blkOrEbnfRootAST, expecting);
    }
}

