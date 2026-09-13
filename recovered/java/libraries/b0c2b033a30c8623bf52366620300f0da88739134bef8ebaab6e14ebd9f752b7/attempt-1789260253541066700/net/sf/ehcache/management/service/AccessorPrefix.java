/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

public enum AccessorPrefix {
    get,
    is,
    has;


    public static boolean isAccessor(String methodName) {
        for (AccessorPrefix prefix : AccessorPrefix.values()) {
            if (!methodName.startsWith(prefix.toString())) continue;
            return true;
        }
        return false;
    }

    public static String trimPrefix(String methodName) {
        String trimmed = null;
        for (AccessorPrefix prefix : AccessorPrefix.values()) {
            if (!methodName.startsWith(prefix.toString())) continue;
            trimmed = methodName.substring(prefix.toString().length());
        }
        return trimmed;
    }
}

