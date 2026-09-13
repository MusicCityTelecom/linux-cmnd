/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.util.Expando
 */
package groovy.json;

import groovy.json.DefaultJsonGenerator;
import groovy.json.JsonGenerator;
import groovy.json.JsonLexer;
import groovy.json.JsonToken;
import groovy.lang.Closure;
import groovy.util.Expando;
import java.io.StringReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.groovy.json.internal.CharBuf;
import org.apache.groovy.json.internal.Chr;

public class JsonOutput {
    static final char OPEN_BRACKET = '[';
    static final char CLOSE_BRACKET = ']';
    static final char OPEN_BRACE = '{';
    static final char CLOSE_BRACE = '}';
    static final char COLON = ':';
    static final char COMMA = ',';
    static final char SPACE = ' ';
    static final char NEW_LINE = '\n';
    static final char QUOTE = '\"';
    static final char[] EMPTY_STRING_CHARS = Chr.array('\"', '\"');
    static final char[] EMPTY_MAP_CHARS = new char[]{'{', '}'};
    static final char[] EMPTY_LIST_CHARS = new char[]{'[', ']'};
    static final JsonGenerator DEFAULT_GENERATOR = new DefaultJsonGenerator(new JsonGenerator.Options());

    public static String toJson(Boolean bool) {
        return DEFAULT_GENERATOR.toJson(bool);
    }

    public static String toJson(Number n) {
        return DEFAULT_GENERATOR.toJson(n);
    }

    public static String toJson(Character c) {
        return DEFAULT_GENERATOR.toJson(c);
    }

    public static String toJson(String s) {
        return DEFAULT_GENERATOR.toJson(s);
    }

    public static String toJson(Date date) {
        return DEFAULT_GENERATOR.toJson(date);
    }

    public static String toJson(Calendar cal) {
        return DEFAULT_GENERATOR.toJson(cal);
    }

    public static String toJson(UUID uuid) {
        return DEFAULT_GENERATOR.toJson(uuid);
    }

    public static String toJson(URL url) {
        return DEFAULT_GENERATOR.toJson(url);
    }

    public static String toJson(Closure closure) {
        return DEFAULT_GENERATOR.toJson(closure);
    }

    public static String toJson(Expando expando) {
        return DEFAULT_GENERATOR.toJson(expando);
    }

    public static String toJson(Object object) {
        return DEFAULT_GENERATOR.toJson(object);
    }

    public static String toJson(Map m) {
        return DEFAULT_GENERATOR.toJson(m);
    }

    public static String prettyPrint(String jsonPayload) {
        int indentSize = 0;
        CharBuf output = CharBuf.create((int)((double)jsonPayload.length() * 1.2));
        JsonLexer lexer = new JsonLexer(new StringReader(jsonPayload));
        HashMap<Integer, char[]> indentCache = new HashMap<Integer, char[]>();
        block9: while (lexer.hasNext()) {
            JsonToken token = lexer.next();
            switch (token.getType()) {
                case OPEN_CURLY: {
                    output.addChars(Chr.array('{', '\n')).addChars(JsonOutput.getIndent(indentSize += 4, indentCache));
                    continue block9;
                }
                case CLOSE_CURLY: {
                    output.addChar('\n');
                    if ((indentSize -= 4) > 0) {
                        output.addChars(JsonOutput.getIndent(indentSize, indentCache));
                    }
                    output.addChar('}');
                    continue block9;
                }
                case OPEN_BRACKET: {
                    output.addChars(Chr.array('[', '\n')).addChars(JsonOutput.getIndent(indentSize += 4, indentCache));
                    continue block9;
                }
                case CLOSE_BRACKET: {
                    output.addChar('\n');
                    if ((indentSize -= 4) > 0) {
                        output.addChars(JsonOutput.getIndent(indentSize, indentCache));
                    }
                    output.addChar(']');
                    continue block9;
                }
                case COMMA: {
                    output.addChars(Chr.array(',', '\n')).addChars(JsonOutput.getIndent(indentSize, indentCache));
                    continue block9;
                }
                case COLON: {
                    output.addChars(Chr.array(':', ' '));
                    continue block9;
                }
                case STRING: {
                    String textStr = token.getText();
                    String textWithoutQuotes = textStr.substring(1, textStr.length() - 1);
                    if (textWithoutQuotes.length() > 0) {
                        output.addJsonEscapedString(textWithoutQuotes);
                        continue block9;
                    }
                    output.addQuoted(Chr.array(new char[0]));
                    continue block9;
                }
            }
            output.addString(token.getText());
        }
        return output.toString();
    }

    private static char[] getIndent(int indentSize, Map<Integer, char[]> indentCache) {
        char[] indent = indentCache.get(indentSize);
        if (indent == null) {
            indent = new char[indentSize];
            Arrays.fill(indent, ' ');
            indentCache.put(indentSize, indent);
        }
        return indent;
    }

    public static JsonUnescaped unescaped(CharSequence text) {
        return new JsonUnescaped(text);
    }

    public static class JsonUnescaped {
        private CharSequence text;

        public JsonUnescaped(CharSequence text) {
            this.text = text;
        }

        public CharSequence getText() {
            return this.text;
        }

        public String toString() {
            return this.text.toString();
        }
    }
}

