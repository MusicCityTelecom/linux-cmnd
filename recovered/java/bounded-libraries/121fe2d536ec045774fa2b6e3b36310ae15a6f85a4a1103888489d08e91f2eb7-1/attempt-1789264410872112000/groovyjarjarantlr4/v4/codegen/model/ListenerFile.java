/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputFile;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListenerFile
extends OutputFile {
    public String genPackage;
    public String accessLevel;
    public String exportMacro;
    public String grammarName;
    public String parserName;
    public Set<String> listenerNames = new LinkedHashSet<String>();
    public Map<String, String> listenerLabelRuleNames = new LinkedHashMap<String, String>();
    @ModelElement
    public Action header;
    @ModelElement
    public Map<String, Action> namedActions;

    public ListenerFile(OutputModelFactory factory, String fileName) {
        super(factory, fileName);
        Grammar g = factory.getGrammar();
        this.parserName = g.getRecognizerName();
        this.grammarName = g.name;
        this.namedActions = this.buildNamedActions(factory.getGrammar());
        for (Map.Entry<String, List<RuleAST>> entry : g.contextASTs.entrySet()) {
            for (RuleAST ruleAST : entry.getValue()) {
                try {
                    Map<String, List<Tuple2<Integer, AltAST>>> labeledAlternatives = g.getLabeledAlternatives(ruleAST);
                    this.listenerNames.addAll(labeledAlternatives.keySet());
                }
                catch (RecognitionException ex) {}
            }
        }
        for (Rule r : g.rules.values()) {
            this.listenerNames.add(r.getBaseContext());
        }
        for (Rule r : g.rules.values()) {
            Map<String, List<Tuple2<Integer, AltAST>>> labels = r.getAltLabels();
            if (labels == null) continue;
            for (Map.Entry<String, List<Tuple2<Integer, AltAST>>> pair : labels.entrySet()) {
                this.listenerLabelRuleNames.put(pair.getKey(), r.name);
            }
        }
        ActionAST ast = g.namedActions.get("header");
        if (ast != null) {
            this.header = new Action(factory, ast);
        }
        this.genPackage = g.tool.genPackage;
        this.accessLevel = g.getOptionString("accessLevel");
        this.exportMacro = g.getOptionString("exportMacro");
    }
}

