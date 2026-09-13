/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.Token;

public interface ActionSplitterListener {
    public void qualifiedAttr(String var1, Token var2, Token var3);

    public void setAttr(String var1, Token var2, Token var3);

    public void attr(String var1, Token var2);

    public void setNonLocalAttr(String var1, Token var2, Token var3, Token var4);

    public void nonLocalAttr(String var1, Token var2, Token var3);

    public void text(String var1);
}

