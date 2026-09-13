/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

public abstract class DataObjectPropertyName {
    private DataObjectPropertyName() {
    }

    public static String toDashedForm(String name) {
        StringBuilder result = new StringBuilder(name.length());
        boolean inIndex = false;
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (inIndex) {
                result.append(ch);
                if (ch != ']') continue;
                inIndex = false;
                continue;
            }
            if (ch == '[') {
                inIndex = true;
                result.append(ch);
                continue;
            }
            char c = ch = ch != '_' ? ch : (char)'-';
            if (Character.isUpperCase(ch) && result.length() > 0 && result.charAt(result.length() - 1) != '-') {
                result.append('-');
            }
            result.append(Character.toLowerCase(ch));
        }
        return result.toString();
    }
}

