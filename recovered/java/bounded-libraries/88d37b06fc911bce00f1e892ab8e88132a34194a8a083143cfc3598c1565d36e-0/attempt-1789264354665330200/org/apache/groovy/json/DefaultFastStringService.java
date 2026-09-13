/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.json;

import org.apache.groovy.json.FastStringService;

public class DefaultFastStringService
implements FastStringService {
    @Override
    public char[] toCharArray(String string) {
        return string.toCharArray();
    }

    @Override
    public String noCopyStringFromChars(char[] chars) {
        return new String(chars);
    }
}

