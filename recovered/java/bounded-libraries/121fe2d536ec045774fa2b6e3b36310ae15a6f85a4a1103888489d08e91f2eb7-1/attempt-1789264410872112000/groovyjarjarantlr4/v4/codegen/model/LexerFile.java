/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.Lexer;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputFile;
import java.util.Map;

public class LexerFile
extends OutputFile {
    public String genPackage;
    public String exportMacro;
    public boolean genListener;
    public boolean genVisitor;
    @ModelElement
    public Lexer lexer;
    @ModelElement
    public Map<String, Action> namedActions;

    public LexerFile(OutputModelFactory factory, String fileName) {
        super(factory, fileName);
        this.namedActions = this.buildNamedActions(factory.getGrammar());
        this.genPackage = factory.getGrammar().tool.genPackage;
        this.exportMacro = factory.getGrammar().getOptionString("exportMacro");
        this.genListener = factory.getGrammar().tool.gen_listener;
        this.genVisitor = factory.getGrammar().tool.gen_visitor;
    }
}

