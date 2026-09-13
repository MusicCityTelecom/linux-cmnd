/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.Grammar;

public class GrammarToken
extends CommonToken {
    public Grammar g;
    public int originalTokenIndex = -1;

    public GrammarToken(Grammar g, Token oldToken) {
        super(oldToken);
        this.g = g;
    }

    @Override
    public int getCharPositionInLine() {
        if (this.originalTokenIndex >= 0) {
            return this.g.originalTokenStream.get(this.originalTokenIndex).getCharPositionInLine();
        }
        return super.getCharPositionInLine();
    }

    @Override
    public int getLine() {
        if (this.originalTokenIndex >= 0) {
            return this.g.originalTokenStream.get(this.originalTokenIndex).getLine();
        }
        return super.getLine();
    }

    @Override
    public int getTokenIndex() {
        return this.originalTokenIndex;
    }

    @Override
    public int getStartIndex() {
        if (this.originalTokenIndex >= 0) {
            return ((CommonToken)this.g.originalTokenStream.get(this.originalTokenIndex)).getStartIndex();
        }
        return super.getStartIndex();
    }

    @Override
    public int getStopIndex() {
        int n = super.getStopIndex() - super.getStartIndex() + 1;
        return this.getStartIndex() + n - 1;
    }

    @Override
    public String toString() {
        String txt;
        String channelStr = "";
        if (this.channel > 0) {
            channelStr = ",channel=" + this.channel;
        }
        if ((txt = this.getText()) != null) {
            txt = txt.replaceAll("\n", "\\\\n");
            txt = txt.replaceAll("\r", "\\\\r");
            txt = txt.replaceAll("\t", "\\\\t");
        } else {
            txt = "<no text>";
        }
        return "[@" + this.getTokenIndex() + "," + this.getStartIndex() + ":" + this.getStopIndex() + "='" + txt + "',<" + this.getType() + ">" + channelStr + "," + this.getLine() + ":" + this.getCharPositionInLine() + "]";
    }
}

