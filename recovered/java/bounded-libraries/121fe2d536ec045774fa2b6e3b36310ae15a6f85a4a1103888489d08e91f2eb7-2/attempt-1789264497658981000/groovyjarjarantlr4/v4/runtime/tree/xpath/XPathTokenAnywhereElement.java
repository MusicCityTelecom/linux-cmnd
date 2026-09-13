/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.xpath;

import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.Trees;
import groovyjarjarantlr4.v4.runtime.tree.xpath.XPathElement;
import java.util.Collection;

public class XPathTokenAnywhereElement
extends XPathElement {
    protected int tokenType;

    public XPathTokenAnywhereElement(String tokenName, int tokenType) {
        super(tokenName);
        this.tokenType = tokenType;
    }

    @Override
    public Collection<ParseTree> evaluate(ParseTree t) {
        return Trees.findAllTokenNodes(t, this.tokenType);
    }
}

