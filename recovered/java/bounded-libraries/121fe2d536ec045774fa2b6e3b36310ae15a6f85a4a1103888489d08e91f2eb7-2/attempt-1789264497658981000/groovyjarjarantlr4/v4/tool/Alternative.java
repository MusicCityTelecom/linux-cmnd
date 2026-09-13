/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.runtime.misc.MultiMap;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.AttributeResolver;
import groovyjarjarantlr4.v4.tool.LabelElementPair;
import groovyjarjarantlr4.v4.tool.LabelType;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;
import java.util.List;

public class Alternative
implements AttributeResolver {
    public Rule rule;
    public AltAST ast;
    public int altNum;
    public MultiMap<String, TerminalAST> tokenRefs = new MultiMap();
    public MultiMap<String, GrammarAST> tokenRefsInActions = new MultiMap();
    public MultiMap<String, GrammarAST> ruleRefs = new MultiMap();
    public MultiMap<String, GrammarAST> ruleRefsInActions = new MultiMap();
    public MultiMap<String, LabelElementPair> labelDefs = new MultiMap();
    public List<ActionAST> actions = new ArrayList<ActionAST>();

    public Alternative(Rule r, int altNum) {
        this.rule = r;
        this.altNum = altNum;
    }

    @Override
    public boolean resolvesToToken(String x, ActionAST node) {
        if (this.tokenRefs.get(x) != null) {
            return true;
        }
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        return anyLabelDef != null && anyLabelDef.type == LabelType.TOKEN_LABEL;
    }

    @Override
    public boolean resolvesToAttributeDict(String x, ActionAST node) {
        if (this.resolvesToToken(x, node)) {
            return true;
        }
        if (this.ruleRefs.get(x) != null) {
            return true;
        }
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        return anyLabelDef != null && anyLabelDef.type == LabelType.RULE_LABEL;
    }

    @Override
    public Attribute resolveToAttribute(String x, ActionAST node) {
        return this.rule.resolveToAttribute(x, node);
    }

    @Override
    public Attribute resolveToAttribute(String x, String y, ActionAST node) {
        if (this.tokenRefs.get(x) != null) {
            return this.rule.getPredefinedScope(LabelType.TOKEN_LABEL).get(y);
        }
        if (this.ruleRefs.get(x) != null) {
            return this.rule.g.getRule(x).resolveRetvalOrProperty(y);
        }
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        if (anyLabelDef != null && anyLabelDef.type == LabelType.RULE_LABEL) {
            return this.rule.g.getRule(anyLabelDef.element.getText()).resolveRetvalOrProperty(y);
        }
        if (anyLabelDef != null) {
            AttributeDict scope = this.rule.getPredefinedScope(anyLabelDef.type);
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
        return anyLabelDef != null && (anyLabelDef.type == LabelType.TOKEN_LABEL || anyLabelDef.type == LabelType.RULE_LABEL);
    }

    @Override
    public boolean resolvesToListLabel(String x, ActionAST node) {
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        return anyLabelDef != null && (anyLabelDef.type == LabelType.RULE_LIST_LABEL || anyLabelDef.type == LabelType.TOKEN_LIST_LABEL);
    }

    public LabelElementPair getAnyLabelDef(String x) {
        List labels = (List)this.labelDefs.get(x);
        if (labels != null) {
            return (LabelElementPair)labels.get(0);
        }
        return null;
    }

    public Rule resolveToRule(String x) {
        if (this.ruleRefs.get(x) != null) {
            return this.rule.g.getRule(x);
        }
        LabelElementPair anyLabelDef = this.getAnyLabelDef(x);
        if (anyLabelDef != null && anyLabelDef.type == LabelType.RULE_LABEL) {
            return this.rule.g.getRule(anyLabelDef.element.getText());
        }
        return null;
    }
}

