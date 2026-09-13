/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import java.util.ArrayList;
import java.util.List;

public class ScopeParser {
    public static AttributeDict parseTypedArgList(@Nullable ActionAST action, String s, Grammar g) {
        return ScopeParser.parse(action, s, ',', g);
    }

    public static AttributeDict parse(@Nullable ActionAST action, String s, char separator, Grammar g) {
        AttributeDict dict = new AttributeDict();
        List<Tuple2<String, Integer>> decls = ScopeParser.splitDecls(s, separator);
        for (Tuple2<String, Integer> decl : decls) {
            if (decl.getItem1().trim().length() <= 0) continue;
            Attribute a = ScopeParser.parseAttributeDef(action, decl, g);
            dict.add(a);
        }
        return dict;
    }

    public static Attribute parseAttributeDef(@Nullable ActionAST action, @NotNull Tuple2<String, Integer> decl, Grammar g) {
        if (decl.getItem1() == null) {
            return null;
        }
        Attribute attr = new Attribute();
        int rightEdgeOfDeclarator = decl.getItem1().length() - 1;
        int equalsIndex = decl.getItem1().indexOf(61);
        if (equalsIndex > 0) {
            attr.initValue = decl.getItem1().substring(equalsIndex + 1, decl.getItem1().length()).trim();
            rightEdgeOfDeclarator = equalsIndex - 1;
        }
        String declarator = decl.getItem1().substring(0, rightEdgeOfDeclarator + 1);
        String text = decl.getItem1();
        Tuple2<Integer, Integer> p = (text = text.replaceAll("::", "")).contains(":") ? ScopeParser._parsePostfixDecl(attr, declarator, action, g) : ScopeParser._parsePrefixDecl(attr, declarator, action, g);
        int idStart = p.getItem1();
        int idStop = p.getItem2();
        attr.decl = decl.getItem1();
        if (action != null) {
            String actionText = action.getText();
            int[] lines = new int[actionText.length()];
            int[] charPositionInLines = new int[actionText.length()];
            int i = 0;
            int line = 0;
            int col = 0;
            while (i < actionText.length()) {
                lines[i] = line++;
                charPositionInLines[i] = col;
                if (actionText.charAt(i) == '\n') {
                    col = -1;
                }
                ++i;
                ++col;
            }
            int[] charIndexes = new int[actionText.length()];
            int i2 = 0;
            int j = 0;
            while (i2 < actionText.length()) {
                charIndexes[j] = i2;
                if (i2 < actionText.length() - 1 && actionText.charAt(i2) == '/' && actionText.charAt(i2 + 1) == '/') {
                    while (i2 < actionText.length() && actionText.charAt(i2) != '\n') {
                        ++i2;
                    }
                }
                ++i2;
                ++j;
            }
            int declOffset = charIndexes[decl.getItem2()];
            int declLine = lines[declOffset + idStart];
            int line2 = action.getToken().getLine() + declLine;
            int charPositionInLine = charPositionInLines[declOffset + idStart];
            if (declLine == 0) {
                charPositionInLine += action.getToken().getCharPositionInLine() + 1;
            }
            int offset = ((CommonToken)action.getToken()).getStartIndex();
            attr.token = new CommonToken(action.getToken().getInputStream(), 28, 0, offset + declOffset + idStart + 1, offset + declOffset + idStop);
            attr.token.setLine(line2);
            attr.token.setCharPositionInLine(charPositionInLine);
            assert (attr.name.equals(attr.token.getText())) : "Attribute text should match the pseudo-token text at this point.";
        }
        return attr;
    }

