/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.parse.ActionSplitter;
import groovyjarjarantlr4.v4.semantics.BlankActionSplitterListener;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import java.util.List;

public class ActionSniffer
extends BlankActionSplitterListener {
    public Grammar g;
    public Rule r;
    public Alternative alt;
    public ActionAST node;
    public Token actionToken;
    public ErrorManager errMgr;

    public ActionSniffer(Grammar g, Rule r, Alternative alt, ActionAST node, Token actionToken) {
        this.g = g;
        this.r = r;
        this.alt = alt;
        this.node = node;
        this.actionToken = actionToken;
        this.errMgr = g.tool.errMgr;
    }

    public void examineAction() {
        ANTLRStringStream in = new ANTLRStringStream(this.actionToken.getText());
        in.setLine(this.actionToken.getLine());
        in.setCharPositionInLine(this.actionToken.getCharPositionInLine());
        ActionSplitter splitter = new ActionSplitter((CharStream)in, this);
        this.node.chunks = splitter.getActionTokens();
    }

    public void processNested(Token actionToken) {
        ANTLRStringStream in = new ANTLRStringStream(actionToken.getText());
        in.setLine(actionToken.getLine());
        in.setCharPositionInLine(actionToken.getCharPositionInLine());
        ActionSplitter splitter = new ActionSplitter((CharStream)in, this);
        splitter.getActionTokens();
    }

    @Override
    public void attr(String expr, Token x) {
        this.trackRef(x);
    }

    @Override
    public void qualifiedAttr(String expr, Token x, Token y) {
        this.trackRef(x);
    }

    @Override
    public void setAttr(String expr, Token x, Token rhs) {
        this.trackRef(x);
        this.processNested(rhs);
    }

    @Override
    public void setNonLocalAttr(String expr, Token x, Token y, Token rhs) {
        this.processNested(rhs);
    }

    public void trackRef(Token x) {
        List rRefs;
        List xRefs = (List)this.alt.tokenRefs.get(x.getText());
        if (xRefs != null) {
            this.alt.tokenRefsInActions.map(x.getText(), this.node);
        }
        if ((rRefs = (List)this.alt.ruleRefs.get(x.getText())) != null) {
            this.alt.ruleRefsInActions.map(x.getText(), this.node);
        }
    }
}

