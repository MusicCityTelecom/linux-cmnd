/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.runtime.atn.BlockStartState;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class AltBlock
extends Choice {
    public AltBlock(OutputModelFactory factory, GrammarAST blkOrEbnfRootAST, List<CodeBlockForAlt> alts) {
        super(factory, blkOrEbnfRootAST, alts);
        this.decision = ((BlockStartState)blkOrEbnfRootAST.atnState).decision;
    }
}

