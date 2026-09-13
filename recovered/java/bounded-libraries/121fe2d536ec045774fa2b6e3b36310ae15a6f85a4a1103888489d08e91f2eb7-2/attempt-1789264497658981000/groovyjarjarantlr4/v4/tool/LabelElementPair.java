/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LabelType;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class LabelElementPair {
    public static final BitSet tokenTypeForTokens = new BitSet();
    public GrammarAST label;
    public GrammarAST element;
    public LabelType type;

    public LabelElementPair(Grammar g, GrammarAST label, GrammarAST element, int labelOp) {
        this.label = label;
        this.element = element;
        if (element.getFirstDescendantWithType(tokenTypeForTokens) != null) {
            this.type = labelOp == 10 ? LabelType.TOKEN_LABEL : LabelType.TOKEN_LIST_LABEL;
        } else if (element.getFirstDescendantWithType(57) != null) {
            this.type = labelOp == 10 ? LabelType.RULE_LABEL : LabelType.RULE_LIST_LABEL;
        }
        if (g.isLexer() && element.getFirstDescendantWithType(62) != null && labelOp == 10) {
            this.type = LabelType.LEXER_STRING_LABEL;
        }
    }

    public String toString() {
        return this.label.getText() + " " + (Object)((Object)this.type) + " " + this.element.toString();
    }

    static {
        tokenTypeForTokens.add(66);
        tokenTypeForTokens.add(62);
        tokenTypeForTokens.add(100);
    }
}

