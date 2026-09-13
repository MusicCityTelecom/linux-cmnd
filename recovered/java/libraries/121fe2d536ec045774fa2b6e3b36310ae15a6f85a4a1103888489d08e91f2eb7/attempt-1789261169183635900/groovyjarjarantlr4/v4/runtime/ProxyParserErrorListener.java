/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ANTLRErrorListener;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserErrorListener;
import groovyjarjarantlr4.v4.runtime.ProxyErrorListener;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.SimulatorState;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import java.util.BitSet;
import java.util.Collection;

public class ProxyParserErrorListener
extends ProxyErrorListener<Token>
implements ParserErrorListener {
    public ProxyParserErrorListener(Collection<? extends ANTLRErrorListener<? super Token>> delegates) {
        super(delegates);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        for (ANTLRErrorListener listener : this.getDelegates()) {
            if (!(listener instanceof ParserErrorListener)) continue;
            ParserErrorListener parserErrorListener = (ParserErrorListener)listener;
            parserErrorListener.reportAmbiguity(recognizer, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
        }
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, SimulatorState conflictState) {
        for (ANTLRErrorListener listener : this.getDelegates()) {
            if (!(listener instanceof ParserErrorListener)) continue;
            ParserErrorListener parserErrorListener = (ParserErrorListener)listener;
            parserErrorListener.reportAttemptingFullContext(recognizer, dfa, startIndex, stopIndex, conflictingAlts, conflictState);
        }
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, SimulatorState acceptState) {
        for (ANTLRErrorListener listener : this.getDelegates()) {
            if (!(listener instanceof ParserErrorListener)) continue;
            ParserErrorListener parserErrorListener = (ParserErrorListener)listener;
            parserErrorListener.reportContextSensitivity(recognizer, dfa, startIndex, stopIndex, prediction, acceptState);
        }
    }
}

