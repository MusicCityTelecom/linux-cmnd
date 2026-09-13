/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputFile;
import groovyjarjarantlr4.v4.codegen.model.Parser;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionText;
import groovyjarjarantlr4.v4.tool.Grammar;
import java.util.Map;

public class ParserFile
extends OutputFile {
    public String genPackage;
    public String exportMacro;
    public boolean genListener;
    public boolean genVisitor;
    @ModelElement
    public Parser parser;
    @ModelElement
    public Map<String, Action> namedActions;
    @ModelElement
    public ActionChunk contextSuperClass;
    public String grammarName;

    public ParserFile(OutputModelFactory factory, String fileName) {
        super(factory, fileName);
        Grammar g = factory.getGrammar();
        this.namedActions = this.buildNamedActions(factory.getGrammar());
        this.genPackage = g.tool.genPackage;
        this.exportMacro = factory.getGrammar().getOptionString("exportMacro");
        this.genListener = g.tool.gen_listener;
        this.genVisitor = g.tool.gen_visitor;
        this.grammarName = g.name;
        if (g.getOptionString("contextSuperClass") != null) {
            this.contextSuperClass = new ActionText(null, g.getOptionString("contextSuperClass"));
        }
    }
}

