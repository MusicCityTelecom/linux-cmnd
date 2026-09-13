/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import org.apache.groovy.parser.antlr4.SyntaxErrorReportable;

public abstract class AbstractParser
extends Parser
implements SyntaxErrorReportable {
    public AbstractParser(TokenStream input) {
        super(input);
    }
}

