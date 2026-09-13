/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;

public interface DebugEventListener {
    public static final String PROTOCOL_VERSION = "2";
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public void enterRule(String var1, String var2);

    public void enterAlt(int var1);

    public void exitRule(String var1, String var2);

    public void enterSubRule(int var1);

    public void exitSubRule(int var1);

    public void enterDecision(int var1, boolean var2);

    public void exitDecision(int var1);

    public void consumeToken(Token var1);

    public void consumeHiddenToken(Token var1);

    public void LT(int var1, Token var2);

    public void mark(int var1);

    public void rewind(int var1);

    public void rewind();

    public void beginBacktrack(int var1);

    public void endBacktrack(int var1, boolean var2);

    public void location(int var1, int var2);

    public void recognitionException(RecognitionException var1);

    public void beginResync();

    public void endResync();

    public void semanticPredicate(boolean var1, String var2);

    public void commence();

    public void terminate();

    public void consumeNode(Object var1);

    public void LT(int var1, Object var2);

    public void nilNode(Object var1);

    public void errorNode(Object var1);

    public void createNode(Object var1);

    public void createNode(Object var1, Token var2);

    public void becomeRoot(Object var1, Object var2);

    public void addChild(Object var1, Object var2);

    public void setTokenBoundaries(Object var1, int var2, int var3);
}

