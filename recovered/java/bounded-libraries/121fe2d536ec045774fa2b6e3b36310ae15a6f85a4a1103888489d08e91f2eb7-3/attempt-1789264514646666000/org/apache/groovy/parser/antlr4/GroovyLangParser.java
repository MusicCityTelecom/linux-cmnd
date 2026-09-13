/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.FailedPredicateException;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.apache.groovy.parser.antlr4.internal.atnmanager.ParserAtnManager;
import org.apache.groovy.util.SystemUtil;

public class GroovyLangParser
extends GroovyParser {
    private static final boolean GROOVY_PARSER_PROFILING_ENABLED = SystemUtil.getBooleanSafe("groovy.antlr4.profile");

    public GroovyLangParser(TokenStream input) {
        super(input);
        this.setInterpreter(new ParserATNSimulator(this, ParserAtnManager.INSTANCE.getATN()));
        if (GROOVY_PARSER_PROFILING_ENABLED) {
            this.setProfile(true);
        }
    }

    @Override
    protected FailedPredicateException createFailedPredicateException(String predicate, String message) {
        return new LightWeightFailedPredicateException(this, predicate, message);
    }

    private static class LightWeightFailedPredicateException
    extends FailedPredicateException {
        LightWeightFailedPredicateException(Parser recognizer, String predicate, String message) {
            super(recognizer, predicate, message);
        }

        @Override
        public final Throwable fillInStackTrace() {
            return this;
        }
    }
}

