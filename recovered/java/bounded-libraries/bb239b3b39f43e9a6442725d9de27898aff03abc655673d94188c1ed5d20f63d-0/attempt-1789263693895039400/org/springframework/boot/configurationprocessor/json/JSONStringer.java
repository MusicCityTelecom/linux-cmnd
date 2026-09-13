/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JSONStringer {
    final StringBuilder out = new StringBuilder();
    private final List<Scope> stack = new ArrayList<Scope>();
    private final String indent;

    public JSONStringer() {
        this.indent = null;
    }

    JSONStringer(int indentSpaces) {
        char[] indentChars = new char[indentSpaces];
        Arrays.fill(indentChars, ' ');
        this.indent = new String(indentChars);
    }

    public JSONStringer array() throws JSONException {
        return this.open(Scope.EMPTY_ARRAY, "[");
    }

    public JSONStringer endArray() throws JSONException {
        return this.close(Scope.EMPTY_ARRAY, Scope.NONEMPTY_ARRAY, "]");
    }

    public JSONStringer object() throws JSONException {
        return this.open(Scope.EMPTY_OBJECT, "{");
    }

    public JSONStringer endObject() throws JSONException {
        return this.close(Scope.EMPTY_OBJECT, Scope.NONEMPTY_OBJECT, "}");
    }

    JSONStringer open(Scope empty, String openBracket) throws JSONException {
        if (this.stack.isEmpty() && this.out.length() > 0) {
            throw new JSONException("Nesting problem: multiple top-level roots");
        }
        this.beforeValue();
        this.stack.add(empty);
        this.out.append(openBracket);
        return this;
    }

    JSONStringer close(Scope empty, Scope nonempty, String closeBracket) throws JSONException {
        Scope context = this.peek();
        if (context != nonempty && context != empty) {
            throw new JSONException("Nesting problem");
        }
        this.stack.remove(this.stack.size() - 1);
        if (context == nonempty) {
            this.newline();
        }
        this.out.append(closeBracket);
        return this;
    }

    private Scope peek() throws JSONException {
        if (this.stack.isEmpty()) {
            throw new JSONException("Nesting problem");
        }
        return this.stack.get(this.stack.size() - 1);
    }

    private void replaceTop(Scope topOfStack) {
        this.stack.set(this.stack.size() - 1, topOfStack);
    }

    public JSONStringer value(Object value) throws JSONException {
        if (this.stack.isEmpty()) {
            throw new JSONException("Nesting problem");
        }
        if (value instanceof JSONArray) {
            ((JSONArray)value).writeTo(this);
            return this;
        }
        if (value instanceof JSONObject) {
            ((JSONObject)value).writeTo(this);
            return this;
        }
        this.beforeValue();
        if (value == null || value instanceof Boolean || value == JSONObject.NULL) {
            this.out.append(value);
        } else if (value instanceof Number) {
            this.out.append(JSONObject.numberToString((Number)value));
        } else {
            this.string(value.toString());
        }
        return this;
    }

    public JSONStringer value(boolean value) throws JSONException {
        if (this.stack.isEmpty()) {
            throw new JSONException("Nesting problem");
        }
        this.beforeValue();
        this.out.append(value);
        return this;
    }

    public JSONStringer value(double value) throws JSONException {
        if (this.stack.isEmpty()) {
            throw new JSONException("Nesting problem");
        }
        this.beforeValue();
        this.out.append(JSONObject.numberToString(value));
        return this;
    }

    public JSONStringer value(long value) throws JSONException {
        if (this.stack.isEmpty()) {
            throw new JSONException("Nesting problem");
        }
        this.beforeValue();
        this.out.append(value);
        return this;
    }

    private void string(String value) {
        this.out.append("\"");
        int length = value.length();
        block8: for (int i = 0; i < length; ++i) {
            char c = value.charAt(i);
            switch (c) {
                case '\"': 
                case '/': 
                case '\\': {
                    this.out.append('\\').append(c);
                    continue block8;
                }
                case '\t': {
                    this.out.append("\\t");
                    continue block8;
                }
                case '\b': {
                    this.out.append("\\b");
                    continue block8;
                }
                case '\n': {
                    this.out.append("\\n");
                    continue block8;
                }
                case '\r': {
                    this.out.append("\\r");
                    continue block8;
                }
                case '\f': {
                    this.out.append("\\f");
                    continue block8;
                }
                default: {
                    if (c <= '\u001f') {
                        this.out.append(String.format("\\u%04x", c));
                        continue block8;
                    }
                    this.out.append(c);
                }
            }
        }
        this.out.append("\"");
    }

    private void newline() {
        if (this.indent == null) {
            return;
        }
        this.out.append("\n");
        for (int i = 0; i < this.stack.size(); ++i) {
            this.out.append(this.indent);
        }
    }

    public JSONStringer key(String name) throws JSONException {
        if (name == null) {
            throw new JSONException("Names must be non-null");
        }
        this.beforeKey();
        this.string(name);
        return this;
    }

    private void beforeKey() throws JSONException {
        Scope context = this.peek();
        if (context == Scope.NONEMPTY_OBJECT) {
            this.out.append(',');
        } else if (context != Scope.EMPTY_OBJECT) {
            throw new JSONException("Nesting problem");
        }
        this.newline();
        this.replaceTop(Scope.DANGLING_KEY);
    }

    private void beforeValue() throws JSONException {
        if (this.stack.isEmpty()) {
            return;
        }
        Scope context = this.peek();
        if (context == Scope.EMPTY_ARRAY) {
            this.replaceTop(Scope.NONEMPTY_ARRAY);
            this.newline();
        } else if (context == Scope.NONEMPTY_ARRAY) {
            this.out.append(',');
            this.newline();
        } else if (context == Scope.DANGLING_KEY) {
            this.out.append(this.indent == null ? ":" : ": ");
            this.replaceTop(Scope.NONEMPTY_OBJECT);
        } else if (context != Scope.NULL) {
            throw new JSONException("Nesting problem");
        }
    }

    public String toString() {
        return this.out.length() == 0 ? null : this.out.toString();
    }

    static enum Scope {
        EMPTY_ARRAY,
        NONEMPTY_ARRAY,
        EMPTY_OBJECT,
        DANGLING_KEY,
        NONEMPTY_OBJECT,
        NULL;

    }
}

