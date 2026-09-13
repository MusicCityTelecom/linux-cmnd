/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1AltBlock;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class LL1OptionalBlock
extends LL1AltBlock {
    public LL1OptionalBlock(OutputModelFactory factory, GrammarAST blkAST, List<CodeBlockForAlt> alts) {
        super(factory, blkAST, alts);
    }
}

