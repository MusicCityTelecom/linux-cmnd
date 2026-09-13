/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.classify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.Assert;

public class PatternMatcher<S> {
    private Map<String, S> map = new HashMap<String, S>();
    private List<String> sorted = new ArrayList<String>();

    public PatternMatcher(Map<String, S> map) {
        this.map = map;
        this.sorted = new ArrayList<String>(map.keySet());
        Collections.sort(this.sorted, new Comparator<String>(){

            @Override
            public int compare(String o1, String o2) {
                String s1 = o1;
                String s2 = o2;
                return s2.compareTo(s1);
            }
        });
    }

    public static boolean match(String pattern, String str) {
        char ch;
        int strIdxStart;
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxEnd = strArr.length - 1;
        boolean containsStar = pattern.contains("*");
        if (!containsStar) {
            if (patIdxEnd != strIdxEnd) {
                return false;
            }
            for (int i = 0; i <= patIdxEnd; ++i) {
                char ch2 = patArr[i];
                if (ch2 == '?' || ch2 == strArr[i]) continue;
                return false;
            }
            return true;
        }
        if (patIdxEnd == 0) {
            return true;
        }
        for (strIdxStart = 0; (ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd; ++strIdxStart) {
            if (ch != '?' && ch != strArr[strIdxStart]) {
                return false;
            }
            ++patIdxStart;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (patArr[i] == '*') continue;
                return false;
            }
            return true;
        }
        while ((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?' && ch != strArr[strIdxEnd]) {
                return false;
            }
            --patIdxEnd;
            --strIdxEnd;
        }
        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (patArr[i] == '*') continue;
                return false;
            }
            return true;
        }
        while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patIdxStart + 1; i <= patIdxEnd; ++i) {
                if (patArr[i] != '*') continue;
                patIdxTmp = i;
                break;
            }
            if (patIdxTmp == patIdxStart + 1) {
                ++patIdxStart;
                continue;
            }
            int patLength = patIdxTmp - patIdxStart - 1;
            int strLength = strIdxEnd - strIdxStart + 1;
            int foundIdx = -1;
            block7: for (int i = 0; i <= strLength - patLength; ++i) {
                for (int j = 0; j < patLength; ++j) {
                    ch = patArr[patIdxStart + j + 1];
                    if (ch != '?' && ch != strArr[strIdxStart + i + j]) continue block7;
                }
                foundIdx = strIdxStart + i;
                break;
            }
            if (foundIdx == -1) {
                return false;
            }
            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }
        for (int i = patIdxStart; i <= patIdxEnd; ++i) {
            if (patArr[i] == '*') continue;
            return false;
        }
        return true;
    }

    public S match(String line) {
        S value = null;
        Assert.notNull((Object)line, (String)"A non-null key must be provided to match against.");
        for (String key : this.sorted) {
            if (!PatternMatcher.match(key, line)) continue;
            value = this.map.get(key);
            break;
        }
        if (value == null) {
            throw new IllegalStateException("Could not find a matching pattern for key=[" + line + "]");
        }
        return value;
    }
}

