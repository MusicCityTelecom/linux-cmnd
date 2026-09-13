/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JavaccError;
import org.apache.commons.jexl3.parser.Token;

public class ParseException
extends Exception
implements JavaccError {
    private static final long serialVersionUID = 1L;
    private String after = "";
    private int line = -1;
    private int column = -1;

    public ParseException(Token currentToken, int[][] expectedTokenSequences, String[] tokenImage) {
        super("parse error");
        Token tok = currentToken.next;
        this.after = tok.image;
        this.line = tok.beginLine;
        this.column = tok.beginColumn;
    }

    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public String getAfter() {
        return this.after;
    }
}

