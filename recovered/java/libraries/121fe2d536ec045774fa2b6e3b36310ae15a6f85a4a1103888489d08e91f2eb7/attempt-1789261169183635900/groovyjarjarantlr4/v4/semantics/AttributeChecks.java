/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.parse.ActionSplitter;
import groovyjarjarantlr4.v4.parse.ActionSplitterListener;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LabelElementPair;
import groovyjarjarantlr4.v4.tool.LabelType;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class AttributeChecks
implements ActionSplitterListener {
    public Grammar g;
    public Rule r;
    public Alternative alt;
    public ActionAST node;
    public Token actionToken;
    public ErrorManager errMgr;

    public AttributeChecks(Grammar g, Rule r, Alternative alt, ActionAST node, Token actionToken) {
        this.g = g;
        this.r = r;
        this.alt = alt;
        this.node = node;
        this.actionToken = actionToken;
        this.errMgr = g.tool.errMgr;
    }

    public static void checkAllAttributeExpressions(Grammar g) {
        AttributeChecks checker;
        for (ActionAST act : g.namedActions.values()) {
            checker = new AttributeChecks(g, null, null, act, act.token);
            checker.examineAction();
        }
        for (Rule r : g.rules.values()) {
            for (ActionAST a : r.namedActions.values()) {
                AttributeChecks checker2 = new AttributeChecks(g, r, null, a, a.token);
                checker2.examineAction();
            }
            for (int i = 1; i <= r.numberOfAlts; ++i) {
                Alternative alt = r.alt[i];
                for (ActionAST a : alt.actions) {
                    AttributeChecks checker3 = new AttributeChecks(g, r, alt, a, a.token);
                    checker3.examineAction();
                }
            }
            for (GrammarAST e : r.exceptions) {
                ActionAST a = (ActionAST)e.getChild(1);
                AttributeChecks checker4 = new AttributeChecks(g, r, null, a, a.token);
                checker4.examineAction();
            }
            if (r.finallyAction == null) continue;
            checker = new AttributeChecks(g, r, null, r.finallyAction, r.finallyAction.token);
            checker.examineAction();
        }
    }

    public void examineAction() {
        ANTLRStringStream in = new ANTLRStringStream(this.actionToken.getText());
        in.setLine(this.actionToken.getLine());
        in.setCharPositionInLine(this.actionToken.getCharPositionInLine());
        ActionSplitter splitter = new ActionSplitter((CharStream)in, this);
        this.node.chunks = splitter.getActionTokens();
    }

    @Override
    public void qualifiedAttr(String expr, Token x, Token y) {
        if (this.g.isLexer()) {
            this.errMgr.grammarError(ErrorType.ATTRIBUTE_IN_LEXER_ACTION, this.g.fileName, x, x.getText() + "." + y.getText(), expr);
            return;
        }
        if (this.node.resolver.resolveToAttribute(x.getText(), this.node) != null) {
            this.attr(expr, x);
            return;
        }
        if (this.node.resolver.resolveToAttribute(x.getText(), y.getText(), this.node) == null) {
            Rule rref = this.isolatedRuleRef(x.getText());
            if (rref != null) {
                if (rref.args != null && rref.args.get(y.getText()) != null) {
                    this.g.tool.errMgr.grammarError(ErrorType.INVALID_RULE_PARAMETER_REF, this.g.fileName, y, y.getText(), rref.name, expr);
                } else {
                    this.errMgr.grammarError(ErrorType.UNKNOWN_RULE_ATTRIBUTE, this.g.fileName, y, y.getText(), rref.name, expr);
                }
            } else if (!this.node.resolver.resolvesToAttributeDict(x.getText(), this.node)) {
                this.errMgr.grammarError(ErrorType.UNKNOWN_SIMPLE_ATTRIBUTE, this.g.fileName, x, x.getText(), expr);
            } else {
                this.errMgr.grammarError(ErrorType.UNKNOWN_ATTRIBUTE_IN_SCOPE, this.g.fileName, y, y.getText(), expr);
            }
        }
    }

    @Override
    public void setAttr(String expr, Token x, Token rhs) {
        if (this.g.isLexer()) {
            this.errMgr.grammarError(ErrorType.ATTRIBUTE_IN_LEXER_ACTION, this.g.fileName, x, x.getText(), expr);
            return;
        }
        if (this.node.resolver.resolveToAttribute(x.getText(), this.node) == null) {
            ErrorType errorType = ErrorType.UNKNOWN_SIMPLE_ATTRIBUTE;
            if (this.node.resolver.resolvesToListLabel(x.getText(), this.node)) {
                errorType = ErrorType.ASSIGNMENT_TO_LIST_LABEL;
            }
            this.errMgr.grammarError(errorType, this.g.fileName, x, x.getText(), expr);
        }
        new AttributeChecks(this.g, this.r, this.alt, this.node, rhs).examineAction();
    }

    @Override
    public void attr(String expr, Token x) {
        if (this.g.isLexer()) {
            this.errMgr.grammarError(ErrorType.ATTRIBUTE_IN_LEXER_ACTION, this.g.fileName, x, x.getText(), expr);
            return;
        }
        if (this.node.resolver.resolveToAttribute(x.getText(), this.node) == null) {
            if (this.node.resolver.resolvesToToken(x.getText(), this.node)) {
                return;
            }
            if (this.node.resolver.resolvesToListLabel(x.getText(), this.node)) {
                return;
            }
            if (this.isolatedRuleRef(x.getText()) != null) {
                this.errMgr.grammarError(ErrorType.ISOLATED_RULE_REF, this.g.fileName, x, x.getText(), expr);
                return;
            }
            this.errMgr.grammarError(ErrorType.UNKNOWN_SIMPLE_ATTRIBUTE, this.g.fileName, x, x.getText(), expr);
        }
    }

    @Override
    public void nonLocalAttr(String expr, Token x, Token y) {
        Rule r = this.g.getRule(x.getText());
        if (r == null) {
            this.errMgr.grammarError(ErrorType.UNDEFINED_RULE_IN_NONLOCAL_REF, this.g.fileName, x, x.getText(), y.getText(), expr);
        } else if (r.resolveToAttribute(y.getText(), null) == null) {
            this.errMgr.grammarError(ErrorType.UNKNOWN_RULE_ATTRIBUTE, this.g.fileName, y, y.getText(), x.getText(), expr);
        }
    }

    @Override
    public void setNonLocalAttr(String expr, Token x, Token y, Token rhs) {
        Rule r = this.g.getRule(x.getText());
        if (r == null) {
            this.errMgr.grammarError(ErrorType.UNDEFINED_RULE_IN_NONLOCAL_REF, this.g.fileName, x, x.getText(), y.getText(), expr);
        } else if (r.resolveToAttribute(y.getText(), null) == null) {
            this.errMgr.grammarError(ErrorType.UNKNOWN_RULE_ATTRIBUTE, this.g.fileName, y, y.getText(), x.getText(), expr);
        }
    }

    @Override
    public void text(String text) {
    }

    public void templateInstance(String expr) {
    }

    public void indirectTemplateInstance(String expr) {
    }

    public void setExprAttribute(String expr) {
    }

    public void setSTAttribute(String expr) {
    }

    public void templateExpr(String expr) {
    }

    public Rule isolatedRuleRef(String x) {
        if (this.node.resolver instanceof Grammar) {
            return null;
        }
        if (x.equals(this.r.name)) {
            return this.r;
        }
        List labels = null;
        if (this.node.resolver instanceof Rule) {
            labels = (List)this.r.getElementLabelDefs().get(x);
        } else if (this.node.resolver instanceof Alternative) {
            labels = (List)((Alternative)this.node.resolver).labelDefs.get(x);
        }
        if (labels != null) {
            LabelElementPair anyLabelDef = (LabelElementPair)labels.get(0);
            if (anyLabelDef.type == LabelType.RULE_LABEL) {
                return this.g.getRule(anyLabelDef.element.getText());
            }
        }
        if (this.node.resolver instanceof Alternative && ((Alternative)this.node.resolver).ruleRefs.get(x) != null) {
            return this.g.getRule(x);
        }
        return null;
    }
}

