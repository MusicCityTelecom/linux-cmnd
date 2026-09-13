/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.DFASerializer;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class LexerDFASerializer
extends DFASerializer {
    public LexerDFASerializer(@NotNull DFA dfa) {
        super(dfa, VocabularyImpl.EMPTY_VOCABULARY);
    }

    @Override
    @NotNull
    protected String getEdgeLabel(int i) {
        return new StringBuilder("'").appendCodePoint(i).append("'").toString();
    }
}

