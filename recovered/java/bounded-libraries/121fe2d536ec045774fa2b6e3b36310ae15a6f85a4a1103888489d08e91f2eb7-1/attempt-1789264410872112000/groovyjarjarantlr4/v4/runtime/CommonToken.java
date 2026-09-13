/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenSource;
import groovyjarjarantlr4.v4.runtime.WritableToken;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.io.Serializable;

public class CommonToken
implements WritableToken,
Serializable {
    private static final long serialVersionUID = -6708843461296520577L;
    protected static final Tuple2<TokenSource, CharStream> EMPTY_SOURCE = Tuple.create(null, null);
    protected int type;
    protected int line;
    protected int charPositionInLine = -1;
    protected int channel = 0;
    @NotNull
    protected Tuple2<? extends TokenSource, CharStream> source;
    protected String text;
    protected int index = -1;
    protected int start;
    protected int stop;

    public CommonToken(int type) {
        this.type = type;
        this.source = EMPTY_SOURCE;
    }

    public CommonToken(@NotNull Tuple2<? extends TokenSource, CharStream> source, int type, int channel, int start, int stop) {
        this.source = source;
        this.type = type;
        this.channel = channel;
        this.start = start;
        this.stop = stop;
        if (source.getItem1() != null) {
            this.line = source.getItem1().getLine();
            this.charPositionInLine = source.getItem1().getCharPositionInLine();
        }
    }

    public CommonToken(int type, String text) {
        this.type = type;
        this.channel = 0;
        this.text = text;
        this.source = EMPTY_SOURCE;
    }

    public CommonToken(@NotNull Token oldToken) {
        this.type = oldToken.getType();
        this.line = oldToken.getLine();
        this.index = oldToken.getTokenIndex();
        this.charPositionInLine = oldToken.getCharPositionInLine();
        this.channel = oldToken.getChannel();
        this.start = oldToken.getStartIndex();
        this.stop = oldToken.getStopIndex();
        if (oldToken instanceof CommonToken) {
            this.text = ((CommonToken)oldToken).text;
            this.source = ((CommonToken)oldToken).source;
        } else {
            this.text = oldToken.getText();
            this.source = Tuple.create(oldToken.getTokenSource(), oldToken.getInputStream());
        }
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String getText() {
        if (this.text != null) {
            return this.text;
        }
        CharStream input = this.getInputStream();
        if (input == null) {
            return null;
        }
        int n = input.size();
        if (this.start < n && this.stop < n) {
            return input.getText(Interval.of(this.start, this.stop));
        }
        return "<EOF>";
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    @Override
    public void setCharPositionInLine(int charPositionInLine) {
        this.charPositionInLine = charPositionInLine;
    }

    @Override
    public int getChannel() {
        return this.channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getStartIndex() {
        return this.start;
    }

    public void setStartIndex(int start) {
        this.start = start;
    }

    @Override
    public int getStopIndex() {
        return this.stop;
    }

    public void setStopIndex(int stop) {
        this.stop = stop;
    }

    @Override
    public int getTokenIndex() {
        return this.index;
    }

    @Override
    public void setTokenIndex(int index) {
        this.index = index;
    }

    @Override
    public TokenSource getTokenSource() {
        return this.source.getItem1();
    }

    @Override
    public CharStream getInputStream() {
        return this.source.getItem2();
    }

    public String toString() {
        return this.toString(null);
    }

    public String toString(@Nullable Recognizer<?, ?> r) {
        String txt;
        String channelStr = "";
        if (this.channel > 0) {
            channelStr = ",channel=" + this.channel;
        }
        if ((txt = this.getText()) != null) {
            txt = txt.replace("\n", "\\n");
            txt = txt.replace("\r", "\\r");
            txt = txt.replace("\t", "\\t");
        } else {
            txt = "<no text>";
        }
        String typeString = String.valueOf(this.type);
        if (r != null) {
            typeString = r.getVocabulary().getDisplayName(this.type);
        }
        return "[@" + this.getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + typeString + ">" + channelStr + "," + this.line + ":" + this.getCharPositionInLine() + "]";
    }
}

