/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.json;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JSONTokener {
    private final String in;
    private int pos;

    public JSONTokener(String in) {
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        this.in = in;
    }

    public Object nextValue() throws JSONException {
        int c = this.nextCleanInternal();
        switch (c) {
            case -1: {
                throw this.syntaxError("End of input");
            }
            case 123: {
                return this.readObject();
            }
            case 91: {
                return this.readArray();
            }
            case 34: 
            case 39: {
                return this.nextString((char)c);
            }
        }
        --this.pos;
        return this.readLiteral();
    }

    private int nextCleanInternal() throws JSONException {
        block9: while (this.pos < this.in.length()) {
            char c = this.in.charAt(this.pos++);
            switch (c) {
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    continue block9;
                }
                case '/': {
                    if (this.pos == this.in.length()) {
                        return c;
                    }
                    char peek = this.in.charAt(this.pos);
                    switch (peek) {
                        case '*': {
                            ++this.pos;
                            int commentEnd = this.in.indexOf("*/", this.pos);
                            if (commentEnd == -1) {
                                throw this.syntaxError("Unterminated comment");
                            }
                            this.pos = commentEnd + 2;
                            continue block9;
                        }
                        case '/': {
                            ++this.pos;
                            this.skipToEndOfLine();
                            continue block9;
                        }
                    }
                    return c;
                }
                case '#': {
                    this.skipToEndOfLine();
                    continue block9;
                }
            }
            return c;
        }
        return -1;
    }

    private void skipToEndOfLine() {
        while (this.pos < this.in.length()) {
            char c = this.in.charAt(this.pos);
            if (c == '\r' || c == '\n') {
                ++this.pos;
                break;
            }
            ++this.pos;
        }
    }

    public String nextString(char quote) throws JSONException {
        StringBuilder builder = null;
        int start = this.pos;
        while (this.pos < this.in.length()) {
            char c;
            if ((c = this.in.charAt(this.pos++)) == quote) {
                if (builder == null) {
                    return new String(this.in.substring(start, this.pos - 1));
                }
                builder.append(this.in, start, this.pos - 1);
                return builder.toString();
            }
            if (c != '\\') continue;
            if (this.pos == this.in.length()) {
                throw this.syntaxError("Unterminated escape sequence");
            }
            if (builder == null) {
                builder = new StringBuilder();
            }
            builder.append(this.in, start, this.pos - 1);
            builder.append(this.readEscapeCharacter());
            start = this.pos;
        }
        throw this.syntaxError("Unterminated string");
    }

    private char readEscapeCharacter() throws JSONException {
        char escaped = this.in.charAt(this.pos++);
        switch (escaped) {
            case 'u': {
                if (this.pos + 4 > this.in.length()) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                String hex = this.in.substring(this.pos, this.pos + 4);
                this.pos += 4;
                return (char)Integer.parseInt(hex, 16);
            }
            case 't': {
                return '\t';
            }
            case 'b': {
                return '\b';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 'f': {
                return '\f';
            }
        }
        return escaped;
    }

    private Object readLiteral() throws JSONException {
        String literal = this.nextToInternal("{}[]/\\:,=;# \t\f");
        if (literal.isEmpty()) {
            throw this.syntaxError("Expected literal value");
        }
        if ("null".equalsIgnoreCase(literal)) {
            return JSONObject.NULL;
        }
        if ("true".equalsIgnoreCase(literal)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(literal)) {
            return Boolean.FALSE;
        }
        if (literal.indexOf(46) == -1) {
            int base = 10;
            String number = literal;
            if (number.startsWith("0x") || number.startsWith("0X")) {
                number = number.substring(2);
                base = 16;
            } else if (number.startsWith("0") && number.length() > 1) {
                number = number.substring(1);
                base = 8;
            }
            try {
                long longValue = Long.parseLong(number, base);
                if (longValue <= Integer.MAX_VALUE && longValue >= Integer.MIN_VALUE) {
                    return (int)longValue;
                }
                return longValue;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        try {
            return Double.valueOf(literal);
        }
        catch (NumberFormatException numberFormatException) {
            return new String(literal);
        }
    }

    private String nextToInternal(String excluded) {
        int start = this.pos;
        while (this.pos < this.in.length()) {
            char c = this.in.charAt(this.pos);
            if (c == '\r' || c == '\n' || excluded.indexOf(c) != -1) {
                return this.in.substring(start, this.pos);
            }
            ++this.pos;
        }
        return this.in.substring(start);
    }

    private JSONObject readObject() throws JSONException {
        JSONObject result = new JSONObject();
        int first = this.nextCleanInternal();
        if (first == 125) {
            return result;
        }
        if (first != -1) {
            --this.pos;
        }
        block4: while (true) {
            Object name;
            if (!((name = this.nextValue()) instanceof String)) {
                if (name == null) {
                    throw this.syntaxError("Names cannot be null");
                }
                throw this.syntaxError("Names must be strings, but " + name + " is of type " + name.getClass().getName());
            }
            int separator = this.nextCleanInternal();
            if (separator != 58 && separator != 61) {
                throw this.syntaxError("Expected ':' after " + name);
            }
            if (this.pos < this.in.length() && this.in.charAt(this.pos) == '>') {
                ++this.pos;
            }
            result.put((String)name, this.nextValue());
            switch (this.nextCleanInternal()) {
                case 125: {
                    return result;
                }
                case 44: 
                case 59: {
                    continue block4;
                }
            }
            break;
        }
        throw this.syntaxError("Unterminated object");
    }

    private JSONArray readArray() throws JSONException {
        JSONArray result = new JSONArray();
        boolean hasTrailingSeparator = false;
        block9: while (true) {
            switch (this.nextCleanInternal()) {
                case -1: {
                    throw this.syntaxError("Unterminated array");
                }
                case 93: {
                    if (hasTrailingSeparator) {
                        result.put(null);
                    }
                    return result;
                }
                case 44: 
                case 59: {
                    result.put(null);
                    hasTrailingSeparator = true;
                    continue block9;
                }
            }
            --this.pos;
            result.put(this.nextValue());
            switch (this.nextCleanInternal()) {
                case 93: {
                    return result;
                }
                case 44: 
                case 59: {
                    hasTrailingSeparator = true;
                    continue block9;
                }
            }
            break;
        }
        throw this.syntaxError("Unterminated array");
    }

    public JSONException syntaxError(String message) {
        return new JSONException(message + this);
    }

    public String toString() {
        return " at character " + this.pos + " of " + this.in;
    }

    public boolean more() {
        return this.pos < this.in.length();
    }

    public char next() {
        return this.pos < this.in.length() ? this.in.charAt(this.pos++) : (char)'\u0000';
    }

    public char next(char c) throws JSONException {
        char result = this.next();
        if (result != c) {
            throw this.syntaxError("Expected " + c + " but was " + result);
        }
        return result;
    }

    public char nextClean() throws JSONException {
        int nextCleanInt = this.nextCleanInternal();
        return nextCleanInt == -1 ? (char)'\u0000' : (char)nextCleanInt;
    }

    public String next(int length) throws JSONException {
        if (this.pos + length > this.in.length()) {
            throw this.syntaxError(length + " is out of bounds");
        }
        String result = this.in.substring(this.pos, this.pos + length);
        this.pos += length;
        return result;
    }

    public String nextTo(String excluded) {
        if (excluded == null) {
            throw new NullPointerException("excluded == null");
        }
        return this.nextToInternal(excluded).trim();
    }

    public String nextTo(char excluded) {
        return this.nextToInternal(String.valueOf(excluded)).trim();
    }

    public void skipPast(String thru) {
        int thruStart = this.in.indexOf(thru, this.pos);
        this.pos = thruStart == -1 ? this.in.length() : thruStart + thru.length();
    }

    public char skipTo(char to) {
        int index = this.in.indexOf(to, this.pos);
        if (index != -1) {
            this.pos = index;
            return to;
        }
        return '\u0000';
    }

    public void back() {
        if (--this.pos == -1) {
            this.pos = 0;
        }
    }

    public static int dehexchar(char hex) {
        if (hex >= '0' && hex <= '9') {
            return hex - 48;
        }
        if (hex >= 'A' && hex <= 'F') {
            return hex - 65 + 10;
        }
        if (hex >= 'a' && hex <= 'f') {
            return hex - 97 + 10;
        }
        return -1;
    }
}

