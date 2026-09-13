/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.BaseTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.Tree;

public class CommonTreeAdaptor
extends BaseTreeAdaptor {
    public Object dupNode(Object t) {
        if (t == null) {
            return null;
        }
        return ((Tree)t).dupNode();
    }

    public Object create(Token payload) {
        return new CommonTree(payload);
    }

    public Token createToken(int tokenType, String text) {
        return new CommonToken(tokenType, text);
    }

    public Token createToken(Token fromToken) {
        return new CommonToken(fromToken);
    }

    public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
        if (t == null) {
            return;
        }
        int start = 0;
        int stop = 0;
        if (startToken != null) {
            start = startToken.getTokenIndex();
        }
        if (stopToken != null) {
            stop = stopToken.getTokenIndex();
        }
        ((Tree)t).setTokenStartIndex(start);
        ((Tree)t).setTokenStopIndex(stop);
    }

    public int getTokenStartIndex(Object t) {
        if (t == null) {
            return -1;
        }
        return ((Tree)t).getTokenStartIndex();
    }

    public int getTokenStopIndex(Object t) {
        if (t == null) {
            return -1;
        }
        return ((Tree)t).getTokenStopIndex();
    }

    public String getText(Object t) {
        if (t == null) {
            return null;
        }
        return ((Tree)t).getText();
    }

    public int getType(Object t) {
        if (t == null) {
            return 0;
        }
        return ((Tree)t).getType();
    }

    public Token getToken(Object t) {
        if (t instanceof CommonTree) {
            return ((CommonTree)t).getToken();
        }
        return null;
    }

    public Object getChild(Object t, int i) {
        if (t == null) {
            return null;
        }
        return ((Tree)t).getChild(i);
    }

    public int getChildCount(Object t) {
        if (t == null) {
            return 0;
        }
        return ((Tree)t).getChildCount();
    }

    public Object getParent(Object t) {
        if (t == null) {
            return null;
        }
        return ((Tree)t).getParent();
    }

    public void setParent(Object t, Object parent) {
        if (t != null) {
            ((Tree)t).setParent((Tree)parent);
        }
    }

    public int getChildIndex(Object t) {
        if (t == null) {
            return 0;
        }
        return ((Tree)t).getChildIndex();
    }

    public void setChildIndex(Object t, int index) {
        if (t != null) {
            ((Tree)t).setChildIndex(index);
        }
    }

    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
        if (parent != null) {
            ((Tree)parent).replaceChildren(startChildIndex, stopChildIndex, t);
        }
    }
}

