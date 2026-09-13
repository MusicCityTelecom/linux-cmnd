/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4.internal.atnmanager;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import org.apache.groovy.parser.antlr4.GroovyLangParser;
import org.apache.groovy.parser.antlr4.internal.atnmanager.AtnManager;

public class ParserAtnManager
extends AtnManager {
    private final AtnManager.AtnWrapper parserAtnWrapper = new AtnManager.AtnWrapper(GroovyLangParser._ATN);
    public static final ParserAtnManager INSTANCE = new ParserAtnManager();

    @Override
    public ATN getATN() {
        return this.parserAtnWrapper.checkAndClear();
    }

    @Override
    protected boolean shouldClearDfaCache() {
        return true;
    }

    private ParserAtnManager() {
    }
}

