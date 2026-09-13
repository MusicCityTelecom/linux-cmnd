/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.debug.BlankDebugEventListener;

public class Tracer
extends BlankDebugEventListener {
    public IntStream input;
    protected int level = 0;

    public Tracer(IntStream input) {
        this.input = input;
    }

    public void enterRule(String ruleName) {
        for (int i = 1; i <= this.level; ++i) {
            System.out.print(" ");
        }
        System.out.println("> " + ruleName + " lookahead(1)=" + this.getInputSymbol(1));
        ++this.level;
    }

    public void exitRule(String ruleName) {
        --this.level;
        for (int i = 1; i <= this.level; ++i) {
            System.out.print(" ");
        }
        System.out.println("< " + ruleName + " lookahead(1)=" + this.getInputSymbol(1));
    }

    public Object getInputSymbol(int k) {
        if (this.input instanceof TokenStream) {
            return ((TokenStream)this.input).LT(k);
        }
        return Character.valueOf((char)this.input.LA(k));
    }
}

