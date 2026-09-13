/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.debug.BlankDebugEventListener;
import groovyjarjarantlr4.runtime.tree.ParseTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParseTreeBuilder
extends BlankDebugEventListener {
    public static final String EPSILON_PAYLOAD = "<epsilon>";
    Stack<ParseTree> callStack = new Stack();
    List<Token> hiddenTokens = new ArrayList<Token>();
    int backtracking = 0;

    public ParseTreeBuilder(String grammarName) {
        ParseTree root = this.create("<grammar " + grammarName + ">");
        this.callStack.push(root);
    }

    public ParseTree getTree() {
        return (ParseTree)this.callStack.elementAt(0);
    }

    public ParseTree create(Object payload) {
        return new ParseTree(payload);
    }

    public ParseTree epsilonNode() {
        return this.create(EPSILON_PAYLOAD);
    }

    public void enterDecision(int d, boolean couldBacktrack) {
        ++this.backtracking;
    }

    public void exitDecision(int i) {
        --this.backtracking;
    }

    public void enterRule(String filename, String ruleName) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree parentRuleNode = this.callStack.peek();
        ParseTree ruleNode = this.create(ruleName);
        parentRuleNode.addChild(ruleNode);
        this.callStack.push(ruleNode);
    }

    public void exitRule(String filename, String ruleName) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree ruleNode = this.callStack.peek();
        if (ruleNode.getChildCount() == 0) {
            ruleNode.addChild(this.epsilonNode());
        }
        this.callStack.pop();
    }

    public void consumeToken(Token token) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree ruleNode = this.callStack.peek();
        ParseTree elementNode = this.create(token);
        elementNode.hiddenTokens = this.hiddenTokens;
        this.hiddenTokens = new ArrayList<Token>();
        ruleNode.addChild(elementNode);
    }

    public void consumeHiddenToken(Token token) {
        if (this.backtracking > 0) {
            return;
        }
        this.hiddenTokens.add(token);
    }

    public void recognitionException(RecognitionException e) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree ruleNode = this.callStack.peek();
        ParseTree errorNode = this.create(e);
        ruleNode.addChild(errorNode);
    }
}

