/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.AttributeRenderer
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.StringRenderer
 */
package groovyjarjarantlr4.v4.codegen.target;

import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.codegen.UnicodeEscapes;
import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.StringRenderer;

public class JavaTarget
extends Target {
    private static final ThreadLocal<STGroup> targetTemplates = new ThreadLocal();
    protected static final String[] javaKeywords = new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"};
    protected final Set<String> badWords = new HashSet<String>();

    public JavaTarget(CodeGenerator gen) {
        super(gen, "Java");
    }

    public Set<String> getBadWords() {
        if (this.badWords.isEmpty()) {
            this.addBadWords();
        }
        return this.badWords;
    }

    protected void addBadWords() {
        this.badWords.addAll(Arrays.asList(javaKeywords));
        this.badWords.add("rule");
        this.badWords.add("parserRule");
    }

    @Override
    public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal, boolean addQuotes) {
        int toAdvance;
        StringBuilder sb = new StringBuilder();
        String is = literal;
        if (addQuotes) {
            sb.append('\"');
        }
        block4: for (int i = 1; i < is.length() - 1; i += toAdvance) {
            int codePoint = is.codePointAt(i);
            toAdvance = Character.charCount(codePoint);
            if (codePoint == 92) {
                int escapedCodePoint = is.codePointAt(i + toAdvance);
                ++toAdvance;
                switch (escapedCodePoint) {
                    case 92: 
                    case 98: 
                    case 102: 
                    case 110: 
                    case 114: 
                    case 116: {
                        sb.append('\\');
                        sb.appendCodePoint(escapedCodePoint);
                        break;
                    }
                    case 117: {
                        if (is.charAt(i + toAdvance) == '{') {
                            while (is.charAt(i + toAdvance) != '}') {
                                ++toAdvance;
                            }
                            ++toAdvance;
                        } else {
                            toAdvance += 4;
                        }
                        if (i + toAdvance > is.length()) continue block4;
                        String fullEscape = is.substring(i, i + toAdvance);
                        this.appendUnicodeEscapedCodePoint(CharSupport.getCharValueFromCharInGrammarLiteral(fullEscape), sb);
                        break;
                    }
                    default: {
                        if (this.shouldUseUnicodeEscapeForCodePointInDoubleQuotedString(escapedCodePoint)) {
                            this.appendUnicodeEscapedCodePoint(escapedCodePoint, sb);
                            break;
                        }
                        sb.appendCodePoint(escapedCodePoint);
                        break;
                    }
                }
                continue;
            }
            if (codePoint == 34) {
                sb.append("\\\"");
                continue;
            }
            if (this.shouldUseUnicodeEscapeForCodePointInDoubleQuotedString(codePoint)) {
                this.appendUnicodeEscapedCodePoint(codePoint, sb);
                continue;
            }
            sb.appendCodePoint(codePoint);
        }
        if (addQuotes) {
            sb.append('\"');
        }
        return sb.toString();
    }

    @Override
    public String encodeIntAsCharEscape(int v) {
        if (v < 0 || v > 65535) {
            throw new IllegalArgumentException(String.format("Cannot encode the specified value: %d", v));
        }
        if (v >= 0 && v < this.targetCharValueEscape.length && this.targetCharValueEscape[v] != null) {
            return this.targetCharValueEscape[v];
        }
        if (!(v < 32 || v >= 127 || Character.isDigit(v) && v != 56 && v != 57)) {
            return String.valueOf((char)v);
        }
        if (v >= 0 && v <= 127) {
            String oct = Integer.toOctalString(v);
            return "\\" + oct;
        }
        String hex = Integer.toHexString(v | 0x10000).substring(1, 5);
        return "\\u" + hex;
    }

    @Override
    public int getSerializedATNSegmentLimit() {
        return 21845;
    }

    @Override
    protected boolean visibleGrammarSymbolCausesIssueInGeneratedCode(GrammarAST idNode) {
        return this.getBadWords().contains(idNode.getText());
    }

    @Override
    protected STGroup loadTemplates() {
        STGroup result = targetTemplates.get();
        if (result == null) {
            result = super.loadTemplates();
            result.registerRenderer(String.class, (AttributeRenderer)new JavaStringRenderer(), true);
            targetTemplates.set(result);
        }
        return result;
    }

    @Override
    protected void appendUnicodeEscapedCodePoint(int codePoint, StringBuilder sb) {
        UnicodeEscapes.appendJavaStyleEscapedCodePoint(codePoint, sb);
    }

    protected static class JavaStringRenderer
    extends StringRenderer {
        protected JavaStringRenderer() {
        }

        public String toString(Object o, String formatString, Locale locale) {
            if ("java-escape".equals(formatString)) {
                return ((String)o).replace("\\u", "\\u005Cu");
            }
            return super.toString(o, formatString, locale);
        }
    }
}

