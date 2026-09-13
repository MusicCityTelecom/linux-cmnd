/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import groovyjarjarantlr4.v4.runtime.tree.RuleNode;
import groovyjarjarantlr4.v4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.runtime.tree.Trees;
import java.util.Arrays;
import java.util.List;

public class RuleContext
implements RuleNode {
    public RuleContext parent;
    public int invokingState = -1;

    public RuleContext() {
    }

    public RuleContext(RuleContext parent, int invokingState) {
        this.parent = parent;
        this.invokingState = invokingState;
    }

    public static RuleContext getChildContext(RuleContext parent, int invokingState) {
        return new RuleContext(parent, invokingState);
    }

    public int depth() {
        int n = 0;
        RuleContext p = this;
        while (p != null) {
            p = p.parent;
            ++n;
        }
        return n;
    }

    public boolean isEmpty() {
        return this.invokingState == -1;
    }

    @Override
    public Interval getSourceInterval() {
        return Interval.INVALID;
    }

    @Override
    public RuleContext getRuleContext() {
        return this;
    }

    @Override
    public RuleContext getParent() {
        return this.parent;
    }

    @Override
    public RuleContext getPayload() {
        return this;
    }

    @Override
    public String getText() {
        if (this.getChildCount() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.getChildCount(); ++i) {
            builder.append(this.getChild(i).getText());
        }
        return builder.toString();
    }

    public int getRuleIndex() {
        return -1;
    }

    public int getAltNumber() {
        return 0;
    }

    public void setAltNumber(int altNumber) {
    }

    public void setParent(RuleContext parent) {
        this.parent = parent;
    }

    @Override
    public ParseTree getChild(int i) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
        return visitor.visitChildren(this);
    }

    @Override
    public String toStringTree(@Nullable Parser recog) {
        return Trees.toStringTree((Tree)this, recog);
    }

    public String toStringTree(@Nullable List<String> ruleNames) {
        return Trees.toStringTree((Tree)this, ruleNames);
    }

    @Override
    public String toStringTree() {
        return this.toStringTree((List<String>)null);
    }

    public String toString() {
        return this.toString((List<String>)null, (RuleContext)null);
    }

    public final String toString(@Nullable Recognizer<?, ?> recog) {
        return this.toString(recog, (RuleContext)ParserRuleContext.emptyContext());
    }

    public final String toString(@Nullable List<String> ruleNames) {
        return this.toString(ruleNames, null);
    }

    public String toString(@Nullable Recognizer<?, ?> recog, @Nullable RuleContext stop) {
        String[] ruleNames = recog != null ? recog.getRuleNames() : null;
        List<String> ruleNamesList = ruleNames != null ? Arrays.asList(ruleNames) : null;
        return this.toString(ruleNamesList, stop);
    }

    public String toString(@Nullable List<String> ruleNames, @Nullable RuleContext stop) {
        StringBuilder buf = new StringBuilder();
        RuleContext p = this;
        buf.append("[");
        while (p != null && p != stop) {
            if (ruleNames == null) {
                if (!p.isEmpty()) {
                    buf.append(p.invokingState);
                }
            } else {
                int ruleIndex = p.getRuleIndex();
                String ruleName = ruleIndex >= 0 && ruleIndex < ruleNames.size() ? ruleNames.get(ruleIndex) : Integer.toString(ruleIndex);
                buf.append(ruleName);
            }
            if (!(p.parent == null || ruleNames == null && p.parent.isEmpty())) {
                buf.append(" ");
            }
            p = p.parent;
        }
        buf.append("]");
        return buf.toString();
    }
}

