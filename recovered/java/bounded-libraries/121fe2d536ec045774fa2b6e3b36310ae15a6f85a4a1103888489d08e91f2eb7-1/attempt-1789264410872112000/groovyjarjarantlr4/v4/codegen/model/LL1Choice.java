/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.ThrowNoViableAlt;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public abstract class LL1Choice
extends Choice {
    public List<String[]> altLook;
    @ModelElement
    public ThrowNoViableAlt error;

    public LL1Choice(OutputModelFactory factory, GrammarAST blkAST, List<CodeBlockForAlt> alts) {
        super(factory, blkAST, alts);
    }
}

