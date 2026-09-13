/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.RuleSempredFunction;
import groovyjarjarantlr4.v4.codegen.model.SerializedATN;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionText;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Recognizer
extends OutputModelObject {
    public String name;
    public String grammarName;
    public String grammarFileName;
    public String accessLevel;
    public Map<String, Integer> tokens;
    @Deprecated
    public List<String> tokenNames;
    public List<String> literalNames;
    public List<String> symbolicNames;
    public Set<String> ruleNames;
    public Collection<Rule> rules;
    @ModelElement
    public ActionChunk superClass;
    public boolean abstractRecognizer;
    @ModelElement
    public SerializedATN atn;
    @ModelElement
    public LinkedHashMap<Rule, RuleSempredFunction> sempredFuncs = new LinkedHashMap();

    public Recognizer(OutputModelFactory factory) {
        super(factory);
        Grammar g = factory.getGrammar();
        this.grammarFileName = new File(g.fileName).getName();
        this.grammarName = g.name;
        this.name = g.getRecognizerName();
        this.accessLevel = g.getOptionString("accessLevel");
        this.tokens = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : g.tokenNameToTypeMap.entrySet()) {
            Integer ttype = entry.getValue();
            if (ttype <= 0) continue;
            this.tokens.put(entry.getKey(), ttype);
        }
        this.ruleNames = g.rules.keySet();
        this.rules = g.rules.values();
        this.atn = new SerializedATN(factory, g.atn, Arrays.asList(g.getRuleNames()));
        this.superClass = g.getOptionString("superClass") != null ? new ActionText(null, g.getOptionString("superClass")) : null;
        this.tokenNames = Recognizer.translateTokenStringsToTarget(g.getTokenDisplayNames(), factory);
        this.literalNames = Recognizer.translateTokenStringsToTarget(g.getTokenLiteralNames(), factory);
        this.symbolicNames = Recognizer.translateTokenStringsToTarget(g.getTokenSymbolicNames(), factory);
        this.abstractRecognizer = g.isAbstract();
    }

    protected static List<String> translateTokenStringsToTarget(String[] tokenStrings, OutputModelFactory factory) {
        int lastTrueEntry;
        String[] result = (String[])tokenStrings.clone();
        for (int i = 0; i < tokenStrings.length; ++i) {
            result[i] = Recognizer.translateTokenStringToTarget(tokenStrings[i], factory);
        }
        for (lastTrueEntry = result.length - 1; lastTrueEntry >= 0 && result[lastTrueEntry] == null; --lastTrueEntry) {
        }
        if (lastTrueEntry < result.length - 1) {
            result = Arrays.copyOf(result, lastTrueEntry + 1);
        }
        return Arrays.asList(result);
    }

    protected static String translateTokenStringToTarget(String tokenName, OutputModelFactory factory) {
        if (tokenName == null) {
            return null;
        }
        if (tokenName.charAt(0) == '\'') {
            boolean addQuotes = false;
            String targetString = factory.getTarget().getTargetStringLiteralFromANTLRStringLiteral(factory.getGenerator(), tokenName, addQuotes);
            return "\"'" + targetString + "'\"";
        }
        return factory.getTarget().getTargetStringLiteralFromString(tokenName, true);
    }
}

