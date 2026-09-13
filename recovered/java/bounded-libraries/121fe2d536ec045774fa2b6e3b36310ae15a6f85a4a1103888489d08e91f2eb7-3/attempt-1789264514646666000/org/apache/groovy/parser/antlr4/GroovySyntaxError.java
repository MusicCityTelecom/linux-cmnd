/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

public class GroovySyntaxError
extends AssertionError {
    public static final int LEXER = 0;
    public static final int PARSER = 1;
    private final int source;
    private final int line;
    private final int column;

    public GroovySyntaxError(String message, int source, int line, int column) {
        super(message, null);
        if (source != 0 && source != 1) {
            throw new IllegalArgumentException("Invalid syntax error source: " + source);
        }
        this.source = source;
        this.line = line;
        this.column = column;
    }

    public int getSource() {
        return this.source;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }
}

