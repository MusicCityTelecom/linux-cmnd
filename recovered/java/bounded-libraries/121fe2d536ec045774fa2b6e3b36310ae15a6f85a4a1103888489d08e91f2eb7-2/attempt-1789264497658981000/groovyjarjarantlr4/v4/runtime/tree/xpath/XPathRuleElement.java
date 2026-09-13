/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.xpath;

import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.runtime.tree.Trees;
import groovyjarjarantlr4.v4.runtime.tree.xpath.XPathElement;
import java.util.ArrayList;
import java.util.Collection;

public class XPathRuleElement
extends XPathElement {
    protected int ruleIndex;

    public XPathRuleElement(String ruleName, int ruleIndex) {
        super(ruleName);
        this.ruleIndex = ruleIndex;
    }

    @Override
    public Collection<ParseTree> evaluate(ParseTree t) {
        ArrayList<ParseTree> nodes = new ArrayList<ParseTree>();
        for (Tree c : Trees.getChildren(t)) {
            ParserRuleContext ctx;
            if (!(c instanceof ParserRuleContext) || ((ctx = (ParserRuleContext)c).getRuleIndex() != this.ruleIndex || this.invert) && (ctx.getRuleIndex() == this.ruleIndex || !this.invert)) continue;
            nodes.add(ctx);
        }
        return nodes;
    }
}

