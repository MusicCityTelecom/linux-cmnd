/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JavaccError;
import org.apache.commons.jexl3.parser.StringParser;

public class TokenMgrException
extends RuntimeException
implements JavaccError {
    private static final long serialVersionUID = 1L;
    public static final int LEXICAL_ERROR = 0;
    public static final int STATIC_LEXER_ERROR = 1;
    public static final int INVALID_LEXICAL_STATE = 2;
    public static final int LOOP_DETECTED = 3;
    private final int errorCode;
    private int state;
    private char current;
    private String after;
    private boolean eof;
    private int line;
    private int column;

    @Override
    public String getMessage() {
        return "Lexical error at line " + this.line + ", column " + this.column + ".  Encountered: " + (this.eof ? "<EOF> " : StringParser.escapeString(String.valueOf(this.current), '\"') + " (" + this.current + "), ") + "after : " + StringParser.escapeString(this.after, '\"');
    }

    public TokenMgrException(String message, int reason) {
        super(message);
        this.errorCode = reason;
    }

    public TokenMgrException(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, int curChar, int reason) {
        this.eof = EOFSeen;
        this.state = lexState;
        this.line = errorLine;
        this.column = errorColumn;
        this.after = errorAfter;
        this.current = (char)curChar;
        this.errorCode = reason;
    }

    public int getErrorCode() {
        return this.errorCode;
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

    protected static String addEscapes(String str) {
        StringBuilder retval = new StringBuilder();
        block11: for (int i = 0; i < str.length(); ++i) {
            switch (str.charAt(i)) {
                case '\u0000': {
                    continue block11;
                }
                case '\b': {
                    retval.append("//b");
                    continue block11;
                }
                case '\t': {
                    retval.append("//t");
                    continue block11;
                }
                case '\n': {
                    retval.append("//n");
                    continue block11;
                }
                case '\f': {
                    retval.append("//f");
                    continue block11;
                }
                case '\r': {
                    retval.append("//r");
                    continue block11;
                }
                case '\"': {
                    retval.append("//\"");
                    continue block11;
                }
                case '\'': {
                    retval.append("//'");
                    continue block11;
                }
                case '/': {
                    retval.append("////");
                    continue block11;
                }
                default: {
                    char ch = str.charAt(i);
                    if (ch < ' ' || ch > '~') {
                        String s = "0000" + Integer.toString(ch, 16);
                        retval.append("//u").append(s.substring(s.length() - 4));
                        continue block11;
                    }
                    retval.append(ch);
                }
            }
        }
        return retval.toString();
    }
}

