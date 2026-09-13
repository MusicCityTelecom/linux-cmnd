/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class Sync
extends SrcOp {
    public int decision;

    public Sync(OutputModelFactory factory, GrammarAST blkOrEbnfRootAST, IntervalSet expecting, int decision, String position) {
        super(factory, blkOrEbnfRootAST);
        this.decision = decision;
    }
}

