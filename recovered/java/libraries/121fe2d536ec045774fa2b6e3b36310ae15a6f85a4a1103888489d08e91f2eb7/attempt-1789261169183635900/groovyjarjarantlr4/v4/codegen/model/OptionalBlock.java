/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.AltBlock;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class OptionalBlock
extends AltBlock {
    public OptionalBlock(OutputModelFactory factory, GrammarAST questionAST, List<CodeBlockForAlt> alts) {
        super(factory, questionAST, alts);
    }
}

