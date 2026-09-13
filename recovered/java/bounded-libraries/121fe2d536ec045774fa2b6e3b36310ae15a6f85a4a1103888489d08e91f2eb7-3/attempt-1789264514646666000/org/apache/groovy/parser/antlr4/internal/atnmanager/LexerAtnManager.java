/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4.internal.atnmanager;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import org.apache.groovy.parser.antlr4.GroovyLangLexer;
import org.apache.groovy.parser.antlr4.internal.atnmanager.AtnManager;
import org.apache.groovy.util.SystemUtil;

public class LexerAtnManager
extends AtnManager {
    private static final String GROOVY_CLEAR_LEXER_DFA_CACHE = "groovy.antlr4.clear.lexer.dfa.cache";
    private static final boolean TO_CLEAR_LEXER_DFA_CACHE;
    private final AtnManager.AtnWrapper lexerAtnWrapper = new AtnManager.AtnWrapper(GroovyLangLexer._ATN);
    public static final LexerAtnManager INSTANCE;

    @Override
    public ATN getATN() {
        return this.lexerAtnWrapper.checkAndClear();
    }

    @Override
    protected boolean shouldClearDfaCache() {
        return TO_CLEAR_LEXER_DFA_CACHE;
    }

    private LexerAtnManager() {
    }

    static {
        INSTANCE = new LexerAtnManager();
        TO_CLEAR_LEXER_DFA_CACHE = SystemUtil.getBooleanSafe(GROOVY_CLEAR_LEXER_DFA_CACHE);
    }
}

