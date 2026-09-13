/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Lexer;
import org.apache.groovy.parser.antlr4.SyntaxErrorReportable;

public abstract class AbstractLexer
extends Lexer
implements SyntaxErrorReportable {
    public AbstractLexer(CharStream input) {
        super(input);
    }
}

