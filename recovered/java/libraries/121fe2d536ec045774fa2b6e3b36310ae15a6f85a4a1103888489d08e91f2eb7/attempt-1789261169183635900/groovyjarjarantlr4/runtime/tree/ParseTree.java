/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.BaseTree;
import groovyjarjarantlr4.runtime.tree.Tree;
import java.util.List;

public class ParseTree
extends BaseTree {
    public Object payload;
    public List<Token> hiddenTokens;

    public ParseTree(Object label) {
        this.payload = label;
    }

    public Tree dupNode() {
        return null;
    }

    public int getType() {
        return 0;
    }

    public String getText() {
        return this.toString();
    }

    public int getTokenStartIndex() {
        return 0;
    }

    public void setTokenStartIndex(int index) {
    }

    public int getTokenStopIndex() {
        return 0;
    }

    public void setTokenStopIndex(int index) {
    }

    public String toString() {
        if (this.payload instanceof Token) {
            Token t = (Token)this.payload;
            if (t.getType() == -1) {
                return "<EOF>";
            }
            return t.getText();
        }
        return this.payload.toString();
    }

    public String toStringWithHiddenTokens() {
        String nodeText;
        StringBuilder buf = new StringBuilder();
        if (this.hiddenTokens != null) {
            for (int i = 0; i < this.hiddenTokens.size(); ++i) {
                Token hidden = this.hiddenTokens.get(i);
                buf.append(hidden.getText());
            }
        }
        if (!(nodeText = this.toString()).equals("<EOF>")) {
            buf.append(nodeText);
        }
        return buf.toString();
    }

    public String toInputString() {
        StringBuffer buf = new StringBuffer();
        this._toStringLeaves(buf);
        return buf.toString();
    }

    public void _toStringLeaves(StringBuffer buf) {
        if (this.payload instanceof Token) {
            buf.append(this.toStringWithHiddenTokens());
            return;
        }
        for (int i = 0; this.children != null && i < this.children.size(); ++i) {
            ParseTree t = (ParseTree)this.children.get(i);
            t._toStringLeaves(buf);
        }
    }
}

