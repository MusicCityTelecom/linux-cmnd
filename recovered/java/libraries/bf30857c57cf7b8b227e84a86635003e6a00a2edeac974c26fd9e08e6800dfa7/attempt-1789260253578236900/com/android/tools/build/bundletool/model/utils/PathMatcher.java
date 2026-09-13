/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.google.common.collect.ImmutableSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class PathMatcher {
    private static final ImmutableSet<Character> REGEXP_SPECIAL_CHARS = "<([{\\^-=$!|]})?*+.>".chars().mapToObj(c2 -> Character.valueOf((char)c2)).collect(ImmutableSet.toImmutableSet());
    private final Pattern regexpPattern;

    private PathMatcher(Pattern regexpPattern) {
        this.regexpPattern = regexpPattern;
    }

    public static PathMatcher createFromGlob(String globPattern) {
        try {
            Pattern regexpPattern = Pattern.compile(PathMatcher.convertGlobToRegexp(globPattern));
            return new PathMatcher(regexpPattern);
        }
        catch (PatternSyntaxException e2) {
            throw new GlobPatternSyntaxException(globPattern, e2);
        }
    }

    public boolean matches(String input) {
        return this.regexpPattern.matcher(input).matches();
    }

    private static String convertGlobToRegexp(String globPattern) {
        StringBuilder regexpBuilder = new StringBuilder().append('^');
        boolean inGroup = false;
        int openingGroupIdx = 0;
        block10: for (int i2 = 0; i2 < globPattern.length(); ++i2) {
            switch (globPattern.charAt(i2)) {
                case '\\': {
                    if (i2 == globPattern.length() - 1) {
                        throw new GlobPatternSyntaxException("No character to escape.", globPattern, i2);
                    }
                    regexpBuilder.append('\\').append(globPattern.charAt(i2 + 1));
                    ++i2;
                    continue block10;
                }
                case '*': {
                    if (i2 + 1 < globPattern.length() && globPattern.charAt(i2 + 1) == '*') {
                        ++i2;
                        regexpBuilder.append(".*?");
                        continue block10;
                    }
                    regexpBuilder.append("[^/]*");
                    continue block10;
                }
                case '?': {
                    regexpBuilder.append(".");
                    continue block10;
                }
                case '[': {
                    char currentChar;
                    char nextChar;
                    int openBracketIdx = i2++;
                    regexpBuilder.append('[');
                    char c2 = nextChar = i2 < globPattern.length() ? globPattern.charAt(i2) : (char)'\u0000';
                    if (nextChar == '^') {
                        regexpBuilder.append('\\');
                    } else if (nextChar == '!') {
                        regexpBuilder.append('^');
                    }
                    while (i2 < globPattern.length() && globPattern.charAt(i2) != ']') {
                        currentChar = globPattern.charAt(i2);
                        if (currentChar == '/') {
                            throw new GlobPatternSyntaxException("Character '/' is not allowed within a character set", globPattern, i2);
                        }
                        regexpBuilder.append(globPattern.charAt(i2));
                        ++i2;
                    }
                    if (i2 == globPattern.length()) {
                        throw new GlobPatternSyntaxException("No matching ']' found.", globPattern, openBracketIdx);
                    }
                    if (i2 == openBracketIdx + 1) {
                        throw new GlobPatternSyntaxException("Empty characters set.", globPattern, openBracketIdx);
                    }
                    regexpBuilder.append(globPattern.charAt(i2));
                    continue block10;
                }
                case '{': {
                    if (inGroup) {
                        throw new GlobPatternSyntaxException("Cannot nest groups.", globPattern, i2);
                    }
                    openingGroupIdx = i2;
                    inGroup = true;
                    regexpBuilder.append("(?:");
                    continue block10;
                }
                case '}': {
                    if (!inGroup) {
                        throw new GlobPatternSyntaxException("No matching '{' found.", globPattern, i2);
                    }
                    regexpBuilder.append(')');
                    inGroup = false;
                    continue block10;
                }
                case ']': {
                    throw new GlobPatternSyntaxException("No matching '[' found.", globPattern, i2);
                }
                case ',': {
                    if (inGroup) {
                        regexpBuilder.append('|');
                        continue block10;
                    }
                    regexpBuilder.append(',');
                    continue block10;
                }
                default: {
                    char currentChar = globPattern.charAt(i2);
                    if (REGEXP_SPECIAL_CHARS.contains(Character.valueOf(currentChar))) {
                        regexpBuilder.append('\\');
                    }
                    regexpBuilder.append(currentChar);
                }
            }
        }
        if (inGroup) {
            throw new GlobPatternSyntaxException("No matching '}' found.", globPattern, openingGroupIdx);
        }
        return regexpBuilder.append('$').toString();
    }

    public static class GlobPatternSyntaxException
    extends RuntimeException {
        private GlobPatternSyntaxException(String message, String globPattern, int index) {
            super(String.format("Unable to parse glob pattern '%s' at character %d. Error: %s", globPattern, index + 1, message));
        }

        private GlobPatternSyntaxException(String globPattern, Throwable cause) {
            super(String.format("Unable to parse glob pattern '%s'.", globPattern), cause);
        }
    }
}

