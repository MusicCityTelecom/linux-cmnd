/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.runtime.misc.MultiMap;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.AttributeResolver;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LabelElementPair;
import groovyjarjarantlr4.v4.tool.LabelType;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rule
implements AttributeResolver {
    public static final AttributeDict predefinedRulePropertiesDict = new AttributeDict(AttributeDict.DictType.PREDEFINED_RULE);
    public static final Set<String> validLexerCommands;
    public String name;
    private String baseContext;
    public List<GrammarAST> modifiers;
    public RuleAST ast;
    public AttributeDict args;
    public AttributeDict retvals;
    public AttributeDict locals;
    public Grammar g;
    public String mode;
    public Map<String, ActionAST> namedActions = new HashMap<String, ActionAST>();
    public List<GrammarAST> exceptions = new ArrayList<GrammarAST>();
    public List<ActionAST> actions = new ArrayList<ActionAST>();
    public ActionAST finallyAction;
    public int numberOfAlts;
    public boolean isStartRule = true;
    public Alternative[] alt;
    public int index;
    public int actionIndex = -1;

    public Rule(Grammar g, String name, RuleAST ast, int numberOfAlts) {
        this.g = g;
        this.name = name;
        this.ast = ast;
        this.numberOfAlts = numberOfAlts;
        this.alt = new Alternative[numberOfAlts + 1];
        for (int i = 1; i <= numberOfAlts; ++i) {
            this.alt[i] = new Alternative(this, i);
        }
    }

    public String getBaseContext() {
        if (this.baseContext != null && !this.baseContext.isEmpty()) {
            return this.baseContext;
        }
        String optionBaseContext = this.ast.getOptionString("baseContext");
        if (optionBaseContext != null && !optionBaseContext.isEmpty()) {
            return optionBaseContext;
        }
        int variantDelimiter = this.name.indexOf(36);
        if (variantDelimiter >= 0) {
            return this.name.substring(0, variantDelimiter);
        }
        return this.name;
    }

    public void setBaseContext(String baseContext) {
        this.baseContext = baseContext;
    }

    public void defineActionInAlt(int currentAlt, ActionAST actionAST) {
        this.actions.add(actionAST);
        this.alt[currentAlt].actions.add(actionAST);
        if (this.g.isLexer()) {
            this.defineLexerAction(actionAST);
        }
    }

    public void defineLexerAction(ActionAST actionAST) {
        this.actionIndex = this.g.lexerActions.size();
        if (this.g.lexerActions.get(actionAST) == null) {
            this.g.lexerActions.put(actionAST, this.actionIndex);
        }
    }

    public void definePredicateInAlt(int currentAlt, PredAST predAST) {
        this.actions.add(predAST);
        this.alt[currentAlt].actions.add(predAST);
        if (this.g.sempreds.get(predAST) == null) {
            this.g.sempreds.put(predAST, this.g.sempreds.size());
        }
    }

    public Attribute resolveRetvalOrProperty(String y) {
        Attribute a;
        if (this.retvals != null && (a = this.retvals.get(y)) != null) {
            return a;
        }
        AttributeDict d = this.getPredefinedScope(LabelType.RULE_LABEL);
        return d.get(y);
    }

    public Set<String> getTokenRefs() {
        HashSet<String> refs = new HashSet<String>();
        for (int i = 1; i <= this.numberOfAlts; ++i) {
            refs.addAll(this.alt[i].tokenRefs.keySet());
        }
        return refs;
    }

    public Set<String> getElementLabelNames() {
        HashSet<String> refs = new HashSet<String>();
        for (int i = 1; i <= this.numberOfAlts; ++i) {
            refs.addAll(this.alt[i].labelDefs.keySet());
        }
        if (refs.isEmpty()) {
            return null;
        }
        return refs;
    }

    public MultiMap<String, LabelElementPair> getElementLabelDefs() {
        MultiMap<String, LabelElementPair> defs = new MultiMap<String, LabelElementPair>();
        for (int i = 1; i <= this.numberOfAlts; ++i) {
            for (List pairs : this.alt[i].labelDefs.values()) {
                for (LabelElementPair p : pairs) {
                    defs.map(p.label.getText(), p);
                }
            }
        }
        return defs;
    }

    public boolean hasAltSpecificContexts() {
        return this.getAltLabels() != null;
    }

    public int getOriginalNumberOfAlts() {
        return this.numberOfAlts;
    }

    public Map<String, List<Tuple2<Integer, AltAST>>> getAltLabels() {
        LinkedHashMap<String, List<Tuple2<Integer, AltAST>>> labels = new LinkedHashMap<String, List<Tuple2<Integer, AltAST>>>();
        for (int i = 1; i <= this.numberOfAlts; ++i) {
            GrammarAST altLabel = this.alt[i].ast.altLabel;
            if (altLabel == null) continue;
            ArrayList<Tuple2<Integer, AltAST>> list = (ArrayList<Tuple2<Integer, AltAST>>)labels.get(altLabel.getText());
            if (list == null) {
                list = new ArrayList<Tuple2<Integer, AltAST>>();
                labels.put(altLabel.getText(), list);
            }
            list.add(Tuple.create(i, this.alt[i].ast));
        }
        if (labels.isEmpty()) {
            return null;
        }
        return labels;
    }

    public List<AltAST> getUnlabeledAltASTs() {
        ArrayList<AltAST> alts = new ArrayList<AltAST>();
        for (int i = 1; i <= this.numberOfAlts; ++i) {
            GrammarAST altLabel = this.alt[i].ast.altLabel;
            if (altLabel != null) continue;
            alts.add(this.alt[i].ast);
        }
        if (alts.isEmpty()) {
            return null;
        }
        return alts;
    }

    @Override
    public Attribute resolveToAttribute(String x, ActionAST node) {
        Attribute a;
        if (this.args != null && (a = this.args.get(x)) != null) {
            return a;
        }
        if (this.retvals != null && (a = this.retvals.get(x)) != null) {
            return a;
        }
        if (this.locals != null && (a = this.locals.get(x)) != null) {
            return a;
        }
        AttributeDict properties = this.getPredefinedScope(LabelType.RULE_LABEL);
        return properties.get(x);
    }

    @Override
    public Attribute resolveToAttribute(String x, String y, ActionAST node) {
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        if (anyLabelDef != null) {
            if (anyLabelDef.type == LabelType.RULE_LABEL) {
                return this.g.getRule(anyLabelDef.element.getText()).resolveRetvalOrProperty(y);
            }
            AttributeDict scope = this.getPredefinedScope(anyLabelDef.type);
            if (scope == null) {
                return null;
            }
            return scope.get(y);
        }
        return null;
    }

    @Override
    public boolean resolvesToLabel(String x, ActionAST node) {
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        return anyLabelDef != null && (anyLabelDef.type == LabelType.RULE_LABEL || anyLabelDef.type == LabelType.TOKEN_LABEL);
    }

    @Override
    public boolean resolvesToListLabel(String x, ActionAST node) {
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        return anyLabelDef != null && (anyLabelDef.type == LabelType.RULE_LIST_LABEL || anyLabelDef.type == LabelType.TOKEN_LIST_LABEL);
    }

    @Override
    public boolean resolvesToToken(String x, ActionAST node) {
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        return anyLabelDef != null && anyLabelDef.type == LabelType.TOKEN_LABEL;
    }

    @Override
    public boolean resolvesToAttributeDict(String x, ActionAST node) {
        return this.resolvesToToken(x, node);
    }

    public Rule resolveToRule(String x) {
        if (x.equals(this.name)) {
            return this;
        }
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        if (anyLabelDef != null && anyLabelDef.type == LabelType.RULE_LABEL) {
            return this.g.getRule(anyLabelDef.element.getText());
        }
        return this.g.getRule(x);
    }

    public LabelElementPair getAnyLabelDef(String x) {
        List labels = (List)this.getElementLabelDefs().get(x);
        if (labels != null) {
            return (LabelElementPair)labels.get(0);
        }
        return null;
    }

    public AttributeDict getPredefinedScope(LabelType ltype) {
        String grammarLabelKey = this.g.getTypeString() + ":" + (Object)((Object)ltype);
        return Grammar.grammarAndLabelRefTypeToScope.get(grammarLabelKey);
    }

    public boolean isFragment() {
        if (this.modifiers == null) {
            return false;
        }
        for (GrammarAST a : this.modifiers) {
            if (!a.getText().equals("fragment")) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rule)) {
            return false;
        }
        return this.name.equals(((Rule)obj).name);
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Rule{name=").append(this.name);
        if (this.args != null) {
            buf.append(", args=").append(this.args);
        }
        if (this.retvals != null) {
            buf.append(", retvals=").append(this.retvals);
        }
        buf.append("}");
        return buf.toString();
    }

    static {
        predefinedRulePropertiesDict.add(new Attribute("parser"));
        predefinedRulePropertiesDict.add(new Attribute("text"));
        predefinedRulePropertiesDict.add(new Attribute("start"));
        predefinedRulePropertiesDict.add(new Attribute("stop"));
        predefinedRulePropertiesDict.add(new Attribute("ctx"));
        validLexerCommands = new HashSet<String>();
        validLexerCommands.add("mode");
        validLexerCommands.add("pushMode");
        validLexerCommands.add("type");
        validLexerCommands.add("channel");
        validLexerCommands.add("popMode");
        validLexerCommands.add("skip");
        validLexerCommands.add("more");
    }
}

