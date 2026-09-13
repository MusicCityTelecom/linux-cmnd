/*
 * Decompiled with CFR 0.152.
 */
package com.vdurmont.semver4j;

import com.vdurmont.semver4j.Semver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tokenizer {
    private static final Map<Semver.SemverType, Map<Character, Token>> SPECIAL_CHARS = new HashMap<Semver.SemverType, Map<Character, Token>>();

    protected static List<Token> tokenize(String requirement, Semver.SemverType type) {
        Map<Character, Token> specialChars = SPECIAL_CHARS.get((Object)type);
        if (type == Semver.SemverType.COCOAPODS) {
            requirement = requirement.replace("~>", "~");
        } else if (type == Semver.SemverType.NPM) {
            requirement = requirement.replace("||", "|");
        }
        requirement = requirement.replace("<=", "\u2264").replace(">=", "\u2265");
        LinkedList<Token> tokens = new LinkedList<Token>();
        Token previousToken = null;
        char[] chars = requirement.toCharArray();
        Token token = null;
        for (char c : chars) {
            if (c == ' ') continue;
            if (specialChars.containsKey(Character.valueOf(c))) {
                if (token != null) {
                    tokens.add(token);
                    previousToken = token;
                    token = null;
                }
                Token current = specialChars.get(Character.valueOf(c));
                if (current.type.isUnary() && previousToken != null && previousToken.type == TokenType.VERSION) {
                    tokens.add(new Token(TokenType.AND));
                }
                tokens.add(current);
                previousToken = current;
                continue;
            }
            if (token == null) {
                token = new Token(TokenType.VERSION);
            }
            token.append(c);
        }
        if (token != null) {
            tokens.add(token);
        }
        return tokens;
    }

    static {
        for (Semver.SemverType semverType : Semver.SemverType.values()) {
            SPECIAL_CHARS.put(semverType, new HashMap());
        }
        for (Enum enum_ : TokenType.values()) {
            if (((TokenType)enum_).character == null) continue;
            for (Semver.SemverType type : Semver.SemverType.values()) {
                if (!((TokenType)enum_).supports(type)) continue;
                SPECIAL_CHARS.get((Object)type).put(((TokenType)enum_).character, new Token((TokenType)enum_));
            }
        }
    }

    protected static enum TokenType {
        TILDE(Character.valueOf('~'), true, Semver.SemverType.COCOAPODS, Semver.SemverType.NPM),
        CARET(Character.valueOf('^'), true, Semver.SemverType.NPM),
        EQ(Character.valueOf('='), true, Semver.SemverType.NPM),
        LT(Character.valueOf('<'), true, Semver.SemverType.COCOAPODS, Semver.SemverType.NPM),
        LTE(Character.valueOf('\u2264'), true, Semver.SemverType.COCOAPODS, Semver.SemverType.NPM),
        GT(Character.valueOf('>'), true, Semver.SemverType.COCOAPODS, Semver.SemverType.NPM),
        GTE(Character.valueOf('\u2265'), true, Semver.SemverType.COCOAPODS, Semver.SemverType.NPM),
        HYPHEN(Character.valueOf('-'), false, Semver.SemverType.NPM),
        OR(Character.valueOf('|'), false, Semver.SemverType.NPM),
        AND(null, false, new Semver.SemverType[0]),
        OPENING(Character.valueOf('('), false, Semver.SemverType.NPM),
        CLOSING(Character.valueOf(')'), false, Semver.SemverType.NPM),
        VERSION(null, false, new Semver.SemverType[0]);

        public final Character character;
        private final boolean unary;
        private final Semver.SemverType[] supportedTypes;

        private TokenType(Character character, boolean unary, Semver.SemverType ... supportedTypes) {
            this.character = character;
            this.unary = unary;
            this.supportedTypes = supportedTypes;
        }

        public boolean isUnary() {
            return this.unary;
        }

        public boolean supports(Semver.SemverType type) {
            for (Semver.SemverType t : this.supportedTypes) {
                if (t != type) continue;
                return true;
            }
            return false;
        }
    }

    protected static class Token {
        public final TokenType type;
        public String value;

        public Token(TokenType type) {
            this(type, null);
        }

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public void append(char c) {
            if (this.value == null) {
                this.value = "";
            }
            this.value = this.value + c;
        }
    }
}

