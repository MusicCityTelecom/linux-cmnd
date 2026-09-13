/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.xpath;

import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.Trees;
import groovyjarjarantlr4.v4.runtime.tree.xpath.XPathElement;
import java.util.Collection;

public class XPathRuleAnywhereElement
extends XPathElement {
    protected int ruleIndex;

    public XPathRuleAnywhereElement(String ruleName, int ruleIndex) {
        super(ruleName);
        this.ruleIndex = ruleIndex;
    }

    @Override
    public Collection<ParseTree> evaluate(ParseTree t) {
        return Trees.findAllRuleNodes(t, this.ruleIndex);
    }
}

