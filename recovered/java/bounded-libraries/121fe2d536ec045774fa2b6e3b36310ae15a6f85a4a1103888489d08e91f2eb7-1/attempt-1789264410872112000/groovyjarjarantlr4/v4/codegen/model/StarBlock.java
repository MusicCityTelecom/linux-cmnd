/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.Loop;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class StarBlock
extends Loop {
    public String loopLabel;

    public StarBlock(OutputModelFactory factory, GrammarAST blkOrEbnfRootAST, List<CodeBlockForAlt> alts) {
        super(factory, blkOrEbnfRootAST, alts);
        this.loopLabel = factory.getTarget().getLoopLabel(blkOrEbnfRootAST);
        StarLoopEntryState star = (StarLoopEntryState)blkOrEbnfRootAST.atnState;
        this.loopBackStateNumber = star.loopBackState.stateNumber;
        this.decision = star.decision;
    }
}

