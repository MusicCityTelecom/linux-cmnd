/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CommonToken;
import groovyjarjarantlr4.v4.runtime.TokenFactory;
import groovyjarjarantlr4.v4.runtime.TokenSource;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;

public class CommonTokenFactory
implements TokenFactory {
    public static final TokenFactory DEFAULT = new CommonTokenFactory();
    protected final boolean copyText;

    public CommonTokenFactory(boolean copyText) {
        this.copyText = copyText;
    }

    public CommonTokenFactory() {
        this(false);
    }

    @Override
    public CommonToken create(Tuple2<? extends TokenSource, CharStream> source, int type, String text, int channel, int start, int stop, int line, int charPositionInLine) {
        CommonToken t = new CommonToken(source, type, channel, start, stop);
        t.setLine(line);
        t.setCharPositionInLine(charPositionInLine);
        if (text != null) {
            t.setText(text);
        } else if (this.copyText && source.getItem2() != null) {
            t.setText(source.getItem2().getText(Interval.of(start, stop)));
        }
        return t;
    }

    @Override
    public CommonToken create(int type, String text) {
        return new CommonToken(type, text);
    }
}

