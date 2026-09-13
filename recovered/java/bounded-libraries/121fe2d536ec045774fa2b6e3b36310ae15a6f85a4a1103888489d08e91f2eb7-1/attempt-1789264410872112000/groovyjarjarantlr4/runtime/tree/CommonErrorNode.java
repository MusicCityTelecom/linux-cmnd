/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.MismatchedTokenException;
import groovyjarjarantlr4.runtime.MissingTokenException;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.UnwantedTokenException;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;

public class CommonErrorNode
extends CommonTree {
    public IntStream input;
    public Token start;
    public Token stop;
    public RecognitionException trappedException;

    public CommonErrorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
        if (stop == null || stop.getTokenIndex() < start.getTokenIndex() && stop.getType() != -1) {
            stop = start;
        }
        this.input = input;
        this.start = start;
        this.stop = stop;
        this.trappedException = e;
    }

    public boolean isNil() {
        return false;
    }

    public int getType() {
        return 0;
    }

    public String getText() {
        String badText;
        if (this.start instanceof Token) {
            int i = this.start.getTokenIndex();
            int j = this.stop.getTokenIndex();
            if (this.stop.getType() == -1) {
                j = ((TokenStream)this.input).size();
            }
            badText = ((TokenStream)this.input).toString(i, j);
        } else {
            badText = this.start instanceof Tree ? ((TreeNodeStream)this.input).toString(this.start, this.stop) : "<unknown>";
        }
        return badText;
    }

    public String toString() {
        if (this.trappedException instanceof MissingTokenException) {
            return "<missing type: " + ((MissingTokenException)this.trappedException).getMissingType() + ">";
        }
        if (this.trappedException instanceof UnwantedTokenException) {
            return "<extraneous: " + ((UnwantedTokenException)this.trappedException).getUnexpectedToken() + ", resync=" + this.getText() + ">";
        }
        if (this.trappedException instanceof MismatchedTokenException) {
            return "<mismatched token: " + this.trappedException.token + ", resync=" + this.getText() + ">";
        }
        if (this.trappedException instanceof NoViableAltException) {
            return "<unexpected: " + this.trappedException.token + ", resync=" + this.getText() + ">";
        }
        return "<error: " + this.getText() + ">";
    }
}

