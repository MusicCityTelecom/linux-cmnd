/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import java.util.HashMap;
import java.util.Map;

public abstract class OutputFile
extends OutputModelObject {
    public final String fileName;
    public final String grammarFileName;
    public final String ANTLRVersion;
    public final String TokenLabelType;
    public final String InputSymbolType;

    public OutputFile(OutputModelFactory factory, String fileName) {
        super(factory);
        this.fileName = fileName;
        Grammar g = factory.getGrammar();
        this.grammarFileName = g.fileName;
        this.ANTLRVersion = Tool.VERSION;
        this.InputSymbolType = this.TokenLabelType = g.getOptionString("TokenLabelType");
    }

    public Map<String, Action> buildNamedActions(Grammar g) {
        HashMap<String, Action> namedActions = new HashMap<String, Action>();
        for (String name : g.namedActions.keySet()) {
            ActionAST ast = g.namedActions.get(name);
            namedActions.put(name, new Action(this.factory, ast));
        }
        return namedActions;
    }
}

