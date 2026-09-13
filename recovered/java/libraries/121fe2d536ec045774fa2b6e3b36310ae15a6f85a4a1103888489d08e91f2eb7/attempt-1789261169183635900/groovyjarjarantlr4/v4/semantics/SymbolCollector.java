/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LabelElementPair;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SymbolCollector
extends GrammarTreeVisitor {
    public Grammar g;
    public List<GrammarAST> rulerefs = new ArrayList<GrammarAST>();
    public List<GrammarAST> qualifiedRulerefs = new ArrayList<GrammarAST>();
    public List<GrammarAST> terminals = new ArrayList<GrammarAST>();
    public List<GrammarAST> tokenIDRefs = new ArrayList<GrammarAST>();
    public Set<String> strings = new HashSet<String>();
    public List<GrammarAST> tokensDefs = new ArrayList<GrammarAST>();
    public List<GrammarAST> channelDefs = new ArrayList<GrammarAST>();
    List<GrammarAST> namedActions = new ArrayList<GrammarAST>();
    public ErrorManager errMgr;
    public Rule currentRule;

    public SymbolCollector(Grammar g) {
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
    public void globalNamedAction(GrammarAST scope, GrammarAST ID, ActionAST action) {
        this.namedActions.add((GrammarAST)ID.getParent());
        action.resolver = this.g;
    }

    @Override
    public void defineToken(GrammarAST ID) {
        this.terminals.add(ID);
        this.tokenIDRefs.add(ID);
        this.tokensDefs.add(ID);
    }

    @Override
    public void defineChannel(GrammarAST ID) {
        this.channelDefs.add(ID);
    }

    @Override
    public void discoverRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, ActionAST arg, ActionAST returns, GrammarAST thrws, GrammarAST options, ActionAST locals, List<GrammarAST> actions, GrammarAST block) {
        this.currentRule = this.g.getRule(ID.getText());
    }

    @Override
    public void discoverLexerRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, GrammarAST block) {
        this.currentRule = this.g.getRule(ID.getText());
    }

    @Override
    public void discoverOuterAlt(AltAST alt) {
        this.currentRule.alt[this.currentOuterAltNumber].ast = alt;
    }

    @Override
    public void actionInAlt(ActionAST action) {
        this.currentRule.defineActionInAlt(this.currentOuterAltNumber, action);
        action.resolver = this.currentRule.alt[this.currentOuterAltNumber];
    }

    @Override
    public void sempredInAlt(PredAST pred) {
        this.currentRule.definePredicateInAlt(this.currentOuterAltNumber, pred);
        pred.resolver = this.currentRule.alt[this.currentOuterAltNumber];
    }

    @Override
    public void ruleCatch(GrammarAST arg, ActionAST action) {
        GrammarAST catchme = (GrammarAST)action.getParent();
        this.currentRule.exceptions.add(catchme);
        action.resolver = this.currentRule;
    }

    @Override
    public void finallyAction(ActionAST action) {
        this.currentRule.finallyAction = action;
        action.resolver = this.currentRule;
    }

    @Override
    public void label(GrammarAST op, GrammarAST ID, GrammarAST element) {
        LabelElementPair lp = new LabelElementPair(this.g, ID, element, op.getType());
        this.currentRule.alt[this.currentOuterAltNumber].labelDefs.map(ID.getText(), lp);
    }

    @Override
    public void stringRef(TerminalAST ref) {
        this.terminals.add(ref);
        this.strings.add(ref.getText());
        if (this.currentRule != null) {
            this.currentRule.alt[this.currentOuterAltNumber].tokenRefs.map(ref.getText(), ref);
        }
    }

    @Override
    public void tokenRef(TerminalAST ref) {
        this.terminals.add(ref);
        this.tokenIDRefs.add(ref);
        if (this.currentRule != null) {
            this.currentRule.alt[this.currentOuterAltNumber].tokenRefs.map(ref.getText(), ref);
        }
    }

    @Override
    public void ruleRef(GrammarAST ref, ActionAST arg) {
        this.rulerefs.add(ref);
        if (this.currentRule != null) {
            this.currentRule.alt[this.currentOuterAltNumber].ruleRefs.map(ref.getText(), ref);
        }
    }

    @Override
    public void grammarOption(GrammarAST ID, GrammarAST valueAST) {
        this.setActionResolver(valueAST);
    }

    @Override
    public void ruleOption(GrammarAST ID, GrammarAST valueAST) {
        this.setActionResolver(valueAST);
    }

    @Override
    public void blockOption(GrammarAST ID, GrammarAST valueAST) {
        this.setActionResolver(valueAST);
    }

    @Override
    public void elementOption(GrammarASTWithOptions t, GrammarAST ID, GrammarAST valueAST) {
        this.setActionResolver(valueAST);
    }

    private void setActionResolver(GrammarAST valueAST) {
        if (valueAST instanceof ActionAST) {
            ((ActionAST)valueAST).resolver = this.currentRule.alt[this.currentOuterAltNumber];
        }
    }
}

