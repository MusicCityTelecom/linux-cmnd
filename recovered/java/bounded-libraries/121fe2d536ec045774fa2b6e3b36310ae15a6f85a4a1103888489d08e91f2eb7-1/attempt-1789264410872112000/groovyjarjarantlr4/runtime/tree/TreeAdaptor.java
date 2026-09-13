/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;

public interface TreeAdaptor {
    public Object create(Token var1);

    public Object dupNode(Object var1);

    public Object dupTree(Object var1);

    public Object nil();

    public Object errorNode(TokenStream var1, Token var2, Token var3, RecognitionException var4);

    public boolean isNil(Object var1);

    public void addChild(Object var1, Object var2);

    public Object becomeRoot(Object var1, Object var2);

    public Object rulePostProcessing(Object var1);

    public int getUniqueID(Object var1);

    public Object becomeRoot(Token var1, Object var2);

    public Object create(int var1, Token var2);

    public Object create(int var1, Token var2, String var3);

    public Object create(int var1, String var2);

    public int getType(Object var1);

    public void setType(Object var1, int var2);

    public String getText(Object var1);

    public void setText(Object var1, String var2);

    public Token getToken(Object var1);

    public void setTokenBoundaries(Object var1, Token var2, Token var3);

    public int getTokenStartIndex(Object var1);

    public int getTokenStopIndex(Object var1);

    public Object getChild(Object var1, int var2);

    public void setChild(Object var1, int var2, Object var3);

    public Object deleteChild(Object var1, int var2);

    public int getChildCount(Object var1);

    public Object getParent(Object var1);

    public void setParent(Object var1, Object var2);

    public int getChildIndex(Object var1);

    public void setChildIndex(Object var1, int var2);

    public void replaceChildren(Object var1, int var2, int var3, Object var4);
}

