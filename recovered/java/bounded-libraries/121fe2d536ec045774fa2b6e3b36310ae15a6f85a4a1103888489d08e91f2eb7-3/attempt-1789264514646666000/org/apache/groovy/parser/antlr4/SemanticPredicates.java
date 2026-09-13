/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.groovy.parser.antlr4.GroovyParser;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ModifierNode;

public class SemanticPredicates {
    private static final Pattern NONSPACES_PATTERN = Pattern.compile("\\S+?");
    private static final Pattern LETTER_AND_LEFTCURLY_PATTERN = Pattern.compile("[a-zA-Z_{]");
    private static final Pattern NONSURROGATE_PATTERN = Pattern.compile("[^\u0000-\u007f\ud800-\udbff]");
    private static final Pattern SURROGATE_PAIR1_PATTERN = Pattern.compile("[\ud800-\udbff]");
    private static final Pattern SURROGATE_PAIR2_PATTERN = Pattern.compile("[\udc00-\udfff]");
    private static final int[] MODIFIER_ARRAY = ModifierNode.MODIFIER_OPCODE_MAP.keySet().stream().mapToInt(Integer::intValue).sorted().toArray();

    public static boolean isFollowedByWhiteSpaces(CharStream cs) {
        int index = 1;
        int c = cs.LA(index);
        while (13 != c && 10 != c && -1 != c) {
            if (StringUtils.matches(String.valueOf((char)c), NONSPACES_PATTERN)) {
                return false;
            }
            c = cs.LA(++index);
        }
        return true;
    }

    public static boolean isFollowedBy(CharStream cs, char ... chars) {
        int c1 = cs.LA(1);
        for (char c : chars) {
            if (c1 != c) continue;
            return true;
        }
        return false;
    }

    public static boolean isFollowedByJavaLetterInGString(CharStream cs) {
        int c1 = cs.LA(1);
        if (36 == c1) {
            return false;
        }
        String str1 = String.valueOf((char)c1);
        if (StringUtils.matches(str1, LETTER_AND_LEFTCURLY_PATTERN)) {
            return true;
        }
        if (StringUtils.matches(str1, NONSURROGATE_PATTERN) && Character.isJavaIdentifierPart(c1)) {
            return true;
        }
        int c2 = cs.LA(2);
        String str2 = String.valueOf((char)c2);
        return StringUtils.matches(str1, SURROGATE_PAIR1_PATTERN) && StringUtils.matches(str2, SURROGATE_PAIR2_PATTERN) && Character.isJavaIdentifierPart(Character.toCodePoint((char)c1, (char)c2));
    }

    public static boolean isFollowingArgumentsOrClosure(GroovyParser.ExpressionContext context) {
        if (context instanceof GroovyParser.PostfixExprAltContext) {
            List peacChildren = ((GroovyParser.PostfixExprAltContext)context).children;
            try {
                ParseTree peacChild = (ParseTree)peacChildren.get(0);
                List pecChildren = ((GroovyParser.PostfixExpressionContext)peacChild).children;
                ParseTree pecChild = (ParseTree)pecChildren.get(0);
                GroovyParser.PathExpressionContext pec = (GroovyParser.PathExpressionContext)pecChild;
                int t = pec.t;
                return 2 == t || 3 == t;
            }
            catch (ClassCastException | IndexOutOfBoundsException e) {
                throw new GroovyBugError("Unexpected structure of expression context: " + context, e);
            }
        }
        return false;
    }

    public static boolean isInvalidMethodDeclaration(TokenStream ts) {
        int tokenType = ts.LT(1).getType();
        return (131 == tokenType || 130 == tokenType || 1 == tokenType || 17 == tokenType) && 86 == ts.LT(2).getType();
    }

    public static boolean isInvalidLocalVariableDeclaration(TokenStream ts) {
        int index = 2;
        int tokenType2 = ts.LT(index).getType();
        if (94 == tokenType2) {
            int tokeTypeN = tokenType2;
            while (94 == (tokeTypeN = ts.LT(index += 2).getType())) {
            }
            if (97 == tokeTypeN || 90 == tokeTypeN) {
                return false;
            }
            tokenType2 = ts.LT(--index + 1).getType();
        } else {
            index = 1;
        }
        Token token = ts.LT(index);
        int tokenType = token.getType();
        int tokenType3 = ts.LT(index + 2).getType();
        int nextCodePoint = token.getText().codePointAt(0);
        return 13 != tokenType && Arrays.binarySearch(MODIFIER_ARRAY, tokenType) < 0 && !Character.isUpperCase(nextCodePoint) && nextCodePoint != 64 && 95 != tokenType3 && 97 != tokenType2 && 90 != tokenType2;
    }
}

