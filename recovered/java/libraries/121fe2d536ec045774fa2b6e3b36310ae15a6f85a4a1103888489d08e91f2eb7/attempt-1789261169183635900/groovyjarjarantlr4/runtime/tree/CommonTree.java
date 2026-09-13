/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.BaseTree;
import groovyjarjarantlr4.runtime.tree.Tree;

public class CommonTree
extends BaseTree {
    public Token token;
    protected int startIndex = -1;
    protected int stopIndex = -1;
    public CommonTree parent;
    public int childIndex = -1;

    public CommonTree() {
    }

    public CommonTree(CommonTree node) {
        super(node);
        this.token = node.token;
        this.startIndex = node.startIndex;
        this.stopIndex = node.stopIndex;
    }

    public CommonTree(Token t) {
        this.token = t;
    }

    public Token getToken() {
        return this.token;
    }

    public Tree dupNode() {
        return new CommonTree(this);
    }

    public boolean isNil() {
        return this.token == null;
    }

    public int getType() {
        if (this.token == null) {
            return 0;
        }
        return this.token.getType();
    }

    public String getText() {
        if (this.token == null) {
            return null;
        }
        return this.token.getText();
    }

    public int getLine() {
        if (this.token == null || this.token.getLine() == 0) {
            if (this.getChildCount() > 0) {
                return this.getChild(0).getLine();
            }
            return 0;
        }
        return this.token.getLine();
    }

    public int getCharPositionInLine() {
        if (this.token == null || this.token.getCharPositionInLine() == -1) {
            if (this.getChildCount() > 0) {
                return this.getChild(0).getCharPositionInLine();
            }
            return 0;
        }
        return this.token.getCharPositionInLine();
    }

    public int getTokenStartIndex() {
        if (this.startIndex == -1 && this.token != null) {
            return this.token.getTokenIndex();
        }
        return this.startIndex;
    }

    public void setTokenStartIndex(int index) {
        this.startIndex = index;
    }

    public int getTokenStopIndex() {
        if (this.stopIndex == -1 && this.token != null) {
            return this.token.getTokenIndex();
        }
        return this.stopIndex;
    }

    public void setTokenStopIndex(int index) {
        this.stopIndex = index;
    }

    public void setUnknownTokenBoundaries() {
        if (this.children == null) {
            if (this.startIndex < 0 || this.stopIndex < 0) {
                this.startIndex = this.stopIndex = this.token.getTokenIndex();
            }
            return;
        }
        for (int i = 0; i < this.children.size(); ++i) {
            ((CommonTree)this.children.get(i)).setUnknownTokenBoundaries();
        }
        if (this.startIndex >= 0 && this.stopIndex >= 0) {
            return;
        }
        if (this.children.size() > 0) {
            CommonTree firstChild = (CommonTree)this.children.get(0);
            CommonTree lastChild = (CommonTree)this.children.get(this.children.size() - 1);
            this.startIndex = firstChild.getTokenStartIndex();
            this.stopIndex = lastChild.getTokenStopIndex();
        }
    }

    public int getChildIndex() {
        return this.childIndex;
    }

    public Tree getParent() {
        return this.parent;
    }

    public void setParent(Tree t) {
        this.parent = (CommonTree)t;
    }

    public void setChildIndex(int index) {
        this.childIndex = index;
    }

    public String toString() {
        if (this.isNil()) {
            return "nil";
        }
        if (this.getType() == 0) {
            return "<errornode>";
        }
        if (this.token == null) {
            return null;
        }
        return this.token.getText();
    }
}

