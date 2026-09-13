/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

public final class StackTraceUtils {
    private StackTraceUtils() {
    }

    public static boolean isFrameContainingXBeforeFrameContainingY(String firstClass, String secondClass, StackTraceElement[] stackTrace) {
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains(firstClass)) {
                return true;
            }
            if (!element.getClassName().contains(secondClass)) continue;
            return false;
        }
        throw new IllegalArgumentException("Neither " + firstClass + " nor " + secondClass + " class found");
    }
}