    public static Tuple2<Integer, Integer> _parsePrefixDecl(Attribute attr, String decl, ActionAST a, Grammar g) {
        boolean inID = false;
        int start = -1;
        for (int i = decl.length() - 1; i >= 0; --i) {
            char ch = decl.charAt(i);
            if (!inID && Character.isLetterOrDigit(ch)) {
                inID = true;
                continue;
            }
            if (!inID || Character.isLetterOrDigit(ch) || ch == '_') continue;
            start = i + 1;
            break;
        }
        if (start < 0 && inID) {
            start = 0;
        }
        if (start < 0) {
            g.tool.errMgr.grammarError(ErrorType.CANNOT_FIND_ATTRIBUTE_NAME_IN_DECL, g.fileName, a.token, decl);
        }
        int stop = -1;
        for (int i = start; i < decl.length(); ++i) {
            char ch = decl.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                stop = i;
                break;
            }
            if (i != decl.length() - 1) continue;
            stop = i + 1;
        }
        attr.name = decl.substring(start, stop);
        attr.type = decl.substring(0, start);
        if (stop <= decl.length() - 1) {
            attr.type = attr.type + decl.substring(stop, decl.length());
        }
        attr.type = attr.type.trim();
        if (attr.type.length() == 0) {
            attr.type = null;
        }
        return Tuple.create(start, stop);
    }

    public static Tuple2<Integer, Integer> _parsePostfixDecl(Attribute attr, String decl, ActionAST a, Grammar g) {
        char ch;
        int i;
        int start = -1;
        int stop = -1;
        int colon = decl.indexOf(58);
        int namePartEnd = colon == -1 ? decl.length() : colon;
        for (i = 0; i < namePartEnd; ++i) {
            ch = decl.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_') continue;
            start = i;
            break;
        }
        if (start == -1) {
            start = 0;
            g.tool.errMgr.grammarError(ErrorType.CANNOT_FIND_ATTRIBUTE_NAME_IN_DECL, g.fileName, a.token, decl);
        }
        for (i = start; i < namePartEnd; ++i) {
            ch = decl.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                stop = i;
                break;
            }
            if (i != namePartEnd - 1) continue;
            stop = namePartEnd;
        }
        if (stop == -1) {
            stop = start;
        }
        attr.name = decl.substring(start, stop);
        attr.type = colon == -1 ? "" : decl.substring(colon + 1, decl.length());
        attr.type = attr.type.trim();
        if (attr.type.length() == 0) {
            attr.type = null;
        }
        return Tuple.create(start, stop);
    }

    public static List<Tuple2<String, Integer>> splitDecls(String s, int separatorChar) {
        ArrayList<Tuple2<String, Integer>> args = new ArrayList<Tuple2<String, Integer>>();
        ScopeParser._splitArgumentList(s, 0, -1, separatorChar, args);
        return args;
    }

    public static int _splitArgumentList(String actionText, int start, int targetChar, int separatorChar, List<Tuple2<String, Integer>> args) {
        int p;
        if (actionText == null) {
            return -1;
        }
        actionText = actionText.replaceAll("//[^\\n]*", "");
        int n = actionText.length();
        int last = p = start;
        block8: while (p < n && actionText.charAt(p) != targetChar) {
            char c = actionText.charAt(p);
            switch (c) {
                case '\'': {
                    ++p;
                    while (p < n && actionText.charAt(p) != '\'') {
                        if (actionText.charAt(p) == '\\' && p + 1 < n && actionText.charAt(p + 1) == '\'') {
                            ++p;
                        }
                        ++p;
                    }
                    ++p;
                    continue block8;
                }
                case '\"': {
                    ++p;
                    while (p < n && actionText.charAt(p) != '\"') {
                        if (actionText.charAt(p) == '\\' && p + 1 < n && actionText.charAt(p + 1) == '\"') {
                            ++p;
                        }
                        ++p;
                    }
                    ++p;
                    continue block8;
                }
                case '(': {
                    p = ScopeParser._splitArgumentList(actionText, p + 1, 41, separatorChar, args);
                    continue block8;
                }
                case '{': {
                    p = ScopeParser._splitArgumentList(actionText, p + 1, 125, separatorChar, args);
                    continue block8;
                }
                case '<': {
                    if (actionText.indexOf(62, p + 1) >= p) {
                        p = ScopeParser._splitArgumentList(actionText, p + 1, 62, separatorChar, args);
                        continue block8;
                    }
                    ++p;
                    continue block8;
                }
                case '[': {
                    p = ScopeParser._splitArgumentList(actionText, p + 1, 93, separatorChar, args);
                    continue block8;
                }
            }
            if (c == separatorChar && targetChar == -1) {
                int index;
                String arg = actionText.substring(last, p);
                for (index = last; index < p && Character.isWhitespace(actionText.charAt(index)); ++index) {
                }
                args.add(Tuple.create(arg.trim(), index));
                last = p + 1;
            }
            ++p;
        }
        if (targetChar == -1 && p <= n) {
            int index;
            String arg = actionText.substring(last, p).trim();
            for (index = last; index < p && Character.isWhitespace(actionText.charAt(index)); ++index) {
            }
            if (arg.length() > 0) {
                args.add(Tuple.create(arg.trim(), index));
            }
        }
        return ++p;
    }
}

