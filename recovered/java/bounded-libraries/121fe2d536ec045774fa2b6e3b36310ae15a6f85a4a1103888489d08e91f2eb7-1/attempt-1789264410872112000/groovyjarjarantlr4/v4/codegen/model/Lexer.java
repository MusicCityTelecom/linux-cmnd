/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.LexerFile;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.Recognizer;
import groovyjarjarantlr4.v4.codegen.model.RuleActionFunction;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer
extends Recognizer {
    public Map<String, Integer> channels;
    public LexerFile file;
    public Collection<String> modes;
    @ModelElement
    public LinkedHashMap<Rule, RuleActionFunction> actionFuncs = new LinkedHashMap();

    public Lexer(OutputModelFactory factory, LexerFile file) {
        super(factory);
        this.file = file;
        Grammar g = factory.getGrammar();
        this.channels = new LinkedHashMap<String, Integer>(g.channelNameToValueMap);
        this.modes = ((LexerGrammar)g).modes.keySet();
    }
}

