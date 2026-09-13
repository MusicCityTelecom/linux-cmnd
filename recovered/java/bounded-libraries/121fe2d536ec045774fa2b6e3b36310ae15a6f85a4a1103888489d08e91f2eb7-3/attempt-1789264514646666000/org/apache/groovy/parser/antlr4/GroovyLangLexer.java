/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CharStreams;
import groovyjarjarantlr4.v4.runtime.Lexer;
import groovyjarjarantlr4.v4.runtime.LexerNoViableAltException;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.LexerATNSimulator;
import java.io.IOException;
import java.io.Reader;
import org.apache.groovy.parser.antlr4.GroovyLexer;
import org.apache.groovy.parser.antlr4.internal.atnmanager.LexerAtnManager;

public class GroovyLangLexer
extends GroovyLexer {
    public GroovyLangLexer(Reader reader) throws IOException {
        this(CharStreams.fromReader(reader));
    }

    public GroovyLangLexer(CharStream input) {
        super(input);
        this.setInterpreter(new PositionAdjustingLexerATNSimulator(this, LexerAtnManager.INSTANCE.getATN()));
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        throw e;
    }

    @Override
    protected void rollbackOneChar() {
        ((PositionAdjustingLexerATNSimulator)this.getInterpreter()).resetAcceptPosition(this.getInputStream(), this._tokenStartCharIndex - 1, this._tokenStartLine, this._tokenStartCharPositionInLine - 1);
    }

    private static class PositionAdjustingLexerATNSimulator
    extends LexerATNSimulator {
        public PositionAdjustingLexerATNSimulator(Lexer recog, ATN atn) {
            super(recog, atn);
        }

        protected void resetAcceptPosition(CharStream input, int index, int line, int charPositionInLine) {
            input.seek(index);
            this.line = line;
            this.charPositionInLine = charPositionInLine;
            this.consume(input);
        }
    }
}

