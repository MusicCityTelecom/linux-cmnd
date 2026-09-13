/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.QuantifierAST;
import java.util.ArrayList;
import java.util.List;

public class Loop
extends Choice {
    public int blockStartStateNumber;
    public int loopBackStateNumber;
    public final int exitAlt;
    @ModelElement
    public List<SrcOp> iteration;

    public Loop(OutputModelFactory factory, GrammarAST blkOrEbnfRootAST, List<CodeBlockForAlt> alts) {
        super(factory, blkOrEbnfRootAST, alts);
        boolean nongreedy = blkOrEbnfRootAST instanceof QuantifierAST && !((QuantifierAST)((Object)blkOrEbnfRootAST)).isGreedy();
        this.exitAlt = nongreedy ? 1 : alts.size() + 1;
    }

    public void addIterationOp(SrcOp op) {
        if (this.iteration == null) {
            this.iteration = new ArrayList<SrcOp>();
        }
        this.iteration.add(op);
    }
}

