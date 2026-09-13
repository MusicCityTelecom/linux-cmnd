/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.misc.MultiMap
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAnalyzer;
import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor;
import groovyjarjarantlr4.v4.parse.ScopeParser;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.stringtemplate.v4.misc.MultiMap;

public class RuleCollector
extends GrammarTreeVisitor {
    public Grammar g;
    public ErrorManager errMgr;
    public OrderedHashMap<String, Rule> rules = new OrderedHashMap();
    public MultiMap<String, GrammarAST> ruleToAltLabels = new MultiMap();
    public Map<String, String> altLabelToRuleName = new HashMap<String, String>();

    public RuleCollector(Grammar g) {
        this.g = g;
        this.errMgr = g.tool.errMgr;
    }

    @Override
    public ErrorManager getErrorManager() {
        return this.errMgr;
    }

    public void process(GrammarAST ast) {
        this.visitGrammar(ast);
    }

    @Override
    public void discoverRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, ActionAST arg, ActionAST returns, GrammarAST thrws, GrammarAST options, ActionAST locals, List<GrammarAST> actions, GrammarAST block) {
        int numAlts = block.getChildCount();
        Rule r = LeftRecursiveRuleAnalyzer.hasImmediateRecursiveRuleRefs(rule, ID.getText()) ? new LeftRecursiveRule(this.g, ID.getText(), rule) : new Rule(this.g, ID.getText(), rule, numAlts);
        this.rules.put(r.name, r);
        if (arg != null) {
            r.args = ScopeParser.parseTypedArgList(arg, arg.getText(), this.g);
            r.args.type = AttributeDict.DictType.ARG;
            r.args.ast = arg;
            arg.resolver = r.alt[this.currentOuterAltNumber];
        }
        if (returns != null) {
            r.retvals = ScopeParser.parseTypedArgList(returns, returns.getText(), this.g);
            r.retvals.type = AttributeDict.DictType.RET;
            r.retvals.ast = returns;
        }
        if (locals != null) {
            r.locals = ScopeParser.parseTypedArgList(locals, locals.getText(), this.g);
            r.locals.type = AttributeDict.DictType.LOCAL;
            r.locals.ast = locals;
        }
        for (GrammarAST a : actions) {
            ActionAST action = (ActionAST)a.getChild(1);
            r.namedActions.put(a.getChild(0).getText(), action);
            action.resolver = r;
        }
    }

    @Override
    public void discoverOuterAlt(AltAST alt) {
        if (alt.altLabel != null) {
            this.ruleToAltLabels.map((Object)this.currentRuleName, (Object)alt.altLabel);
            String altLabel = alt.altLabel.getText();
            this.altLabelToRuleName.put(Utils.capitalize(altLabel), this.currentRuleName);
            this.altLabelToRuleName.put(Utils.decapitalize(altLabel), this.currentRuleName);
        }
    }

    @Override
    public void discoverLexerRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, GrammarAST block) {
        int numAlts = block.getChildCount();
        Rule r = new Rule(this.g, ID.getText(), rule, numAlts);
        r.mode = this.currentModeName;
        if (!modifiers.isEmpty()) {
            r.modifiers = modifiers;
        }
        this.rules.put(r.name, r);
    }
}

