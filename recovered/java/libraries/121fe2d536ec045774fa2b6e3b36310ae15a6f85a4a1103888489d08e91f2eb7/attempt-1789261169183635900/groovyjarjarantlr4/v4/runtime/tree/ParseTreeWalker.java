/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.misc.IntegerStack;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeListener;
import groovyjarjarantlr4.v4.runtime.tree.RuleNode;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;
import java.util.ArrayDeque;

public class ParseTreeWalker {
    public static final ParseTreeWalker DEFAULT = new ParseTreeWalker();

    public void walk(ParseTreeListener listener, ParseTree t) {
        ArrayDeque<ParseTree> nodeStack = new ArrayDeque<ParseTree>();
        IntegerStack indexStack = new IntegerStack();
        ParseTree currentNode = t;
        int currentIndex = 0;
        block0: while (currentNode != null) {
            if (currentNode instanceof ErrorNode) {
                listener.visitErrorNode((ErrorNode)currentNode);
            } else if (currentNode instanceof TerminalNode) {
                listener.visitTerminal((TerminalNode)currentNode);
            } else {
                RuleNode r = (RuleNode)currentNode;
                this.enterRule(listener, r);
            }
            if (currentNode.getChildCount() > 0) {
                nodeStack.push(currentNode);
                indexStack.push(currentIndex);
                currentIndex = 0;
                currentNode = currentNode.getChild(0);
                continue;
            }
            do {
                if (currentNode instanceof RuleNode) {
                    this.exitRule(listener, (RuleNode)currentNode);
                }
                if (nodeStack.isEmpty()) {
                    currentNode = null;
                    currentIndex = 0;
                    continue block0;
                }
                currentNode = ((ParseTree)nodeStack.peek()).getChild(++currentIndex);
                if (currentNode != null) continue block0;
                currentNode = (ParseTree)nodeStack.pop();
                currentIndex = indexStack.pop();
            } while (currentNode != null);
        }
    }

    protected void enterRule(ParseTreeListener listener, RuleNode r) {
        ParserRuleContext ctx = (ParserRuleContext)r.getRuleContext();
        listener.enterEveryRule(ctx);
        ctx.enterRule(listener);
    }

    protected void exitRule(ParseTreeListener listener, RuleNode r) {
        ParserRuleContext ctx = (ParserRuleContext)r.getRuleContext();
        ctx.exitRule(listener);
        listener.exitEveryRule(ctx);
    }
}

