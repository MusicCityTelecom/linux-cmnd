/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.PatternMatchUtils
 */
package org.springframework.integration.support.utils;

import java.util.Arrays;

public final class PatternMatchUtils {
    private PatternMatchUtils() {
    }

    public static Boolean smartMatchIgnoreCase(String str, String ... patterns) {
        if (patterns != null) {
            return PatternMatchUtils.smartMatch(str.toLowerCase(), (String[])Arrays.stream(patterns).map(String::toLowerCase).toArray(String[]::new));
        }
        return null;
    }

    public static Boolean smartMatch(String str, String ... patterns) {
        if (patterns != null) {
            for (String pattern : patterns) {
                boolean reverse = false;
                String patternToUse = pattern;
                if (pattern.startsWith("!")) {
                    reverse = true;
                    patternToUse = pattern.substring(1);
                } else if (pattern.startsWith("\\")) {
                    patternToUse = pattern.substring(1);
                }
                if (!org.springframework.util.PatternMatchUtils.simpleMatch((String)patternToUse, (String)str)) continue;
                return !reverse;
            }
        }
        return null;
    }
}

