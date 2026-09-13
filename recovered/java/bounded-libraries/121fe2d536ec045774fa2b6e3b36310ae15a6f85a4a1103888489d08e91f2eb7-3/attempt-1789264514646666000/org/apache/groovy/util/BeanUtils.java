/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

public class BeanUtils {
    public static String decapitalize(String property) {
        if (property == null || property.isEmpty()) {
            return property;
        }
        if (property.length() >= 2 && Character.isUpperCase(property.charAt(1)) && Character.isUpperCase(property.charAt(0))) {
            return property;
        }
        char[] c = property.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static String capitalize(String property) {
        String rest = property.substring(1);
        if (Character.isLowerCase(property.charAt(0)) && rest.length() > 0 && Character.isUpperCase(rest.charAt(0))) {
            return property;
        }
        return property.substring(0, 1).toUpperCase() + rest;
    }

    private BeanUtils() {
    }
}

