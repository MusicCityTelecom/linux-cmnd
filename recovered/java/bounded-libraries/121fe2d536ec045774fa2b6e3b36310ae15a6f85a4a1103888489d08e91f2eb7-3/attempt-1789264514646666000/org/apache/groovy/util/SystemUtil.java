/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

public class SystemUtil {
    public static String setSystemPropertyFrom(String nameValue) {
        String value;
        String name;
        if (nameValue == null) {
            throw new IllegalArgumentException("argument should not be null");
        }
        int i = nameValue.indexOf(61);
        if (i == -1) {
            name = nameValue;
            value = Boolean.TRUE.toString();
        } else {
            name = nameValue.substring(0, i);
            value = nameValue.substring(i + 1);
        }
        name = name.trim();
        System.setProperty(name, value);
        return name;
    }

    public static String setSystemPropertyFromSafe(String nameValue) {
        try {
            return SystemUtil.setSystemPropertyFrom(nameValue);
        }
        catch (SecurityException securityException) {
            return null;
        }
    }

    public static String getSystemPropertySafe(String name, String defaultValue) {
        try {
            return System.getProperty(name, defaultValue);
        }
        catch (IllegalArgumentException | NullPointerException | SecurityException runtimeException) {
            return defaultValue;
        }
    }

    public static String getSystemPropertySafe(String name) {
        return SystemUtil.getSystemPropertySafe(name, null);
    }

    public static boolean getBooleanSafe(String name) {
        try {
            return Boolean.getBoolean(name);
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

    public static Integer getIntegerSafe(String name, Integer def) {
        try {
            return Integer.getInteger(name, def);
        }
        catch (SecurityException securityException) {
            return def;
        }
    }

    public static Long getLongSafe(String name, Long def) {
        try {
            return Long.getLong(name, def);
        }
        catch (SecurityException securityException) {
            return def;
        }
    }
}

